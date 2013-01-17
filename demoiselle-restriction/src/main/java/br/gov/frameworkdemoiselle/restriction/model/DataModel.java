package br.gov.frameworkdemoiselle.restriction.model;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;

public abstract class DataModel<T, C extends CriteriaBean<T>> extends LazyDataModel<T> {
	private static final long serialVersionUID = 1L;

	private Map<String, String> filters;

	private SortOrder sortOrder;

	private String sortField;

	private C criteriaBean;

	@SuppressWarnings({ "unused" })
	@PostConstruct
	private void init() {
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public C getCriteriaBean() {
		return criteriaBean;
	}

	public void setCriteriaBean(C criteriaBean) {
		this.criteriaBean = criteriaBean;
	}

	//
	// public Set<Predicate> process(CriteriaBuilder cb, Root<T> p) {
	// Set<Predicate> predicates = new HashSet<Predicate>();
	// for (Field field : criteriaBean.getClass().getDeclaredFields()) {
	// for (Processor<T> processor : processors) {
	// Predicate predicate = processor.apply(field, criteriaBean, cb, p,
	// filters);
	// if (predicate != null) {
	// predicates.add(predicate);
	// }
	// }
	// }
	// return predicates;
	// }

}
