package br.gov.component.demoiselle.jsf.restriction.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.annotation.Criteria;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;

@Interceptor
@Criteria(AbstractCriteriaBean.class)
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
		Class<? extends AbstractCriteriaBean> criteriaClass = context.getCriteriaControllerClass();
		Class<? extends AbstractCriteriaBean> criteriaSelectedClass = ctx.getMethod().getAnnotation(Criteria.class).value();
		if (criteriaClass != criteriaSelectedClass) {
			context.setCriteriaControllerClass(criteriaSelectedClass);
			logger.info("Criteria {} no método {}", criteriaSelectedClass.getCanonicalName() , ctx.getMethod().getName());
		}

		return ctx.proceed();
	}
}
