package br.gov.frameworkdemoiselle.restriction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.restriction.core.AbstractCriteria;
import br.gov.frameworkdemoiselle.restriction.custom.criteria.DefaultDataTableCriteria;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface CriteriaBy {

	@SuppressWarnings("rawtypes")
	Class<? extends AbstractCriteria> value() default DefaultDataTableCriteria.class;

}
