package br.gov.component.demoiselle.jsf.restriction.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.gov.component.demoiselle.jsf.restriction.processor.JPAProcessor;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.template.JPACrud;

@Decorator
public abstract class JPACrudDecorator<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	@Any
	private JPACrud<T, I> dao;

	@Inject
	private JPAProcessor processor;

	/**
	 * Intercepta o método findAll() da classe JPACrud e verifica se há um
	 * critério ou projection no contexto do CriteriaProcessor. Em caso
	 * afirmativo processa as entidades com o criterio ou projecao especifico.
	 * Caso contrario, passa para o findAll() da classe JPACrud
	 */
	@Override
	public List<T> findAll() {
		return processor.hasCriteria() ? processor.findAll(Utils.<T> getDomainBeanClass(dao)) : dao.findAll();
	}

}
