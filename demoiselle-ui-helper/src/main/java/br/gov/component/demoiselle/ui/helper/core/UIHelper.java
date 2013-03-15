package br.gov.component.demoiselle.ui.helper.core;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;

public class UIHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private RequestContext requestContext;
	@Inject
	private FacesContext facesContext;

	@Inject
	private MessageContext messageContext;


	protected void update(String id) {
		requestContext.update(id);
	}

	protected void execute(String command) {
		requestContext.execute(command);
	}

	protected void update(String formId, String id) {
		requestContext.update(formId.concat(":").concat(id));
	}

	protected void addMessage(String message) {
		messageContext.add(new DefaultMessage(message), SeverityType.INFO);
	}

	protected void addMessage(String message, Object... params) {
		messageContext.add(new DefaultMessage(message, params), SeverityType.INFO);
	}

	protected boolean isValidationFailed() {
		return facesContext.isValidationFailed();
	}

}