package br.gov.frameworkdemoiselle.jsf.view.stereotype;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;

@Named
@Stereotype
@ViewScoped
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface UIView {

}
