package br.gov.frameworkdemoiselle.restriction2.linkers;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction2.Linker;
import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;

public class RequiredRestrictionLinker<T> implements Linker<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Set<Predicate> link(CriteriaBuilder cb, Root<T> p, RestrictionMap map) {
		Set<Predicate> predicates = new HashSet<Predicate>();
		if (map.isRequired()) {
			RestrictionBean bean = map.getRestrictionBean();
			if (bean != null) {
				if (map.getAttributes().length > 1) {
					Set<Predicate> orPredicates = new HashSet<Predicate>();
					for (int i = 0; i < map.getAttributes().length; i++) {
						RestrictionBean orBean = Reflections.instantiate(bean.getClass());
						orBean.setField(map.getAttributes()[i]);
						orBean.setValue(bean.getValue());
						Predicate predicate = bean.restriction(cb, p);
						if (predicate != null) {
							orPredicates.add(predicate);
						}
					}
					predicates.add(cb.or(orPredicates.toArray(new Predicate[] {})));
				}
			}
		}
		return predicates;
	}

}
