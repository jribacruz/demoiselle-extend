package br.gov.frameworkdemoiselle.jsf.view.produces;

import java.lang.reflect.Field;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.jsf.view.UIDataTable;
import br.gov.frameworkdemoiselle.jsf.view.annotations.DataTable;
import br.gov.frameworkdemoiselle.jsf.view.implementations.UIDataTableImpl;
import br.gov.frameworkdemoiselle.util.Reflections;

public class UIDataTableProducer {

	@Produces
	public <T> UIDataTable<T> create(InjectionPoint ip) {
		Field field = (Field) ip.getMember();
		return new UIDataTableImpl<T>(getFormId(field), getDataTableId(field), this.<T> getDataTableClass(field));
	}

	private <T> Class<T> getDataTableClass(Field field) {
		return Reflections.getGenericTypeArgument(field, 0);
	}

	private String getFormId(Field field) {
		if (field.isAnnotationPresent(DataTable.class)) {
			return field.getAnnotation(DataTable.class).formId();
		}
		return null;
	}

	private String getDataTableId(Field field) {
		if (field.isAnnotationPresent(DataTable.class)) {
			return field.getAnnotation(DataTable.class).id();
		}
		return null;
	}

}
