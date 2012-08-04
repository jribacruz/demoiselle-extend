#demoiselle-jsf-validation
O componente demoiselle-jsf-validation provê mecanismo de validação pra projetos demoiselle jsf. 

Tem como objetivo centralizar as regras de validação no BC de forma simples.

##Como usar
###Validando antes do insert() e antes do update(): @ValidateOnSave
```java
@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Verifica se o bookmark que será persistido inicia com a String 'http://',
	 * Em caso afirmativo o método retorna true, a validação é sucedida e se nenhuma 
	 * outra validação no BC falhar (método retornar false) o método insert() ou update()
	 * é executado.
	 */
	@ValidateOnSave(message="{bookmark.link.url.failure}")
	protected boolean validateLinkURL(Bookmark bookmark) {
		return bookmark.getLink() != null && bookmark.getLink.startWith("http://") ? true: false;
	}
	
}
```

Caso o método **validateLinkURL** retorne false a validação é falha e o método insert() ou update() não é executado. 

###Validando antes do insert(): @ValidateOnInsert
```java
@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Verifica se o bookmark que será persistido já existe na base de dados.
	 * Em caso afirmativo o método retorna false, a validação é falha a mensagem de erro
	 * do atributo message é adicionada no FacesMessage e o método insert() não será executado.
	 * Outras validações que validam o insert ainda serão processadas.
	 */
	@ValidateOnInsert(message="{bookmark.link.presence.failure}")
	protected boolean validateLinkPresence(Bookmark bookmark) {
		return bookmark.getLink() != null && !getDelegate().hasLink(bookmark.getLink()) ? true: false;
	}
	
}
```

Caso o método **validateLinkPresence** retorne false a validação é falha e o método insert() não é executado;

