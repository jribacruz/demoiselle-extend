package br.gov.component.demoiselle.jsf.restriction.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.annotation.Projection;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.template.ProjectionBean;

@Interceptor
@Projection(ProjectionBean.class)
public class ProjectionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@SuppressWarnings("unused")
	@Inject
	private Logger logger;

	@Inject
	public ProjectionInterceptor(Logger logger) {
		this.logger = logger;
	}

	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		Class<? extends ProjectionBean> projectionClass = getProjectionClass();
		Class<? extends ProjectionBean> projectionSelectedClass = getProjectionClass(ctx);
		if (projectionClass != projectionSelectedClass) {
			context.setProjectionClass(projectionSelectedClass);
		}
		return ctx.proceed();
	}

	@SuppressWarnings({ "rawtypes" })
	private Class<? extends ProjectionBean> getProjectionClass() {
		return context.getProjectionClass();
	}

	@SuppressWarnings("rawtypes")
	private Class<? extends ProjectionBean> getProjectionClass(InvocationContext ctx) {
		return ctx.getMethod().getAnnotation(Projection.class).value();
	}
}
