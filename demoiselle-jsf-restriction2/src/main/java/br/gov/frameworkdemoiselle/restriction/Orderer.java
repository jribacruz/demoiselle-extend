package br.gov.frameworkdemoiselle.restriction;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface Orderer {
	public <T> List<Order> apply(CriteriaBuilder cb, Root<T> p);

}
