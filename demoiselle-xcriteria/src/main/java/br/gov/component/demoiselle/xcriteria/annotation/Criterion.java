package br.gov.component.demoiselle.xcriteria.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@ViewScoped
public @interface Criterion {

}
