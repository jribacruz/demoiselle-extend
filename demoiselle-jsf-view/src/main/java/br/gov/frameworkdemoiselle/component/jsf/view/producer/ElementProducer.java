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

	@Produces
	@Default
	public static Element create(final InjectionPoint ip) {

		String preffix = Strings.isEmpty(ip.getBean().getBeanClass().getAnnotation(Component.class).preffix()) ? ip
				.getBean().getName() : ip.getBean().getBeanClass().getAnnotation(Component.class).preffix();

				Field field = (Field) ip.getMember();
				String id = field.isAnnotationPresent(ID.class) ? field.getAnnotation(ID.class).value() : Strings
						.camelCaseToSymbolSeparated(field.getName(), "-").concat("-id");
				String widgetVar = field.isAnnotationPresent(WidgetVar.class) ? field.getAnnotation(WidgetVar.class).value()
						: Strings.camelCaseToSymbolSeparated(field.getName(), "_").concat("_wvar");
				String style = field.isAnnotationPresent(Style.class) ? field.getAnnotation(Style.class).value() : "";
				String styleClass = field.isAnnotationPresent(StyleClass.class) ? field.getAnnotation(StyleClass.class).value()
						: "app-ui-" + Strings.camelCaseToSymbolSeparated(field.getName(), "-");

				return new ElementImpl(preffix.concat("-").concat(id), preffix.concat("_").concat(widgetVar), style, styleClass);
	}
}
