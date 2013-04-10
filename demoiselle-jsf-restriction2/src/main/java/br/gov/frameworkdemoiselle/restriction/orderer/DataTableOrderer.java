package br.gov.frameworkdemoiselle.restriction.orderer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import com.google.common.collect.Lists;

import br.gov.frameworkdemoiselle.restriction.Orderer;
import br.gov.frameworkdemoiselle.util.Strings;

public class DataTableOrderer implements Orderer {

	private String sortField;

	private SortOrder sortOrder;

	public DataTableOrderer(String sortField, SortOrder sortOrder) {
		super();
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@Override
	public <T> List<Order> apply(CriteriaBuilder cb, Root<T> p) {
		if (!Strings.isEmpty(sortField)) {
			if (sortOrder == SortOrder.ASCENDING) {
				return Lists.newArrayList(cb.asc(p.get(sortField)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				return Lists.newArrayList(cb.desc(p.get(sortField)));
			}
		}
		return new ArrayList<Order>();
	}

}
