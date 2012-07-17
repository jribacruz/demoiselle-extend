package br.gov.component.demoiselle.jsf.criteria.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.criteria.annotation.Restriction;
import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.criteria.template.IRestriction;

@Interceptor
@Restriction(value = IRestriction.class)
public class CriteriaInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private Logger logger;

	@Inject
	public CriteriaInterceptor(Logger logger) {
		this.logger = logger;
	}

	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {

		Class<? extends IRestriction> restrictionClass = context.getRestrictionClass();
		Restriction restriction = ctx.getMethod().getAnnotation(Restriction.class);

		if (restrictionClass != restriction.value()) {
			context.setRestrictionClass(restriction.value());
			context.setPageSize(restriction.pageSize());
			logger.info("Restriction {} no método {}", restriction.value(), ctx.getMethod().getName());
		}

		return ctx.proceed();
	}
}
