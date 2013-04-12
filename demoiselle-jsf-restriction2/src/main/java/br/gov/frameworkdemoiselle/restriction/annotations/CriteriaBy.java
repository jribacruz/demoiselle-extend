package br.gov.frameworkdemoiselle.restriction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.restriction.core.Criteria;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.TYPE })
public @interface CriteriaBy {
	
	@SuppressWarnings("rawtypes")
	Class<? extends Criteria> value();
}
