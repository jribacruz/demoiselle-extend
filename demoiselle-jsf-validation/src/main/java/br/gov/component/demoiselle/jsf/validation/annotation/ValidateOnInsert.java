package br.gov.component.demoiselle.jsf.validation.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Target(METHOD)
@Retention(RUNTIME)
public @interface ValidateOnInsert {
	String message();
	String clientId() default "";
}