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


##4. Classe de Restrição (RestrictionBean Class)
---------------------------------------------

```java
public abstract class RestrictionBean<T, X> {...}
```

A classe de restrição (extends RestrictionBean) são as que realizarão efetivamente as restrições na pesquisa, afetando diretamente a clausura where da query. São agrupadas na classe 
de critérios (Criteria Class). A classe recebe como parametro de tipo genérico o tipo da entidade (T) e o tipo do atributo (X) que esta presente na classe T.

Como exemplo, vamos criar uma restrição que restringe a lista de Bookmarks de acordo com o nome de descrição (atributo description da entidade Bookmark)
passado por um campo h:inputText

###classe de restrição
```java

public class BookmarkDescriptionRestriction extends RestrictionBean<Bookmark, String> {

private static final long serialVersionUID = 1L;

	@Override
	public Predicate restriction(CriteriaBuilder cb, Root<Bookmark> p) {
		return cb.like(p.<String> get("description"), "%" + getValue() + "%");
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

##5. Custom RestrictionBeans
----------------------------

O pacote demoiselle-jsf-restriction possui um conjunto de classes restrictions prontas com restrições comuns, que podem sem injetadas diretamente 
na classe Criteria

###5.1 ContainsRestrictionBean




###5.2 EmptyRestrictionBean

###5.3 EqualRestrictionBean

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

###5.4 FalseRestrictionBean

###5.5 EqualRestrictionBean

###5.6 GreaterThanOrEqualToRestrictionBean

###5.7 GreaterThanRestrictionBean

###5.8 InRestrictionBean

###5.9 LessThanOrEqualToRestrictionBean

###5.10 LessThanRestrictionBean

###5.11 LikeLeftRestrictionBean

###5.12 LikeRestritionBean

###5.13 LikeRightRestrictionBean

###5.14 MemberRestrictionBean

###5.15 NotContainsRestrictionBean

###5.16 NotEmptyRestrictionBean

###5.17 NotEqualRestrictionBean

###5.18 NotInRestrictionBean

###5.19 NotMemberRestrictionBean

###5.20 NotNullRestrictionBean

###5.21 NullRestrictionBean

###5.22 TrueRestrictionBean


##6. Modo de seleção
------------------

##7. Vinculando o filterColumn do datatable com RestritionBeans
-------------------------------------------------------------

##8. Projeções
------------

##9. Ordenação
------------

