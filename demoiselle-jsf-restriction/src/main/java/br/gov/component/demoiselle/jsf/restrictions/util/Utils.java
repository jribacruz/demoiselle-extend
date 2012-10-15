package br.gov.component.demoiselle.jsf.restrictions.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.MethodUtils;

import br.gov.component.demoiselle.jsf.restriction.annotation.Restriction;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class Utils {

	@SuppressWarnings("unchecked")
	public static <T> List<T> invokeMethodReturnCollection(Object bean, String name, Object... args) {
		// TODO inserir codigo para verificar se o metodo realmente existe
		try {
			return (List<T>) MethodUtils.invokeMethod(bean, name, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Object bean, String name, Object... args) {
		// TODO inserir codigo para verificar se o metodo realmente existe
		try {
			return (T) MethodUtils.invokeMethod(bean, name, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> Class<T> getDomainBeanClass(Object obj) {
		return Reflections.getGenericTypeArgument(obj.getClass(), 0);
	}

	public static <T> String getId(Class<T> beanClass) {
		for (Field field : beanClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return field.getName();
			}
		}
		return null;
	}

	public static List<Field> getRestrictionFields(Class<?> klass) {
		List<Field> fields = new ArrayList<Field>();

		for (Field field : Reflections.getNonStaticDeclaredFields(klass)) {
			if (field.isAnnotationPresent(Restriction.class)) {
				fields.add(field);
			}
		}
		return fields;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Predicate processRestriction(RestrictionBean restrictionBean, CriteriaBuilder cb, Root<T> p) {

		if (restrictionBean.getValue().getClass() == String.class) {
			if (!Strings.isEmpty((String) restrictionBean.getValue())) {
				if (restrictionBean.restriction(cb, p) != null) {
					return restrictionBean.restriction(cb, p);
				}
			}

		} else if (restrictionBean.getValue() instanceof Collection) {
			Collection collection = (Collection) restrictionBean.getValue();
			if (!collection.isEmpty()) {
				if (restrictionBean.restriction(cb, p) != null) {
					return restrictionBean.restriction(cb, p);
				}
			}
		} else {
			if (restrictionBean.restriction(cb, p) != null) {
				return restrictionBean.restriction(cb, p);
			}
		}
		return null;
	}

}
