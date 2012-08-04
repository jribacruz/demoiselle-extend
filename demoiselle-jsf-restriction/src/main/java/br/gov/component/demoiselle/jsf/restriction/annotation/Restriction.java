package br.gov.component.demoiselle.jsf.restriction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Restriction {

	@SuppressWarnings("rawtypes")
	Class<? extends RestrictionBean> value();
}
