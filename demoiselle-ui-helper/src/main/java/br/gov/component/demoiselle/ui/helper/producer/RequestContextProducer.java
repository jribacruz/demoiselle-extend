package br.gov.component.demoiselle.ui.helper.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import org.primefaces.context.RequestContext;

public class RequestContextProducer {

	@Produces
	@Default
	@RequestScoped
	public RequestContext create() {
		return RequestContext.getCurrentInstance();
	}

}
