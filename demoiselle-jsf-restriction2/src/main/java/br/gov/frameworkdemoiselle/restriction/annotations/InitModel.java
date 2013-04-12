package br.gov.frameworkdemoiselle.restriction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.restriction.core.Criteria;
import br.gov.frameworkdemoiselle.restriction.custom.criteria.DefaultDataTableCriteria;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface InitModel {

	@SuppressWarnings("rawtypes")
	Class<? extends Criteria> criteria() default DefaultDataTableCriteria.class;

	Attribute[] attributes();

	Order[] order() default {};
}
