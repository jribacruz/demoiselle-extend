package br.gov.component.demoiselle.jsf.criteria.decorator;

import java.util.List;

import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.template.Crud;

public class JPACrudDecorator<T, I> implements Crud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	@Any
	private Crud<T, I> dao;

	public void delete(I id) {
		dao.delete(id);
	}

	public List<T> findAll() {
		System.out.println("Interceptando a chamada do findAll()");
		return null;
	}

	public void insert(T entity) {
		dao.insert(entity);
	}

	public T load(I entity) {
		return dao.load(entity);
	}

	public void update(T entity) {
		dao.update(entity);
	}

}
