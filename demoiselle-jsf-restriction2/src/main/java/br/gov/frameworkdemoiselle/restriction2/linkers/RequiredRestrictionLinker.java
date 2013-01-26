package br.gov.frameworkdemoiselle.restriction2.linkers;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.Linker;
import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class RequiredRestrictionLinker<T> implements Linker<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Set<Predicate> link(CriteriaBuilder cb, Root<T> p, RestrictionMap map) {
		Set<Predicate> predicates = new HashSet<Predicate>();
		if (map.isRequired()) {
			Set<Predicate> orPredicates = new HashSet<Predicate>();
			for (RestrictionBean bean : map.getRestrictionBeans()) {
				Predicate predicate = bean.restriction(cb, p);
				if (predicate != null) {
					orPredicates.add(predicate);
				}
			}
			if (!orPredicates.isEmpty()) {
				if (map.getRestrictionBeans().size() > 1) {
					predicates.add(cb.or(orPredicates.toArray(new Predicate[] {})));
				} else {
					predicates.addAll(orPredicates);
				}
			}

		}
		return predicates;
	}
}
