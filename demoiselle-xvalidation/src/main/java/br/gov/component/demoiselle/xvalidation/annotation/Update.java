package br.gov.component.demoiselle.xvalidation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Update {
	@Nonbinding
	String success() default "";

	@Nonbinding
	String failure() default "";
}
