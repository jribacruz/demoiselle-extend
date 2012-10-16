demoiselle-jsf-restriction
==========================



##1. Instalação

##2. Classe de Critérios (Criteria Class)

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
**Com o código acima caso o datatable esteja com o sortBy do column atribuido a ordenação ja será realizada.**

##4. Classe de Restrição (RestrictionBean Class)

```java
public abstract class RestrictionBean<T, X> {...}
```

A classe de restrição (extends RestrictionBean) são as que realizarão efetivamente as restrições na pesquisa, afetando diretamente a clausura where da query. São agrupadas na classe 
de critérios (Criteria Class). A classe recebe como parametro de tipo genérico o tipo da entidade (T) e o tipo do atributo (X) que esta presente na classe T.

Como exemplo, vamos criar uma restrição que restringe a lista de Bookmarks de acordo com o nome de descrição (atributo description da entidade Bookmark)
passado por um campo h:inputText

##Exemplo 1

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

##5. Custom Restriction
----------------------------

O pacote demoiselle-jsf-restriction possui um conjunto de classes restrictions prontas com restrições comuns, que podem sem injetadas diretamente 
na classe Criteria.

###5.1 ContainsRestriction




###5.2 EmptyRestriction

###5.3 EqualRestriction

```java

@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Restriction(field="description")
	private EqualRestrictionBean<Bookmark, String> query1;
	
	//getters and setters
	
}

```

###5.4 FalseRestriction

###5.5 EqualRestriction

###5.6 GreaterThanOrEqualToRestriction

###5.7 GreaterThanRestriction

###5.8 InRestriction

###5.9 LessThanOrEqualToRestriction

###5.10 LessThanRestriction

###5.11 LikeLeftRestriction

###5.12 LikeRestrition
Reescrevendo o Exemplo 1 utilizando o Custom Restriction LikeRestritionBean

###classe de criterios

```java

@CriteriaController
public class BookmarkDataTableCriteria extends AbstractCriteriaBean<Bookmark> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Restriction(field="description")
	private LikeRestrictionBean query1;
	
	//getters and setters
	
}
```


###5.13 LikeRightRestriction

###5.14 MemberRestriction

###5.15 NotContainsRestriction

###5.16 NotEmptyRestriction

###5.17 NotEqualRestriction

###5.18 NotInRestriction

###5.19 NotMemberRestriction

###5.20 NotNullRestriction

###5.21 NullRestriction

###5.22 TrueRestriction


##6. Modo de seleção
------------------

##7. Vinculando o filterColumn do datatable com RestritionBeans

O objetivo da vinculação do filterColumn do datatable é criar  

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

Para implementar a ordenação baste sobrescrever o método orderBy da classe de criterios. Utilizando o método auxiliar **by(Order...order)** é possivel
concatenar várias ordenações.

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


##9. Projeções


