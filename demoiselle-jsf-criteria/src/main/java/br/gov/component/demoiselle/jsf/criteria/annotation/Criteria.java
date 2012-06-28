package br.gov.component.demoiselle.jsf.criteria.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@InterceptorBinding
public @interface Criteria {
	@Nonbinding
	Class<?> value();

	@Nonbinding
	int pageSize() default 0;
}
