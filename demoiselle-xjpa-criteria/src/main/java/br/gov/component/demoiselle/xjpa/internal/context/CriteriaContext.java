package br.gov.component.demoiselle.xjpa.internal.context;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.TypedQuery;

import br.gov.component.demoiselle.xjpa.CriteriaOrder;
import br.gov.component.demoiselle.xjpa.Criterion;

public interface CriteriaContext extends Serializable {
	@SuppressWarnings("rawtypes")
	void criteria(Class<? extends Criterion> criteria);

	<E> void criteria(Class<E> beanClass, CriteriaOrder criteriaOrder, String sortField, Map<String, String> filters);

	void paginate(boolean flag);

	boolean isPaginated();

	void clearAll();

	void clearParams();

	CriteriaOrder getOrder();

	<T> TypedQuery<T> getQuery(Class<T> bean);

	<T> TypedQuery<Long> getQueryCount(Class<T> bean);

	void addParam(String key, Object value);

	Object getParam(String key);

}
