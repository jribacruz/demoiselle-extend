package br.gov.frameworkdemoiselle.restriction.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.gov.frameworkdemoiselle.configuration.ConfigurationException;
import br.gov.frameworkdemoiselle.restriction.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.annotations.Attribute;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

public class Reflections {
	@SuppressWarnings("rawtypes")
	public static <T> List<RestrictionBean> getRestrictionBeans(Class<? extends CriteriaBean<T>> criteriaClass) {
		List<RestrictionBean> beans = new ArrayList<RestrictionBean>();
		CriteriaBean<T> bean = Beans.getReference(criteriaClass);
//		
//		for (Field field : br.gov.frameworkdemoiselle.util.Reflections.getNonStaticDeclaredFields(criteriaInstance.getClass())) {
//			if (RestrictionBean.class.isAssignableFrom(field.getType())) {
//				RestrictionBean bean = (RestrictionBean) br.gov.frameworkdemoiselle.util.Reflections.getFieldValue(field, criteriaInstance);
//				if (bean != null) {
//					setValues(bean, field);
//					beans.add(bean);
//				}
//			}
//		}
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

	@SuppressWarnings("rawtypes")
	public static <T> RestrictionBean getRestrictionBean(CriteriaBean<T> criteriaBean, Class<?> fieldClass) {
		RestrictionBean value = null;

		try {
			Method method;
			String methodName = "get" + Strings.firstToUpper(fieldClass.getSimpleName());

			method = criteriaBean.getClass().getMethod(methodName, null);
			value = (RestrictionBean) method.invoke(criteriaBean, null);
//			if (!fieldClass.isPrimitive()) {
//				method = criteriaBean.getClass().getMethod(methodName, String.class, fieldClass);
//				value = method.invoke(criteriaBean, key, null);
//			} else if (criteriaBean.containsKey(key)) {
//				method = criteriaBean.getClass().getMethod(methodName, String.class);
//				value = method.invoke(criteriaBean, key);
//			}

		} catch (Throwable cause) {
			cause.printStackTrace();
		}

		return value;
	}

}
