package br.gov.frameworkdemoiselle.restriction.processor;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Processor;

public class RestrictionProcessor implements Processor {
	public <T, X> void apply(CriteriaBuilder cb, Root<X> p, List<Predicate> predicates) {

	}
}
