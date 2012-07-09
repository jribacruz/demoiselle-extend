package br.gov.component.demoiselle.jsf.validation.processor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.validation.annotation.ValidateOnDelete;
import br.gov.component.demoiselle.jsf.validation.annotation.ValidateOnInsert;
import br.gov.component.demoiselle.jsf.validation.annotation.ValidateOnSave;
import br.gov.component.demoiselle.jsf.validation.annotation.ValidateOnUpdate;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.util.Faces;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Validator implements Serializable {
	private static final long serialVersionUID = 1L;

	private Multimap<Class<?>, Method> cache;

	@Inject
	private MessageContext messageContext;

	@Inject
	private Logger log;

	public Validator() {
		super();
		this.cache = ArrayListMultimap.create();
	}

	public <T, X> boolean validateOnSave(X bc, T bean) {
		loadOnSaveValidators(bc.getClass().getSuperclass());
		Collection<Method> methods = cache.get(ValidateOnSave.class);
		int validationFailureCount = 0;
		for (Method method : methods) {
			if (invoke(method, bc, bean) == false) {
				log.info("Validação falhou no update ou insert com o metodo: {}", method.getName());
				String message = method.getAnnotation(ValidateOnSave.class).message();
				String clientId = method.getAnnotation(ValidateOnSave.class).clientId();
				sendContextMessage(message, clientId);
				validationFailureCount++;
			}
		}
		return validationFailureCount == 0;
	}

	public <T, X> boolean validateOnInsert(X bc, T bean) {
		loadOnInsertValidators(bc.getClass().getSuperclass());
		Collection<Method> methods = cache.get(ValidateOnInsert.class);
		int validationFailureCount = 0;
		for (Method method : methods) {
			if (invoke(method, bc, bean) == false) {
				log.info("Validação falhou no insert com o metodo: {}", method.getName());
				String message = method.getAnnotation(ValidateOnInsert.class).message();
				String clientId = method.getAnnotation(ValidateOnInsert.class).clientId();
				sendContextMessage(message, clientId);
				validationFailureCount++;
			}
		}
		return validationFailureCount == 0;
	}

	public <T, X> boolean validateOnUpdate(X bc, T bean) {
		loadOnUpdateValidators(bc.getClass().getSuperclass());
		Collection<Method> methods = cache.get(ValidateOnUpdate.class);
		int validationFailureCount = 0;
		for (Method method : methods) {
			if (invoke(method, bc, bean) == false) {
				log.info("Validação falhou no update com o metodo: {}", method.getName());
				String message = method.getAnnotation(ValidateOnUpdate.class).message();
				String clientId = method.getAnnotation(ValidateOnUpdate.class).clientId();
				sendContextMessage(message, clientId);
				validationFailureCount++;
			}
		}
		return validationFailureCount == 0;
	}

	public <T, X> boolean validateOnDelete(X bc, T id) {
		loadOnDeleteValidators(bc.getClass().getSuperclass());
		Collection<Method> methods = cache.get(ValidateOnDelete.class);
		int validationFailureCount = 0;
		for (Method method : methods) {
			if (invoke(method, bc, id) == false) {
				log.info("Validação falhou no delete com o metodo: {}", method.getName());
				String message = method.getAnnotation(ValidateOnDelete.class).message();
				String clientId = method.getAnnotation(ValidateOnDelete.class).clientId();
				sendContextMessage(message, clientId);
				validationFailureCount++;
			}
		}
		return validationFailureCount == 0;
	}

	private void sendContextMessage(String message, String clientId) {
		messageContext.clear();
		DefaultMessage defaultMessage = new DefaultMessage(message, SeverityType.ERROR);
		if (Strings.isEmpty(clientId)) {
			messageContext.add(defaultMessage);
		} else {
			Faces.addMessage(clientId, defaultMessage);
		}
	}

	private void loadOnSaveValidators(Class<?> bcClass) {
		Method[] methods = bcClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(ValidateOnSave.class)) {
				cache.put(ValidateOnSave.class, method);
			}
		}
	}

	private void loadOnInsertValidators(Class<?> bcClass) {
		Method[] methods = bcClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(ValidateOnInsert.class)) {
				cache.put(ValidateOnInsert.class, method);
			}
		}
	}

	private void loadOnUpdateValidators(Class<?> bcClass) {
		Method[] methods = bcClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(ValidateOnUpdate.class)) {
				cache.put(ValidateOnUpdate.class, method);
			}
		}
	}

	private void loadOnDeleteValidators(Class<?> bcClass) {
		Method[] methods = bcClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(ValidateOnDelete.class)) {
				cache.put(ValidateOnDelete.class, method);
			}
		}
	}

	private <T> boolean invoke(final Method method, final Object object, final T param) {
		return method.getParameterTypes().length == 0 ? invokeValidator(method, object) : invokeValidator(method, object,
				param);
	}

	private <T> boolean invokeValidator(final Method method, final Object object, final T param) {
		boolean accessible = method.isAccessible();
		method.setAccessible(true);

		Boolean returnValue = false;
		try {
			returnValue = (Boolean) method.invoke(object, param);
			method.setAccessible(accessible);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	private boolean invokeValidator(final Method method, final Object object) {
		boolean accessible = method.isAccessible();
		method.setAccessible(true);

		Boolean returnValue = false;
		try {
			returnValue = (Boolean) method.invoke(object, new Object[] {});
			method.setAccessible(accessible);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return returnValue;
	}
}
