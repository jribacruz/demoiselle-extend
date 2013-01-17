package br.gov.frameworkdemoiselle.restriction.utils;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.restriction.converter.EnumConverter;
import br.gov.frameworkdemoiselle.restriction.converter.StringConverter;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;

public class RestrictionUtils {
	public static String[] getFields(Field field) {
		return field.getAnnotation(br.gov.frameworkdemoiselle.restriction.annotations.Field.class).value();
	}

	public static Converter getConverter(Field field) {
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
	public static <T, X> RestrictionBean<T, X> getRestrictionBean(Field field, CriteriaBean<T> bean) {
		return (RestrictionBean<T, X>) Reflections.getFieldValue(field, bean);
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
}