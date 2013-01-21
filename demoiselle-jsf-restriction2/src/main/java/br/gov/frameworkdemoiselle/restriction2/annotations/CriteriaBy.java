package br.gov.frameworkdemoiselle.restriction2.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
public @interface CriteriaBy {

	@SuppressWarnings("rawtypes")
	@Nonbinding
	Class<? extends CriteriaBean> value();
}
