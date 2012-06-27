package br.gov.component.demoiselle.jsf.template;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IRestriction<T> extends ICriteria<T>{
	public Predicate restriction(CriteriaBuilder cb, Root<T> p);
}
