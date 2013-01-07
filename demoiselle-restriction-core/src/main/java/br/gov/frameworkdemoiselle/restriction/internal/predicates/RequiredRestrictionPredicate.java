package br.gov.frameworkdemoiselle.restriction.internal.predicates;

import java.lang.reflect.Field;

import br.gov.frameworkdemoiselle.restriction.annotation.Required;

import com.google.common.base.Predicate;

public class RequiredRestrictionPredicate implements Predicate<Field> {

	@Override
	public boolean apply(Field field) {
		return field.isAnnotationPresent(Required.class);
	}

}
