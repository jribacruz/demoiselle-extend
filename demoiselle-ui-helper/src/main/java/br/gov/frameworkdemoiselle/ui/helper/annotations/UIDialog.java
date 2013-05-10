package br.gov.frameworkdemoiselle.ui.helper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD })
public @interface UIDialog {

	String formId() default "";

	String id() default "";

	String widgetVar() default "";

	String bodyId() default "";
}
