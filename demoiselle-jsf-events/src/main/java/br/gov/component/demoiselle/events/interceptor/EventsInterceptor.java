package br.gov.component.demoiselle.events.interceptor;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.gov.frameworkdemoiselle.stereotype.Controller;

@Controller
@Interceptor
public class EventsInterceptor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		return null;
	}

}
