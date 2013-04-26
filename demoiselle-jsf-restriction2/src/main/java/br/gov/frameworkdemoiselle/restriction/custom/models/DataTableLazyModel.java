package br.gov.frameworkdemoiselle.restriction.custom.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.util.Strings;

public class DataTableLazyModel<T> extends DefaultLazyModel<T> {
	private static final long serialVersionUID = 1L;

	private ModelContext<T> context;

	private String query;

	public DataTableLazyModel(ModelContext<T> context, EntityManager em) {
		super();
		this.context = context;
		this.em = em;
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

		Set<Predicate> predicatesQuery = this.processQueryAttributes(cb, p);
		if (!predicatesQuery.isEmpty()) {
			predicates.add(cb.or(predicatesQuery.toArray(new Predicate[] {})));
		}

		return predicates.toArray(new Predicate[] {});
	}

	@Override
	protected List<Order> getOrders(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();

		if (!context.getAsc(cb, p).isEmpty()) {
			orders.addAll(context.getAsc(cb, p));
		}
		if (!context.getDesc(cb, p).isEmpty()) {
			orders.addAll(context.getDesc(cb, p));
		}

		return orders;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	protected Class<T> getBeanClass() {
		return context.getBeanClass();
	}

	private Set<Predicate> processQueryAttributes(CriteriaBuilder cb, Root<T> p) {
		Set<Predicate> predicates = new HashSet<Predicate>();
		if (context.getQueryAttributes().length > 0 && !Strings.isEmpty(this.query)) {
			for (int i = 0; i < context.getQueryAttributes().length; i++) {
				predicates.add(cb.like(cb.lower(p.<String> get(context.getQueryAttributes()[i])), "%" + StringUtils.lowerCase(this.query)
						+ "%"));
			}
		}
		return predicates;
	}

}
