package br.gov.component.demoiselle.xcriteria;

import java.util.List;

import javax.persistence.TypedQuery;

public interface CriteriaProcessor {
	public <T> List<T> findAll(Class<T> beanClass);

	public <T> TypedQuery<T> getQuery(Class<T> bean);

	public <T> TypedQuery<Long> getQueryCount(Class<T> bean);
}
