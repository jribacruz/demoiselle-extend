package br.gov.component.demoiselle.xvalidation.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.xvalidation.Validation;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Faces;
import br.gov.frameworkdemoiselle.util.Strings;

@Interceptor
@Validation(message = "")
public class ValidationInterceptor {
	@Inject
	private MessageContext messageContext;

	@Inject
	private ValidationContext validationContext;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object handler(InvocationContext context) throws Exception {
		Method method = context.getMethod();
		Validation validation = method.getAnnotation(Validation.class);
		logger.debug("Chamada ao método de validação: {}" + method.getName());
		Object ret = null;
		try {
			ret = context.proceed();
			String message = validation.message();
			String clientId = validation.clientId();
			if (!Strings.isEmpty(message)) {
				if (!(Boolean) ret) {
					Message msg = new DefaultMessage(message, validationContext.getValidationParam());
					if (!Strings.isEmpty(clientId)) {
						Faces.addMessage(clientId, msg);
					} else {
						messageContext.add(msg);
					}
				}
			}
			validationContext.addValidation((Boolean) ret);
			return ret;
		} catch (Exception e) {
			logger.debug("A chamada ao método de validação: {} lançou uma exception!", method.getName());
			validationContext.addValidation(false);
			e.printStackTrace();
		}
		return ret;
	}
}
