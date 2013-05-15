package br.gov.frameworkdemoiselle.jsf.view.produces;

import java.lang.reflect.Field;

import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.jsf.view.UIDialog;
import br.gov.frameworkdemoiselle.jsf.view.annotations.Dialog;
import br.gov.frameworkdemoiselle.jsf.view.implementations.UIDialogImpl;
import br.gov.frameworkdemoiselle.util.Reflections;

public class UIDialogProducer {

	public <T> UIDialog<T> create(InjectionPoint ip) {
		Field field = (Field) ip.getMember();

		return new UIDialogImpl<T>(getFormId(field), getDialogId(field), getWidgetVar(field), getBodyId(field),
				this.<T> getDialogClass(field));
	}

	private <T> Class<T> getDialogClass(Field field) {
		return Reflections.getGenericTypeArgument(field, 0);
	}

	private String getFormId(Field field) {
		if (field.isAnnotationPresent(Dialog.class)) {
			return field.getAnnotation(Dialog.class).formId();
		}
		return null;
	}

	private String getDialogId(Field field) {
		if (field.isAnnotationPresent(Dialog.class)) {
			return field.getAnnotation(Dialog.class).id();
		}
		return null;
	}

	private String getBodyId(Field field) {
		if (field.isAnnotationPresent(Dialog.class)) {
			return field.getAnnotation(Dialog.class).bodyId();
		}
		return null;
	}

	private String getWidgetVar(Field field) {
		if (field.isAnnotationPresent(Dialog.class)) {
			return field.getAnnotation(Dialog.class).widgetVar();
		}
		return null;
	}

}
