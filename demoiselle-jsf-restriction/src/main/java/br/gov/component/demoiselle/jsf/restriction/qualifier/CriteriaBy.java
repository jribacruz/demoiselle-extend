package br.gov.component.demoiselle.jsf.restriction.qualifier;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;

@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface CriteriaBy {
	
	@SuppressWarnings("rawtypes")
	@Nonbinding
	Class<? extends AbstractCriteriaBean> value();
}
