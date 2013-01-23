package br.gov.frameworkdemoiselle.restriction2.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.gov.frameworkdemoiselle.util.Strings;

public class ReflectionUtils {
	public static Method getGetter(Class<?> klass, Field field) {
		String methodName = "get" + Strings.firstToUpper(field.getName());
		try {
			return klass.getDeclaredMethod(methodName, null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invoke(Method method, Object bean) {
		try {
			return method.invoke(bean, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
