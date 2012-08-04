#demoiselle-jsf-validation
O componente demoiselle-jsf-validation provê mecanismo de validação pra projetos demoiselle jsf. 

Tem como objetivo centralizar as regras de validação no BC de forma simples.

##Como usar
###Validando antes do insert() e antes do update(): @ValidateOnSave
```java
@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {
	private static final long serialVersionUID = 1L;
	
	@ValidateOnSave(message="{bookmark.link.url.failure}")
	protected boolean validateLinkURL(Bookmark bookmark) {
		return bookmark.getLink() != null && bookmark.getLink.startWith("http://") ? true: false;
	}
	
}
```

###Validando antes do insert(): @ValidateOnInsert
```java
@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {
	private static final long serialVersionUID = 1L;
	
	@ValidateOnInsert(message="{bookmark.link.presence.failure}")
	protected boolean validateLinkPresence(Bookmark bookmark) {
		return bookmark.getLink() != null && getDelegate().hasLink(bookmark.getLink()) ? true: false;
	}
	
}
```

