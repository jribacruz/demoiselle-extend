package br.gov.frameworkdemoiselle.jsf.view.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface Dialog {
	String formId() default "";

	String id() default "";

	String bodyId() default "";

	String widgetVar() default "";
}
