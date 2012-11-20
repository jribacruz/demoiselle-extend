package br.gov.component.demoiselle.jsf.restriction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
public @interface PredicateGroup {
	String name();

	OperatorType operator() default OperatorType.AND;
}
