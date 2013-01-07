package br.gov.frameworkdemoiselle.restriction.internal.predicates;

import java.lang.reflect.Field;

import br.gov.frameworkdemoiselle.restriction.annotation.Required;
import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;

import com.google.common.base.Predicate;

public class RequiredRestrictionPredicate implements Predicate<Field> {

	@Override
	public boolean apply(Field field) {
		return RestrictionBean.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Required.class);
	}

}
