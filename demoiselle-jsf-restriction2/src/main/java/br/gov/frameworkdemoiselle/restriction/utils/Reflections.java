package br.gov.frameworkdemoiselle.restriction.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.gov.frameworkdemoiselle.restriction.AbstractCriteria;
import br.gov.frameworkdemoiselle.restriction.annotations.Attribute;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Strings;

public class Reflections {
	@SuppressWarnings("rawtypes")
	public static <T> List<RestrictionBean> getRestrictionBeans(AbstractCriteria<T> criteriaInstance) {
		List<RestrictionBean> beans = new ArrayList<RestrictionBean>();

		for (Field field : br.gov.frameworkdemoiselle.util.Reflections.getNonStaticDeclaredFields(criteriaInstance.getClass())) {
			if (RestrictionBean.class.isAssignableFrom(field.getType())) {
				RestrictionBean bean = (RestrictionBean) br.gov.frameworkdemoiselle.util.Reflections.getFieldValue(field, criteriaInstance);
				if (bean != null) {
					setValues(bean, field);
					beans.add(bean);
				}
			}
		}
		return beans;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void setValues(RestrictionBean bean, Field field) {
		if (field.isAnnotationPresent(Attribute.class)) {
			String[] attributes = field.getAnnotation(Attribute.class).value();
			for (int i = 0; i < attributes.length; i++) {
				if (Strings.isEmpty(attributes[i])) {
					bean.getFields().add(attributes[i]);
				}
			}
		}
	}


}
