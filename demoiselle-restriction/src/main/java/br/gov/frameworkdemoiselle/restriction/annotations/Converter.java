package br.gov.frameworkdemoiselle.restriction.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Converter {
	Class<? extends Converter> converter();

	@SuppressWarnings("rawtypes")
	Class<? extends Enum> targetClass() default Enum.class;
}
