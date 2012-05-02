package br.gov.component.demoiselle.xvalidation.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.gov.component.demoiselle.xvalidation.Param;
import br.gov.component.demoiselle.xvalidation.annotation.Message;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.util.Strings;

@Controller
@Message(failure = "", success = "")
@Interceptor
public class MessageInterceptor {
	@Inject
	private ValidationContext validationContext;

	@Inject
	private MessageContext messageContext;

	@Inject
	private Param param;

	@AroundInvoke
	public Object handle(InvocationContext ctx) {
		Method method = ctx.getMethod();
		Message message = method.getAnnotation(Message.class);
		validationContext.clearValidation();
		Object ret = null;
		try {
			ret = ctx.proceed();
			if (validationContext.isAllValid()) {
				String messageSuccess = message.success();
				SeverityType type = message.type();
				if (!Strings.isEmpty(messageSuccess)) {
					messageContext.add(new DefaultMessage(messageSuccess, type, param.getSuccessParam()));
				}
			} else {
				String messageFailure = message.failure();
				SeverityType type = message.type();
				if (!Strings.isEmpty(messageFailure)) {
					messageContext.add(new DefaultMessage(messageFailure, type, param.getSuccessParam()));
				}
			}
			return ret;
		} catch (Exception e) {
			String messageFailure = message.failure();
			if (!Strings.isEmpty(messageFailure)) {
				messageContext.add(new DefaultMessage(messageFailure, param.getSuccessParam()));
			}
			return ret;
		}
	}
}
