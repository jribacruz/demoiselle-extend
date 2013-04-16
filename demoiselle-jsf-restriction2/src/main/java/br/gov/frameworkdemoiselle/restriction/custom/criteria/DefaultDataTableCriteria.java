package br.gov.frameworkdemoiselle.restriction.custom.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.core.AbstractCriteria;
import br.gov.frameworkdemoiselle.restriction.custom.restrictions.LikeRestriction;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.restriction.utils.Reflections;

public class DefaultDataTableCriteria<T> extends AbstractCriteria<T> {
	private static final long serialVersionUID = 1L;

	@Inject
	private LikeRestriction<T> query;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (RestrictionBean bean : Reflections.getRestrictionBeans(this)) {
			Set<Predicate> predicate = bean.restriction(cb, p);
			if (predicate != null && !predicate.isEmpty()) {
				predicates.addAll(predicate);
			}
		}
		return predicates;
	}

	@Override
	protected List<Order> getOrders(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		Iterator<SortOrder> iterator = this.orderMap.keySet().iterator();
		while (iterator.hasNext()) {
			SortOrder key = iterator.next();
			List<String> stringsOrders = orderMap.get(key);
			Iterator<String> iterator2 = stringsOrders.iterator();
			while (iterator2.hasNext()) {
				String fieldOrder = iterator2.next();
				if (key == SortOrder.ASCENDING) {
					orders.add(cb.asc(p.get(fieldOrder)));
				} else if (key == SortOrder.DESCENDING) {
					orders.add(cb.desc(p.get(fieldOrder)));
				}
			}
		}
		return orders;
	}

	public LikeRestriction<T> getQuery() {
		return query;
	}

	public void setQuery(LikeRestriction<T> query) {
		this.query = query;
	}
	
	

}
