package br.gov.frameworkdemoiselle.component.jsf.view.impl;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;

public class ElementImpl implements Element {

	private String id;
	private String widgetVar;
	private String style;
	private String styleClass;
	private Element container;

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

	public void setContainer(Element element) {
		this.container = element;
	}

	public Element getContainer() {
		return this.container;
	}

	public void add(Element element) {
		element.setContainer(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ElementImpl [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (widgetVar != null) {
			builder.append("widgetVar=");
			builder.append(widgetVar);
			builder.append(", ");
		}
		if (style != null) {
			builder.append("style=");
			builder.append(style);
			builder.append(", ");
		}
		if (styleClass != null) {
			builder.append("styleClass=");
			builder.append(styleClass);
			builder.append(", ");
		}
		if (container != null) {
			builder.append("container=");
			builder.append(container);
		}
		builder.append("]");
		return builder.toString();
	}

}
