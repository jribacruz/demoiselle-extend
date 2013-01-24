package br.gov.frameworkdemoiselle.restriction2.assembler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.restriction2.Linker;
import br.gov.frameworkdemoiselle.restriction2.base.RestrictionMap;
import br.gov.frameworkdemoiselle.restriction2.linkers.RequiredRestrictionLinker;
import br.gov.frameworkdemoiselle.restriction2.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction2.template.RestrictionBean;

public class CriteriaAssembler<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Set<Linker<T>> restrictionLinkers;

	@Inject
	private Logger logger;

	public CriteriaAssembler() {
		this.restrictionLinkers = new HashSet<Linker<T>>();
		restrictionLinkers.add(new RequiredRestrictionLinker<T>());
	}

	@SuppressWarnings("rawtypes")
	public <X> void assemblyPredicates(Class<? extends CriteriaBean> criteriaBeanClass, CriteriaBuilder cb, CriteriaQuery<X> cq, Root<T> p,
			Map<String, String> filters) {
		Set<Predicate> predicates = new HashSet<Predicate>();
		logger.info("================> A");
		for (Field field : criteriaBeanClass.getDeclaredFields()) {
			logger.info("================> B: " + field.getName());
			if (RestrictionBean.class.isAssignableFrom(field.getType())) {
				RestrictionMap map = new RestrictionMap(criteriaBeanClass, field, filters);
				logger.info("==============> C: " + map);
				for (Linker<T> linker : restrictionLinkers) {
					Set<Predicate> predicates2 = linker.link(cb, p, map);
					logger.info("==============> D: " + predicates2);
					if (!predicates2.isEmpty()) {
						predicates.addAll(predicates2);
					}
				}
			}
		}

		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[] {}));
		}

	}
}
