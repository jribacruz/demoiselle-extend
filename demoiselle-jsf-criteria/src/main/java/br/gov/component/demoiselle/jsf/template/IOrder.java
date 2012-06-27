package br.gov.component.demoiselle.jsf.template;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface IOrder<T> extends ICriteria<T> {
	public Order order(CriteriaBuilder cb, Root<T> p);
}
