package br.gov.frameworkdemoiselle.restriction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.restriction.AbstractCriteria;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface CriteriaBy {

	@SuppressWarnings("rawtypes")
	Class<? extends AbstractCriteria> value();

}
