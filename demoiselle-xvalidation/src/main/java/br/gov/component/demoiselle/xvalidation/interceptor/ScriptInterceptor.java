package br.gov.component.demoiselle.xvalidation.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.primefaces.context.RequestContext;

import br.gov.component.demoiselle.xvalidation.annotation.Script;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.util.Strings;

@Controller
@Script(failure = "", success = "")
@Interceptor
public class ScriptInterceptor {
	@Inject
	private ValidationContext validationContext;

	@AroundInvoke
	public Object handle(InvocationContext ctx) {
		Method method = ctx.getMethod();
		Script script = method.getAnnotation(Script.class);

		Object ret = null;
		try {
			validationContext.clearValidation();
			ret = ctx.proceed();
			RequestContext requestContext = RequestContext.getCurrentInstance();
			if (validationContext.isAllValid()) {
				String scriptSuccess = script.success();
				if (!Strings.isEmpty(scriptSuccess)) {
					requestContext.execute(scriptSuccess);
				}
			} else {
				String scriptFailure = script.failure();
				if (!Strings.isEmpty(scriptFailure)) {
					requestContext.execute(scriptFailure);
				}
			}
			return ret;
		} catch (Exception e) {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			String scriptFailure = script.failure();
			if (!Strings.isEmpty(scriptFailure)) {
				requestContext.execute(scriptFailure);
			}
			return ret;
		}
	}
}
