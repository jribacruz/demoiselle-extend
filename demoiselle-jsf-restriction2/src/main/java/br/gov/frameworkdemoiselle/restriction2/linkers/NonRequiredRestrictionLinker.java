package br.gov.frameworkdemoiselle.restriction2.linkers;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.Linker;
import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class NonRequiredRestrictionLinker<T> implements Linker<T> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<Predicate> link(CriteriaBuilder cb, Root<T> p, RestrictionMap map) {
		Set<Predicate> predicates = new HashSet<Predicate>();
		if (!map.isRequired() && !map.isSelectionMode()) {
			Set<Predicate> orPredicates = new HashSet<Predicate>();
			for (RestrictionBean bean : map.getRestrictionBeans()) {
				if (!RestrictionMap.isValueEmpty(bean)) {
					Predicate predicate = bean.restriction(cb, p);
					if (predicate != null) {
						orPredicates.add(predicate);
					}
				}
			}
			if (!orPredicates.isEmpty()) {
				if (orPredicates.size() > 1) {
					predicates.add(cb.or(orPredicates.toArray(new Predicate[] {})));
				} else {
					predicates.addAll(orPredicates);
				}
			}
		}
		return predicates;
	}

}
