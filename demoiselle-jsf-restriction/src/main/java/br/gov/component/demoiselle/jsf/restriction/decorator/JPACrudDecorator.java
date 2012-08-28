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
		return processor.hasCriteria() || processor.hasProjection() ? processor.getResultList(getDomainBeanClass()) : dao
				.findAll();
	}

	@Override
	public T load(I id) {
		return processor.hasCriteria() || processor.hasProjection() ? processor.load(getDomainBeanClass(), id) : dao
				.load(id);
	}

	private Class<T> getDomainBeanClass() {
		return Reflections.getGenericTypeArgument(dao.getClass(), 0);
	}

}
