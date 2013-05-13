package br.gov.frameworkdemoiselle.restriction.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class EOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private Multimap<SortOrder, String> orders;

	public EOrder() {
		super();
		this.orders = ArrayListMultimap.create();
	}

	public void add(SortOrder sortOrder, String... attributes) {
		if (sortOrder != SortOrder.UNSORTED) {
			for (String attribute : attributes) {
				if (!Strings.isEmpty(attribute)) {
					this.orders.put(sortOrder, attribute);
				}
			}
		}
	}

	public <T> void apply(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		List<Order> list = new ArrayList<Order>();

		for (String att : this.orders.get(SortOrder.ASCENDING)) {
			list.add(cb.asc(p.get(att)));
		}

		for (String att : this.orders.get(SortOrder.DESCENDING)) {
			list.add(cb.desc(p.get(att)));
		}
		cq.orderBy(list);
		//this.orders.clear();
	}

}
