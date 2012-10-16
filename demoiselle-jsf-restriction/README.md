demoiselle-jsf-restriction
==========================

##1. Instalação
-------------

##2. Classes Criteria
---------------------------

##3. Utilizando a classe Criteria com o LazyDataModel (Primefaces)
----------------------------------------------------------------
Para iniciar o uso da classe Criteria basta injetar o LazyDataModel da seguinte maneira:

```java
@ViewController
public class BookmarkListMB extends AbstractListPageBean<Bookmark, Long> {
	
	@Inject
	@Criteria(BookmarkDataTableCriteria.class)
	private LazyDataModel<Bookmark> lazyDataModel;
	
	@Override
	protected List<Bookmark> handleResultList() {
		return this.bc.findAll();
	}
	
}
```


##4. Classes RestrictionBean
-------------------------------------

##5. Custom RestrictionBeans
--------------------------

##6. Modo de seleção
------------------

##7. Vinculando o filterColumn do datatable com RestritionBeans
-------------------------------------------------------------

##8. Projeções
------------

##9. Ordenação
------------

