package br.gov.component.demoiselle.jsf.criteria.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.criteria.annotation.Criteria;
import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;

@Interceptor
@Criteria(value = Void.class)
public class CriteriaInterceptor {

	@Inject
	private CriteriaContext context;

	@Inject
	private Logger log;

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Criteria criteria = ctx.getMethod().getAnnotation(Criteria.class);
		if (criteria != null) {
			context.setCriteria(criteria.value());
			context.setPageSize(criteria.pageSize());
			log.info("Restriction selecionada: {}", new Object[] { criteria.value().getSimpleName() });
		}
		return ctx.proceed();
	}

}
