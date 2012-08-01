package br.gov.component.demoiselle.events;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.gov.component.demoiselle.events.templates.ActionEventBean;

@Inherited
@Target(METHOD)
@Retention(RUNTIME)
public @interface OnComplete {
	Class<? extends ActionEventBean> value();
}
