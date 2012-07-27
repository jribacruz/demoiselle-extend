package br.gov.frameworkdemoiselle.component.jsf.view;

public interface Element {

	public String getId();

	public void setId(String id);

	public String getWidgetVar();

	public void setWidgetVar(String widgetVar);

	public String getStyle();

	public void setStyle(String style);

	public String getStyleClass();

	public void setStyleClass(String styleClass);

	public void setContainer(Element element);

	public Element getContainer();

	public void add(Element element);
}
