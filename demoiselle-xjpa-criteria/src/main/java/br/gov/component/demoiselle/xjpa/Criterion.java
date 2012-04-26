package br.gov.component.demoiselle.xjpa;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterion {
	public <T> Predicate restriction(CriteriaBuilder cb, Root<T> p);

	public <T> Order order(CriteriaBuilder cb, Root<T> p);

	public <T> CompoundSelection<T> projection(CriteriaBuilder cb, Root<T> p);

}
