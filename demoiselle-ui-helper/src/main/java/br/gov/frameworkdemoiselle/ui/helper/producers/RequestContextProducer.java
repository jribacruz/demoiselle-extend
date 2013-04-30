package br.gov.frameworkdemoiselle.ui.helper.producers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.primefaces.context.RequestContext;

public class RequestContextProducer {

	@Produces
	@RequestScoped
	public RequestContext create() {
		return RequestContext.getCurrentInstance();
	}

}
