package br.gov.component.demoiselle.xvalidation.internal.qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface Domain {
	Class<?> value();
}
