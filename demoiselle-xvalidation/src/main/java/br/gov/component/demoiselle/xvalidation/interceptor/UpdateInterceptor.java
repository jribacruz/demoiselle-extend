package br.gov.component.demoiselle.xvalidation.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

import br.gov.component.demoiselle.xvalidation.annotation.Update;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.util.Strings;

@Controller
@Update(failure = "", success = "")
@Interceptor
public class UpdateInterceptor {

	@Inject
	private ValidationContext validationContext;

	@AroundInvoke
	public Object handle(InvocationContext ctx) {
		Method method = ctx.getMethod();
		Update update = method.getAnnotation(Update.class);
		System.out.println("Update Interceptor");
		validationContext.clearValidation();
		Object ret = null;
		try {
			ret = ctx.proceed();
			System.out.println("Depois do proceed Update");
			RequestContext requestContext = RequestContext.getCurrentInstance();
			if (validationContext.isAllValid()) {
				String updateSuccessId = update.success();
				if (!Strings.isEmpty(updateSuccessId)) {
					List<String> updateIdList = new ArrayList<String>();
					updateIdList.addAll(Arrays.asList(StringUtils.split(updateSuccessId, ", ")));
					requestContext.update(updateIdList);
				}
			} else {
				String updateFailureId = update.failure();
				if (!Strings.isEmpty(updateFailureId)) {
					List<String> updateIdList = new ArrayList<String>();
					updateIdList.addAll(Arrays.asList(StringUtils.split(updateFailureId, ", ")));
					requestContext.update(updateIdList);
				}
			}
			return ret;

		} catch (Exception e) {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			String updateFailureId = update.failure();
			if (!Strings.isEmpty(updateFailureId)) {
				List<String> updateIdList = new ArrayList<String>();
				updateIdList.addAll(Arrays.asList(StringUtils.split(updateFailureId, ", ")));
				requestContext.update(updateIdList);
			}
			return ret;
		}
	}

}
