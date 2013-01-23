package br.gov.frameworkdemoiselle.restriction2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.restriction2.converters.StringConverter;
import br.gov.frameworkdemoiselle.restriction2.types.DefaultEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Converter {

	Class<? extends javax.faces.convert.Converter> value() default StringConverter.class;

	@SuppressWarnings("rawtypes")
	Class<? extends Enum> targetClass() default DefaultEnum.class;
}
