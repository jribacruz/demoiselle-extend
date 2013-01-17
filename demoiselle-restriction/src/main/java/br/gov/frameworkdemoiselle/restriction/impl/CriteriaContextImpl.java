package br.gov.frameworkdemoiselle.restriction.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.restriction.CriteriaContext;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>, Object> cache;

	@SuppressWarnings("unused")
	@Inject
	private void init() {
		this.cache = new HashMap<Class<?>, Object>();
	}

	@Override
	public <C extends CriteriaBean<?>> void addCriteria(Class<?> klass, C criteriaBean) {
		this.cache.put(klass, criteriaBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C extends CriteriaBean<?>> C getCriteriaBean(Class<?> klass) {
		return (C) this.cache.get(klass);
	}

}
