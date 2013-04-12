package br.gov.frameworkdemoiselle.restriction;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Processor {
	public <X> void apply(CriteriaBuilder cb, Root<X> p, List<Predicate> predicates);
}
