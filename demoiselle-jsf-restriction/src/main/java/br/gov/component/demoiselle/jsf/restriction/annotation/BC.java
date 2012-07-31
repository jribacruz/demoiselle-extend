package br.gov.component.demoiselle.jsf.restriction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.template.DelegateCrud;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE })
public @interface BC {
	@SuppressWarnings("rawtypes")
	Class<? extends DelegateCrud> value();
}
