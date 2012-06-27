package br.gov.component.demoiselle.jsf.criteria.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.gov.component.demoiselle.jsf.criteria.annotation.Criteria;
import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;

@Interceptor
public class CriteriaInterceptor {

	@Inject
	private CriteriaContext context;

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Criteria criteria = ctx.getMethod().getAnnotation(Criteria.class);
		context.setCriteria(criteria.value());
		context.setPageSize(criteria.pageSize());
		return ctx.proceed();
	}

}
