package br.gov.frameworkdemoiselle.restriction.custom.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	private ModelContext<T> context;

	public DataTableLazyModel(ModelContext<T> context) {
		super();
		this.context = context;
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		this.first = first;
		this.pageSize = pageSize;
		context.setSortField(sortField);
		context.setSortOrder(sortOrder);

		this.setRowCount(this.countAll());
		return this.findAll();
	}

	@Override
	protected Predicate[] getPredicates(CriteriaBuilder cb, Root<T> p) {
		Set<Predicate> predicates = new HashSet<Predicate>();

		return predicates.toArray(new Predicate[] {});
	}

	@Override
	protected List<Order> getOrders(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		return orders;
	}

	public void setQuery(String query) {
		context.setQuery(query);
	}

	public String getQuery() {
		return context.getQuery();
	}

}
