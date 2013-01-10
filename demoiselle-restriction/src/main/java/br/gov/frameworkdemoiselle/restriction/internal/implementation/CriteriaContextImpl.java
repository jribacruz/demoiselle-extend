package br.gov.frameworkdemoiselle.restriction.internal.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import br.gov.frameworkdemoiselle.restriction.context.CriteriaContext;
import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {

	private static final long serialVersionUID = 1L;

	private Class<? extends CriteriaBean<?>> criteriaClass;

	private Map<Class<?>, CriteriaBean<?>> criteriaMap;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		this.criteriaMap = new HashMap<Class<?>, CriteriaBean<?>>();
	}

	@Override
	public Class<? extends CriteriaBean<?>> getCriteriaClass() {
		return this.criteriaClass;
	}

	@Override
	public void registerCriteriaClass(Class<? extends CriteriaBean<?>> criteriaClass) {
		this.criteriaClass = criteriaClass;
	}

	@Override
	public CriteriaBean<?> getCriteria(Class<?> criteriaClass) {
		return this.criteriaMap.get(criteriaClass);
	}

	@Override
	public void registerCriteria(Class<?> criteriaClass, CriteriaBean<?> criteriaBean) {
		this.criteriaMap.put(criteriaClass, criteriaBean);
	}

	@Override
	public boolean isPaginated() {
		return this.criteriaMap.get(this.criteriaClass).isPaginated();
	}

}
