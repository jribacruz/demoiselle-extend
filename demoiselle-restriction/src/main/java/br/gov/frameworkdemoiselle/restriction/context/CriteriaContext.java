package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public interface CriteriaContext extends Serializable {
	public <T> Class<? extends CriteriaBean<T>> getCriteriaClass();

	public <T> void setCriteriaClass(Class<? extends CriteriaBean<T>> criteriaClass);

	public <T> CriteriaBean<T> getCriteria(Class<?> criteriaClass);

	public <T> void setCriteria(Class<?> criteriaClass, CriteriaBean<T> criteriaBean);

}
