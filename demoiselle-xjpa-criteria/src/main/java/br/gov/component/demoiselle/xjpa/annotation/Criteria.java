package br.gov.component.demoiselle.xjpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@ViewScoped
public @interface Criteria {

}
