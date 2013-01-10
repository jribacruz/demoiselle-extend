package br.gov.frameworkdemoiselle.restriction.context;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public interface CriteriaContext extends Serializable {
	public Class<? extends CriteriaBean<?>> getCriteriaClass();

	public void registerCriteriaClass(Class<? extends CriteriaBean<?>> criteriaClass);

	public CriteriaBean<?> getCriteria(Class<?> criteriaClass);

	public void registerCriteria(Class<?> criteriaClass, CriteriaBean<?> criteriaBean);

	public boolean isPaginated();

}
