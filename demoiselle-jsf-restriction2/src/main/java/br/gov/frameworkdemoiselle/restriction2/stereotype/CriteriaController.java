package br.gov.frameworkdemoiselle.restriction2.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.annotation.ViewScoped;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Stereotype
@ViewScoped
@Named
public @interface CriteriaController {
}
