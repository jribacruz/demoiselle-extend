package br.gov.component.demoiselle.ui.helper.producer;

import java.lang.reflect.Field;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.component.demoiselle.ui.helper.core.UIHelper;
import br.gov.component.demoiselle.ui.helper.impl.UIDataTableHelperImpl;
import br.gov.component.demoiselle.ui.helper.impl.UIDialogHelperImpl;
import br.gov.component.demoiselle.ui.helper.types.UIDataTableHelper;
import br.gov.component.demoiselle.ui.helper.types.UIDialogHelper;

public class UIHelperProducer {

	@Produces
	@Default
	public UIDialogHelper createDialogHelper(InjectionPoint ip, @New UIHelper helper) {
		return new UIDialogHelperImpl((Field) ip.getMember(), helper);
	}

	@Produces
	@Default
	public UIDataTableHelper createDataTableHelper(InjectionPoint ip, @New UIHelper helper) {
		return new UIDataTableHelperImpl((Field) ip.getMember(), helper);
	}

}
