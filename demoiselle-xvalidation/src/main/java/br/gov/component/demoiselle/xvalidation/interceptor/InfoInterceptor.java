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

import br.gov.component.demoiselle.xvalidation.Param;
import br.gov.component.demoiselle.xvalidation.annotation.Info;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.util.Strings;

@Interceptor
@Info(message="", updateId="")
public class InfoInterceptor {
	@Inject
	private MessageContext messageContext;

	@Inject
	private Param param;

	@AroundInvoke
	public Object handle(InvocationContext context) throws Exception {
		Method method = context.getMethod();
		Info infoAnnotation = method.getAnnotation(Info.class);
		Object ret = context.proceed();
		if (infoAnnotation != null) {
			String message = infoAnnotation.message();
			String updateId = infoAnnotation.updateId();

			messageContext.add(new DefaultMessage(message, param.get()));
			processUpdateId(updateId);

			return ret;
		}
		return ret;
	}

	private void processUpdateId(String updateId) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (!Strings.isEmpty(updateId)) {
			List<String> updateIdList = new ArrayList<String>();
			updateIdList.addAll(Arrays.asList(StringUtils.split(updateId, ", ")));
			requestContext.update(updateIdList);
		}
	}
}
