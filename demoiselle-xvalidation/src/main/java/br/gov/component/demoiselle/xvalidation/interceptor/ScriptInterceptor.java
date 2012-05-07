package br.gov.component.demoiselle.xvalidation.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import br.gov.component.demoiselle.xvalidation.annotation.Script;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.util.Strings;

@Script
@Interceptor
public class ScriptInterceptor {
	@Inject
	private ValidationContext validationContext;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object handle(InvocationContext ctx) {
		Method method = ctx.getMethod();
		Script script = method.getAnnotation(Script.class);
		validationContext.clearValidation();
		Object ret = null;
		try {
			ret = ctx.proceed();
			RequestContext requestContext = RequestContext.getCurrentInstance();
			if (validationContext.isAllValid()) {
				logger.debug("Todas as validações validas. Adicionando script de sucesso ");
				String scriptSuccess = script.success();
				if (!Strings.isEmpty(scriptSuccess)) {
					logger.debug("Script executado no sucesso : {} " + scriptSuccess);
					requestContext.execute(scriptSuccess);
				}
			} else {
				logger.debug("Alguma a validação invalida. Adicionando script de falha ");
				String scriptFailure = script.failure();
				if (!Strings.isEmpty(scriptFailure)) {
					logger.debug("Script executado na falha : {} " + scriptFailure);
					requestContext.execute(scriptFailure);
				}
			}
			return ret;
		} catch (Exception e) {
			logger.debug("Exception disparada. Adicionando script de falha ");
			validationContext.addValidation(false);
			RequestContext requestContext = RequestContext.getCurrentInstance();
			String scriptFailure = script.failure();
			if (!Strings.isEmpty(scriptFailure)) {
				logger.debug("Script executado na falha : {} " + scriptFailure);
				requestContext.execute(scriptFailure);
			}
			return ret;
		}
	}
}
