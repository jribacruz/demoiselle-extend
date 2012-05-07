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
import org.slf4j.Logger;

import br.gov.component.demoiselle.xvalidation.annotation.Update;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.frameworkdemoiselle.util.Strings;

@Update
@Interceptor
public class UpdateInterceptor {

	@Inject
	private ValidationContext validationContext;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object handle(InvocationContext ctx) {
		Method method = ctx.getMethod();
		Update update = method.getAnnotation(Update.class);
		validationContext.clearValidation();
		Object ret = null;
		try {
			ret = ctx.proceed();
			RequestContext requestContext = RequestContext.getCurrentInstance();
			if (validationContext.isAllValid()) {
				logger.debug("Todas as validações validas. Adicionando update de sucesso ");
				String updateSuccessId = update.success();
				if (!Strings.isEmpty(updateSuccessId)) {
					List<String> updateIdList = new ArrayList<String>();
					logger.debug("Lista de ids para atualização no sucesso: {} "+updateSuccessId);
					updateIdList.addAll(Arrays.asList(StringUtils.split(updateSuccessId, ", ")));
					requestContext.update(updateIdList);

				}
			} else {
				logger.debug("Alguma validação invalida. Adicionando update de falha ");
				String updateFailureId = update.failure();
				if (!Strings.isEmpty(updateFailureId)) {
					List<String> updateIdList = new ArrayList<String>();
					logger.debug("Lista de ids para atualização na falha: {} "+updateFailureId);
					updateIdList.addAll(Arrays.asList(StringUtils.split(updateFailureId, ", ")));
					requestContext.update(updateIdList);
				}
			}
			return ret;

		} catch (Exception e) {
			logger.debug("Exception disparada. Adicionando update de falha ");
			validationContext.addValidation(false);
			RequestContext requestContext = RequestContext.getCurrentInstance();
			String updateFailureId = update.failure();
			if (!Strings.isEmpty(updateFailureId)) {
				List<String> updateIdList = new ArrayList<String>();
				logger.debug("Lista de ids para atualização na falha: {} "+updateFailureId);
				updateIdList.addAll(Arrays.asList(StringUtils.split(updateFailureId, ", ")));
				requestContext.update(updateIdList);
			}
			return ret;
		}
	}

}
