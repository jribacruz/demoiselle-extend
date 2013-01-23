package br.gov.frameworkdemoiselle.restriction2;

import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;

public interface Linker<T> {
	public Set<Predicate> link(CriteriaBuilder cb, Root<T> p, RestrictionMap map);
}
