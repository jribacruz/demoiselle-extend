package br.gov.component.demoiselle.xcriteria.collect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

import br.gov.component.demoiselle.xcriteria.annotation.Attribute;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;

public class XCollection {
	@SuppressWarnings("unused")
	public static <X, T> List<T> transform(BeanMap beanMap, CriteriaBuilder cb, Root<X> p, List<XPredicate<T>> predicates) {
		List<T> predicateList = new ArrayList<T>();

		for (Field field : beanMap.getBean().getClass().getDeclaredFields()) {
			for (XPredicate<T> predicate : predicates) {
				Attribute attribute = field.getAnnotation(Attribute.class);
				String fieldName = attribute != null ? attribute.name() : field.getName();
				Object value = beanMap.get(field.getName());
				System.out.println("Check for: " + fieldName + "Value: " + value);
				if (predicate.check(Arrays.asList(field.getAnnotations()), field.getType() , fieldName, value)) {
					T predicateValue = predicate.apply(cb, p, fieldName, value);
					if (predicateValue != null) {
						predicateList.add(predicateValue);
					}
				}
			}
		}
		return predicateList;
	}
}
