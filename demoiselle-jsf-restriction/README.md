demoiselle-jsf-restriction
==========================

O pacote demoiselle-jsf-restriction tem com como objetivo facilitar e organizar as consultas JPA. Utiliza
o Criteria API para a criação de consultas. Com a concentração das restrições das consultas em um único ponto



##1. Instalação

No pom.xml do projeto adicionar a seguinte dependência:

```xml
<dependency>
	<groupId>br.gov.frameworkdemoiselle.component</groupId>
	<artifactId>demoiselle-jsf-restriction</artifactId>
	<version>0.0.1-RC1</version>
</dependency>
```

E o repositório:
```xml
<repository>
	<id>demoiselle-jsf-restriction-repo</id>
	<url>http://jribacruz.github.com/maven</url>
</repository>
```

##2. Classe de Critérios (Criteria Class)

A classe de critérios é o ponto central onde as restrições (RestrictionBeans), a ordenação, a projeção são declaradas. Por ser
um **ManagedBean** é visivel na camada de visão, onde podemos controlar os valores passados as restrições, como veremos mais a frente.

```java
public abstract class AbstractCriteriaBean<T> {...}
```

```java
@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
}
```

##3. Utilizando a classe Criteria com o LazyDataModel (Primefaces)

Apenas com criação da classe de critérios 

Para iniciar o uso da classe Criteria basta injetar o LazyDataModel da seguinte maneira:

###bean de listagem

```java
@ViewController
public class BookmarkListMB extends AbstractListPageBean<Bookmark, Long> {
	
	@Inject
	private BookmarkBC bc;
	
	@Inject
	@Criteria(BookmarkDataTableCriteria.class)
	private LazyDataModel<Bookmark> lazyDataModel;
	
	@Override
	protected List<Bookmark> handleResultList() {
		return this.bc.findAll();
	}
	
	//getters and setters
	
}
```
Não é necessário implementar o método load do LazyDataModel, na injeção é realizada a implementação do metodo (load)
com os dados da paginação, filtros (veremos como utiliza-los mais a frente) e a ordenação. A implementação do método 
**handleResultList()** com o findAll() como retorno é necessária.

**Com o código acima (caso o datatable esteja com o sortBy do column atribuido) a ordenação ja será realizada automaticamente.**

##4. Classe de Restrição (RestrictionBean Class)

```java
public abstract class RestrictionBean<T, X> {...}
```

A classe de restrição (extends RestrictionBean) são as que realizarão efetivamente as restrições na pesquisa, afetando diretamente a clausura where da query. São agrupadas na classe 
de critérios (Criteria Class). A classe recebe como parametro de tipo genérico o tipo da entidade (T) e o tipo do atributo (X) que esta presente na classe T.

Como exemplo, vamos criar uma restrição que restringe a lista de Bookmarks de acordo com o nome de descrição (atributo description da entidade Bookmark)
passado por um campo h:inputText.

A classe RestrictionBean possui um atributo chamado *value* (do tipo generico X passado na classe RestrictionBean) do tipo especificado no segundo parametro generico da classe. 

##Exemplo 

###classe de restrição
```java

public class BookmarkDescriptionRestriction extends RestrictionBean<Bookmark, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<Bookmark> p) {
		return cb.like(cb.lower(p.<String> get("description")), "%" + getValue().toLowerCase() + "%");
	}

}

```

###classe de criterios

Para utilizarmos a restrição é necessário injeta-la na classe de critérios e anota-la com @Restriction

```java

@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Restriction
	private BookmarkDescriptionRestriction query1;
	
	//getters and setters
	
}
```

em seguinda realizamos um bind com o h:inputText e o campo value da restrição

###xhtml

```xml
<h:inputText value="#{bookmarkDataTableCriteria.query1.value}">
	<p:ajax event="keyup" listener="#{bookmarkDataTableCriteria.filter}" update="[id-do-datatable]" />
</h:inputText>
```

Com o código acima, cada vez que o usuário digitar um valor no input a restrição **BookmarkDescriptionRestriction** é executada e o valor 
do input é passado para a restrição realizar a filtragem. É importante observar que quando o valor do campo input é vazio a restrição não é
executada.

## Restrições não opcionais ( @Restriction(optional=false) )

Por padrão as restrições são opcionais, o que siginifica que se o atributo **value** da classe RestrictionBean é nulo (em branco em caso de string e vazio em caso de coleções)
a restrição não entra na consulta. A restrição não opcional (com a inclusão do atributo optional false) é incluida na consulta mesmo se o valor de **value** é nulo.
Sua utilidade é para incluir **restrições padrões** nas consultas onde o valor do **value** não provem da camada de visão. Como exemplo,
no caso de se restringir a consulta a determinado grupo de usuário etc.

##6. Modo de seleção
O modo de seleção tem como objetivo *ativar/processar* a consulta caso a seleção seja verdadeira. Tal funcionalidade é implementada
para ativar a consulta através de checkbox. Onde queremos que uma restrição entre na consulta caso o checkbox esteja selecionado.
Para isso a classe RestrictionBean possui um atributo **selection* que deve ser associado ao atributo **value** do checkbox. Segue abaixo
um exemplo de implementação:

##7. Vinculando o filterColumn do datatable com RestritionBeans

Com a funcionalidade de filterColumn é possível associar  

###classe de criterios
```java
@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Restriction(field="description", datatableFilterColumn=true)
	private LikeRestrictionBean<Bookmark, String> query1;
	
	//getters and setters
	
}

```
###xhtml do datatable
```xml
<p:dataTable value="#{bookmarkListMB.lazyDataModel}" var="bean">
	...
	<p:column headerText="description" filterBy="#{bean.description}>
		...
	</p:column>
	...
</p:dataTable>
```

##8. Ordenação

Para implementar a ordenação da consulta basta sobrescrever o método orderBy da classe de criterios. Utilizando o método auxiliar **by(Order...order)** da superclasse
 é possivel concatenar várias ordenações.

```java
@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
	
	...
	
	@Override
	protected List<Order> orderBy(CriteriaBuilder cb, Root<Bookmark> p) {
		return by(cb.asc(p.get("description")));
	}

}

```

##9.

##10. Projeções


