package br.gov.component.demoiselle.jsf.restriction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import br.gov.component.demoiselle.jsf.restriction.template.ProjectionBean;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@InterceptorBinding
public @interface Projection {
	@SuppressWarnings("rawtypes")
	@Nonbinding
	Class<? extends ProjectionBean> value();
}
