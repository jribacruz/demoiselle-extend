package br.gov.frameworkdemoiselle.restriction;

public interface CriteriaContext {
	public <C> void addCriteria(Class<?> klass, C criteriaBean);

	public <C> C getCriteriaBean();
}
