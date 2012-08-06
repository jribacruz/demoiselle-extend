package br.gov.component.demoiselle.jsf.restriction.context;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.primefaces.model.SortOrder;

public interface CriteriaContext extends Serializable {
	//	@SuppressWarnings("rawtypes")
	//	void setCriteriaControllerClass(Class<? extends AbstractCriteriaBean> criteriaBeanClass);
	//
	//	@SuppressWarnings("rawtypes")
	//	Class<? extends AbstractCriteriaBean> getCriteriaControllerClass();
	//
	//	@SuppressWarnings("rawtypes")
	//	boolean setProjectionClass(Class<? extends ProjectionBean> projectionClass);
	//
	//	@SuppressWarnings("rawtypes")
	//	Class<? extends ProjectionBean> getProjectionClass();
	//
	//	<T> List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p);
	//
	//	<T> void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);
	//
	//	<T> List<Order> getOrderList(CriteriaBuilder cb, Root<T> p);

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

	void setCollection(Collection<?> collection);

	Collection<?> getCollection();

	public void clear();

	//	void clear();

}
