package br.gov.frameworkdemoiselle.component.jsf.view.producer;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.component.jsf.view.Element;
import br.gov.frameworkdemoiselle.component.jsf.view.annotation.Component;
import br.gov.frameworkdemoiselle.component.jsf.view.annotation.ID;
import br.gov.frameworkdemoiselle.component.jsf.view.annotation.Style;
import br.gov.frameworkdemoiselle.component.jsf.view.annotation.StyleClass;
import br.gov.frameworkdemoiselle.component.jsf.view.annotation.WidgetVar;
import br.gov.frameworkdemoiselle.component.jsf.view.impl.ElementImpl;
import br.gov.frameworkdemoiselle.util.Strings;

public class ElementProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	private static InjectionPoint ip;

	@Produces
	@Default
	public static Element create(final InjectionPoint ip) {
		ElementProducer.ip = ip;
		return new ElementImpl(getPreffix().concat("-").concat(getId()), getPreffix().concat("_").concat(getWidgetVar()),
				getStyle(), getStyleClass());
	}

	private static String getPreffix() {
		return Strings.isEmpty(ip.getBean().getBeanClass().getAnnotation(Component.class).preffix()) ? ip.getBean()
				.getName() : ip.getBean().getBeanClass().getAnnotation(Component.class).preffix();
	}

	private static String getId() {
		Field field = (Field) ip.getMember();
		String id = field.isAnnotationPresent(ID.class) ? field.getAnnotation(ID.class).value() : Strings
				.camelCaseToSymbolSeparated(field.getName(), "-").concat("-id");
		return id;
	}

	private static String getWidgetVar() {
		Field field = (Field) ip.getMember();
		String widgetVar = field.isAnnotationPresent(WidgetVar.class) ? field.getAnnotation(WidgetVar.class).value()
				: Strings.camelCaseToSymbolSeparated(field.getName(), "_").concat("_wvar");
		return widgetVar;
	}

	private static String getStyle() {
		Field field = (Field) ip.getMember();
		String style = field.isAnnotationPresent(Style.class) ? field.getAnnotation(Style.class).value() : null;
		return style;
	}

	private static String getStyleClass() {
		Field field = (Field) ip.getMember();
		String styleClass = field.isAnnotationPresent(StyleClass.class) ? field.getAnnotation(StyleClass.class).value()
				: "app-ui-" + Strings.camelCaseToSymbolSeparated(field.getName(), "-");
		return styleClass;
	}

}
