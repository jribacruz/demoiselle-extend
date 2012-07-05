package br.gov.component.demoiselle.jsf.criteria.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.criteria.processor.CriteriaProcessor;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.util.Reflections;

@Decorator
public class JPACrudDecorator<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	@Any
	private JPACrud<T, I> dao;

	@Inject
	private CriteriaProcessor processor;

	@Inject
	private Logger log;

	@Override
	public List<T> findAll() {
		Class<T> beanClass = Reflections.getGenericTypeArgument(dao.getClass(), 0);
		return processor.getResultList(beanClass);
	}

	@Override
	public void insert(T entity) {
		dao.insert(entity);
	}

	@Override
	public void delete(I id) {
		dao.delete(id);
	}

	@Override
	public void update(T entity) {
		dao.update(entity);
	}

	@Override
	public T load(I id) {
		return dao.load(id);
	}

}
