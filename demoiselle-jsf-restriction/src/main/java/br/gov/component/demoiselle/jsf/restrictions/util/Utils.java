package br.gov.component.demoiselle.jsf.restrictions.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.beanutils.MethodUtils;

import br.gov.frameworkdemoiselle.util.Reflections;

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

}
