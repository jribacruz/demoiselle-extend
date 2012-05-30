package br.gov.component.demoiselle.xcriteria;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ICriterion<X> {
	public Predicate restriction(CriteriaBuilder cb, Root<X> p);

	public Order order(CriteriaBuilder cb, Root<X> p);

	public CompoundSelection<X> projection(CriteriaBuilder cb, Root<X> p);

	public Predicate mapper(CriteriaBuilder cb, Root<X> p , String q);
}
