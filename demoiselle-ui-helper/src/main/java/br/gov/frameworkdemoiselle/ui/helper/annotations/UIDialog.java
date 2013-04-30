package br.gov.frameworkdemoiselle.ui.helper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD })
public @interface UIDialog {

	String formId();

	String id();

	String widgetVar();

	String bodyId();
}
