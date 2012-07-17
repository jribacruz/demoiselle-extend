package br.gov.component.demoiselle.jsf.criteria.template;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IRestriction<T> {
	public Predicate restriction(CriteriaBuilder cb, Root<T> p);

	public void projection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);
}
