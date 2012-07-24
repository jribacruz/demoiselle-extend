package br.gov.component.demoiselle.events.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import br.gov.component.demoiselle.events.OnComplete;
import br.gov.component.demoiselle.events.OnFailure;
import br.gov.component.demoiselle.events.OnSuccess;
import br.gov.frameworkdemoiselle.internal.bootstrap.CoreBootstrap;
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

	private final Logger logger;

	@Inject
	public EventsInterceptor(Logger logger) {
		this.logger = logger;
	}

	private final Map<Class<?>, Map<String, Method>> cache = new HashMap<Class<?>, Map<String, Method>>();

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Object obj = ctx.proceed();

		Class<?> type = getType(ctx);

		if (!isLoaded(type)) {
			loadHandlers(type);
		}

		Method method = getMethod(type, ctx.getMethod().getName());

		if (method != null) {
			if (method.isAnnotationPresent(OnFailure.class) && facesContext.isValidationFailed()) {
				logger.info("Chamada do evento OnFailure para metodo " + method.getName());
				String message = method.getAnnotation(OnFailure.class).message();
				String[] updateList = method.getAnnotation(OnFailure.class).update();
				String execute = method.getAnnotation(OnFailure.class).execute();
				sendMessage(message, SeverityType.ERROR);
				sendUpdate(updateList);
				sendExecute(execute);
			}
			if (method.isAnnotationPresent(OnSuccess.class) && !facesContext.isValidationFailed()) {
				logger.info("Chamada do evento OnSuccess para metodo " + method.getName());
				String message = method.getAnnotation(OnSuccess.class).message();
				String[] updateList = method.getAnnotation(OnSuccess.class).update();
				String execute = method.getAnnotation(OnSuccess.class).execute();
				sendMessage(message, SeverityType.INFO);
				sendUpdate(updateList);
				sendExecute(execute);
			}
			if (method.isAnnotationPresent(OnComplete.class)) {
				logger.info("Chamada do evento OnComplete para metodo " + method.getName());
				String message = method.getAnnotation(OnComplete.class).message();
				String[] updateList = method.getAnnotation(OnComplete.class).update();
				String execute = method.getAnnotation(OnComplete.class).execute();
				sendMessage(message, SeverityType.INFO);
				sendUpdate(updateList);
				sendExecute(execute);
			}
		}
		return obj;
	}

	private final Method getMethod(final Class<?> type, final String methodName) {
		Method handler = null;

		if (cache.containsKey(type) && cache.get(type).containsKey(methodName)) {
			handler = cache.get(type).get(methodName);
		}

		return handler;
	}

	private void sendMessage(String message, SeverityType severityType) {
		messageContext.clear();
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

	private final Class<?> getType(final InvocationContext ic) {
		Class<?> type = ic.getTarget().getClass();

		if (!CoreBootstrap.isAnnotatedType(type)) {
			type = type.getSuperclass();
			logger.debug("proxy-detected", ic.getTarget().getClass(), ic.getTarget().getClass().getSuperclass());
		}

		return type;
	}

	private final boolean isLoaded(final Class<?> type) {
		return cache.containsKey(type);
	}

	private final void loadHandlers(final Class<?> type) {
		Map<String, Method> mapHandlers = new HashMap<String, Method>();
		Method[] methods = type.getMethods();

		for (Method method : methods) {
			if (method.isAnnotationPresent(OnComplete.class) || method.isAnnotationPresent(OnSuccess.class)
					|| method.isAnnotationPresent(OnFailure.class)) {
				mapHandlers.put(method.getName(), method);
			}
		}
		cache.put(type, mapHandlers);
	}
}
