package br.gov.frameworkdemoiselle.restriction.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.restriction.internal.qualifier.CriteriaQualifier;
import br.gov.frameworkdemoiselle.template.JPACrud;

@Decorator
public class CriteriaDecorator<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	private JPACrud<T, I> dao;
	
	@Inject
	@CriteriaQualifier
	private JPACrud<T, I> proxy;

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

	@Override
	public List<T> findAll() {
		return dao.findAll();
	}

}
