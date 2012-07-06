package br.gov.component.demoiselle.events.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.primefaces.context.RequestContext;

import br.gov.component.demoiselle.events.OnComplete;
import br.gov.component.demoiselle.events.OnFailure;
import br.gov.component.demoiselle.events.OnSuccess;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.util.Strings;

@Controller
@Interceptor
public class EventsInterceptor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private MessageContext messageContext;

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Object obj = ctx.proceed();
		Method method = ctx.getMethod();
		if (method.isAnnotationPresent(OnFailure.class) && facesContext.isValidationFailed()) {
			String message = method.getAnnotation(OnFailure.class).message();
			String[] updateList = method.getAnnotation(OnFailure.class).update();
			String execute = method.getAnnotation(OnFailure.class).execute();
			sendMessage(message, SeverityType.ERROR);
			sendUpdate(updateList);
			sendExecute(execute);
		}

		if(method.isAnnotationPresent(OnSuccess.class) && !facesContext.isValidationFailed()) {
			String message = method.getAnnotation(OnSuccess.class).message();
			String[] updateList = method.getAnnotation(OnSuccess.class).update();
			String execute = method.getAnnotation(OnSuccess.class).execute();
			sendMessage(message, SeverityType.INFO);
			sendUpdate(updateList);
			sendExecute(execute);
		}

		if(method.isAnnotationPresent(OnComplete.class)) {
			String message = method.getAnnotation(OnComplete.class).message();
			String[] updateList = method.getAnnotation(OnComplete.class).update();
			String execute = method.getAnnotation(OnComplete.class).execute();
			sendMessage(message, SeverityType.INFO);
			sendUpdate(updateList);
			sendExecute(execute);
		}
		return obj;
	}

	private void sendMessage(String message, SeverityType severityType) {
		if (!Strings.isEmpty(message)) {
			messageContext.add(message, severityType);
		}
	}

	private void sendUpdate(String[] updateList) {
		if (updateList != null && updateList.length > 0) {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.update(Arrays.asList(updateList));
		}
	}

	private void sendExecute(String execute) {
		if (!Strings.isEmpty(execute)) {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute(execute);
		}
	}

}
