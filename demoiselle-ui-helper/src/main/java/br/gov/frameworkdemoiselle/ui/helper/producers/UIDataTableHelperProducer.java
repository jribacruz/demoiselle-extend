package br.gov.frameworkdemoiselle.ui.helper.producers;

import java.lang.reflect.Field;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.ui.helper.annotations.UIDataTable;
import br.gov.frameworkdemoiselle.ui.helper.datatable.UIDataTableHelper;

public class UIDataTableHelperProducer {

	@Produces
	public UIDataTableHelper create(InjectionPoint ip, RequestContext context) {
		Field field = (Field) ip.getMember();
		String formId = field.getAnnotation(UIDataTable.class).formId();
		String id = field.getAnnotation(UIDataTable.class).id();
		return new UIDataTableHelper(context, formId, id);
	}

}
