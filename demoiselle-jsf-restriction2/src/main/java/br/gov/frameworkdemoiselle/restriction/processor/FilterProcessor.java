package br.gov.frameworkdemoiselle.restriction.processor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Processor;
import br.gov.frameworkdemoiselle.util.Strings;

public class FilterProcessor<T> implements Processor<T> {

	private Map<String, String> filters;

	public FilterProcessor(Map<String, String> filters) {
		super();
		this.filters = filters;
	}

	@Override
	public void apply(CriteriaBuilder cb, Root<T> p, List<Predicate> predicates) {
		if (!filters.isEmpty()) {

			Iterator<String> iterator = filters.keySet().iterator();

			while (iterator.hasNext()) {
				String column = iterator.next();
				String value = filters.get(column);
				if (!Strings.isEmpty(value)) {
					predicates.add(cb.like(cb.lower(p.<String> get(column)), "%" + value.toLowerCase() + "%"));
				}
			}
		}

	}

}
