package br.gov.component.demoiselle.jsf.restriction.interceptor;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import br.gov.component.demoiselle.jsf.restriction.annotation.Criteria;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;

@Interceptor
@Criteria(AbstractCriteriaBean.class)
public class CriteriaInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private CriteriaProcessorContext processorContext;

	@SuppressWarnings("unused")
	@Inject
	private Logger logger;

	@Inject
	public CriteriaInterceptor(Logger logger) {
		this.logger = logger;
	}

	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Class<? extends AbstractCriteriaBean> criteriaClass = getContextCriteriaBeanClass();
		Class<? extends AbstractCriteriaBean> criteriaSelectedClass = getCriteriaBeanClass(ctx);
		int pageSize = getPageSize(ctx);
		if (criteriaClass != criteriaSelectedClass) {
			processorContext.setCriteriaControllerClass(criteriaSelectedClass);
			context.setPageSize(pageSize);
			setParamIfNecessary(ctx);
		}
		return ctx.proceed();
	}

	@SuppressWarnings({ "rawtypes" })
	private Class<? extends AbstractCriteriaBean> getContextCriteriaBeanClass() {
		return processorContext.getCriteriaControllerClass();
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractCriteriaBean> getCriteriaBeanClass(InvocationContext ctx) {
		return ctx.getMethod().getAnnotation(Criteria.class).value();
	}

	private int getPageSize(InvocationContext ctx) {
		return ctx.getMethod().getAnnotation(Criteria.class).pageSize();
	}

	private void setParamIfNecessary(InvocationContext ctx) {
		if (ctx.getParameters() != null && ctx.getParameters().length > 0) {
			if (ctx.getParameters()[0].getClass() == String.class) {
				context.setQuery((String) ctx.getParameters()[0]);
			} else if(ctx.getParameters()[0].getClass().isAssignableFrom(Collection.class)) {
				context.setCollection((Collection<?>) ctx.getParameters()[0]);
			}
		}
	}
}
