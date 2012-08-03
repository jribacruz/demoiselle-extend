package br.gov.component.demoiselle.jsf.restriction.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.gov.component.demoiselle.jsf.restriction.processor.CriteriaProcessor;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.util.Reflections;

@Decorator
public abstract class JPACrudDecorator<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	@Any
	private JPACrud<T, I> dao;

	@Inject
	private CriteriaProcessor processor;

	@Override
	public List<T> findAll() {
		Class<T> beanClass = Reflections.getGenericTypeArgument(dao.getClass(), 0);
		return processor.getResultList(beanClass);
	}

	@Override
	public T load(I id) {
		Class<T> beanClass = Reflections.getGenericTypeArgument(dao.getClass(), 0);
		return processor.load(beanClass, id);
	}

}
