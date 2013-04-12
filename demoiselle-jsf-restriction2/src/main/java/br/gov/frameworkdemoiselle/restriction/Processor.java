package br.gov.frameworkdemoiselle.restriction;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Processor<T> {
	public void apply(CriteriaBuilder cb, Root<T> p, List<Predicate> predicates);
}
