package br.gov.frameworkdemoiselle.component.jsf.view.impl;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;

public class ElementImpl implements Element {

	private String id;
	private String widgetVar;
	private String style;
	private String styleClass;

	public ElementImpl(String id, String widgetVar, String style, String styleClass) {
		super();
		this.id = id;
		this.widgetVar = widgetVar;
		this.style = style;
		this.styleClass = styleClass;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWidgetVar() {
		return this.widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
