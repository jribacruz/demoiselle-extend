package br.gov.component.demoiselle.jsf.criteria.context;

import br.gov.component.demoiselle.jsf.criteria.AbstractCriteria;

public interface CriteriaContext {
	public <T, X> void setCriteria(Class<? extends AbstractCriteria<T, X>> criteriaClass);

	public <T, X> void setCriteria(Class<? extends AbstractCriteria<T, X>> criteriaClass, int pageSize);

	public int getPageSize();
}
