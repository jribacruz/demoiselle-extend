package br.gov.component.demoiselle.xjpa.internal.context;

import java.io.Serializable;

import javax.persistence.TypedQuery;

import br.gov.component.demoiselle.xjpa.Criterion;

public interface CriteriaContext extends Serializable {
	void criteria(Class<? extends Criterion> criteria);

	void paginate(boolean flag);

	boolean isPaginated();

	void clearAll();

	void clearParams();

	<T> TypedQuery<T> getQuery(Class<T> bean);

	<T> TypedQuery<Long> getQueryCount(Class<T> bean);

	void addCriteriaParam(String key, Object value);

	Object getCriteriaParam(String key);

}
