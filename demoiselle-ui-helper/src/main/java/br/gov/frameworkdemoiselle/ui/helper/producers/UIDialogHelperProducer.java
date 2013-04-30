package br.gov.frameworkdemoiselle.ui.helper.producers;

import java.lang.reflect.Field;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.ui.helper.annotations.UIDialog;
import br.gov.frameworkdemoiselle.ui.helper.dialog.UIDialogHelper;

public class UIDialogHelperProducer {

	@Produces
	public UIDialogHelper create(InjectionPoint ip, RequestContext context) {
		Field field = (Field) ip.getMember();
		String formId = field.getAnnotation(UIDialog.class).formId();
		String id = field.getAnnotation(UIDialog.class).id();
		String bodyId = field.getAnnotation(UIDialog.class).bodyId();
		String wvar = field.getAnnotation(UIDialog.class).widgetVar();
		return new UIDialogHelper(context, formId, id, bodyId, wvar);
	}

}
