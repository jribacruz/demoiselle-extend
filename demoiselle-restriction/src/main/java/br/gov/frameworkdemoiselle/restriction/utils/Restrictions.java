package br.gov.frameworkdemoiselle.restriction.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.convert.Converter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.restriction.annotations.Required;
import br.gov.frameworkdemoiselle.restriction.annotations.Value;
import br.gov.frameworkdemoiselle.restriction.converter.EnumConverter;
import br.gov.frameworkdemoiselle.restriction.converter.StringConverter;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;

public class Restrictions {

	public static boolean isRequiredRestriction(Field field) {
		return field.isAnnotationPresent(Required.class) ? true : false;
	}

	@SuppressWarnings("unchecked")
	public static <T, X, C extends CriteriaBean<T>> Set<RestrictionBean<T, X>> getBean(Field field, C criteriaBean) {
		Set<RestrictionBean<T, X>> beans = new HashSet<RestrictionBean<T, X>>();
		RestrictionBean<T, X> bean = (RestrictionBean<T, X>) Reflections.getFieldValue(field, criteriaBean);
		applyValue(field, bean);
		applyField(field, bean, beans);
		return beans;
	}

	@SuppressWarnings("unchecked")
	public static <T, X, C extends CriteriaBean<T>> Set<RestrictionBean<T, X>> getBean(Field field, C criteriaBean,
			Map<String, String> filters) {
		Set<RestrictionBean<T, X>> beans = new HashSet<RestrictionBean<T, X>>();
		RestrictionBean<T, X> bean = (RestrictionBean<T, X>) Reflections.getFieldValue(field, criteriaBean);
		if (field.isAnnotationPresent(br.gov.frameworkdemoiselle.restriction.annotations.Field.class)) {
			for (int i = 0; i < field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Field.class).value().length; i++) {
				RestrictionBean<T, X> cpbean = Reflections.instantiate(bean.getClass());
				cpbean.setField(field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Field.class).value()[i]);
				cpbean.setSelection(bean.isSelection());
				cpbean.setValue((X) filters.get(cpbean.getField()));
				beans.add(cpbean);
			}
		}
		return beans;
	}

	@SuppressWarnings("rawtypes")
	public static <T, X> boolean isEmptyValue(RestrictionBean<T, X> bean) {
		if (bean.getValue().getClass() == String.class) {
			String value = (String) bean.getValue();
			return StringUtils.isEmpty(value);
		} else if (Collection.class.isAssignableFrom(bean.getValue().getClass())) {
			Collection collection = (Collection) bean.getValue();
			return collection.isEmpty();
		}
		return true;
	}

	public static <T, X> Predicate callPredicate(RestrictionBean<T, X> bean, CriteriaBuilder cb, Root<T> p) {
		return !Restrictions.isEmptyValue(bean) ? bean.restriction(cb, p) : null;
	}

	@SuppressWarnings("rawtypes")
	private static Converter getConverter(Field field) {

		if (field.isAnnotationPresent(br.gov.frameworkdemoiselle.restriction.annotations.Converter.class)) {
			Class<? extends Converter> converterClass = field.getAnnotation(
					br.gov.frameworkdemoiselle.restriction.annotations.Converter.class).converter();
			if (converterClass == EnumConverter.class) {
				Class<? extends Enum> targetClass = field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Converter.class)
						.targetClass();
				EnumConverter enumConverter = new EnumConverter(targetClass);
				return enumConverter;
			} else {
				return Reflections.instantiate(converterClass);
			}
		}
		return new StringConverter();
	}

	@SuppressWarnings("unchecked")
	private static <T, X> void applyValue(Field field, RestrictionBean<T, X> bean) {
		if (field.isAnnotationPresent(Value.class)) {
			String value = field.getAnnotation(Value.class).value();
			Converter converter = getConverter(field);
			bean.setValue((X) converter.getAsObject(null, null, value));
		}
	}

	@SuppressWarnings("unchecked")
	private static <T, X> void applyField(Field field, RestrictionBean<T, X> bean, Set<RestrictionBean<T, X>> beans) {
		if (field.isAnnotationPresent(br.gov.frameworkdemoiselle.restriction.annotations.Field.class)) {
			for (int i = 0; i < field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Field.class).value().length; i++) {
				RestrictionBean<T, X> cpbean = Reflections.instantiate(bean.getClass());
				cpbean.setField(field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Field.class).value()[i]);
				cpbean.setSelection(bean.isSelection());
				cpbean.setValue(bean.getValue());
				beans.add(cpbean);
			}
		}
	}

}
