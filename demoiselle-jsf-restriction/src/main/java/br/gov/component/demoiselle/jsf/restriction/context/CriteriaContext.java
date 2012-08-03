package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.AbstractCriteriaBean;

public interface CriteriaContext extends Serializable {
	@SuppressWarnings("rawtypes")
	void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass);

	@SuppressWarnings("rawtypes")
	Class<? extends AbstractCriteriaBean> getCriteriaControllerClass();

	<T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p);

	<T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);

	<T> List<Order> getOrderList(CriteriaBuilder cb, Root<T> p);

	int getPageSize();

	void setPageSize(int size);

	void setQuery(String query);

	void setSortOrder(SortOrder sortOrder);

	SortOrder getSortOrder();

	void setFilters(Map<String, String> filters);

	public Map<String, String> getFilters();

	void setSortField(String sortField);

	String getSortField();

	public String getQuery();

	void clear();

}
