package br.gov.component.demoiselle.xjpa.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

import br.gov.component.demoiselle.xjpa.CriteriaOrder;
import br.gov.component.demoiselle.xjpa.Criterion;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class CommonDataTableCriteria<X> implements Criterion<X> {

	private CriteriaOrder criteriaOrder;
	private String sortField;
	private Map<String, String> filters = new HashMap<String, String>();
	private Class<X> beanClass;

	public CommonDataTableCriteria() {
		super();
	}

	public CommonDataTableCriteria(Class<X> beanClass, CriteriaOrder criteriaOrder, String sortField,
			Map<String, String> filters) {
		super();
		this.beanClass = beanClass;
		this.criteriaOrder = criteriaOrder;
		this.sortField = sortField;
		this.filters = filters;
	}

	public Predicate restriction(CriteriaBuilder cb, Root<X> p) {
		X bean = Reflections.instantiate(beanClass);
		BeanMap beanMap = new BeanMap(bean);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		for (Map.Entry<String, String> e : filters.entrySet()) {
			String fieldName = e.getKey();
			String fieldValue = e.getValue();
			Class<?> fieldType = beanMap.getType(fieldName);

			if (fieldType == String.class && !Strings.isEmpty(fieldValue)) {
				predicateList.add(cb.like(cb.lower(p.<String> get(fieldName)), "%" + fieldValue.toLowerCase() + "%"));
			}
		}
		
		return predicateList.isEmpty() ? null : cb.and(predicateList.toArray(new Predicate[] {}));
	}

	public Order order(CriteriaBuilder cb, Root<X> p) {
		if (!Strings.isEmpty(sortField)) {
			if (criteriaOrder == CriteriaOrder.ASC) {
				return cb.asc(p.get(sortField));
			}
			return cb.desc(p.get(sortField));
		}
		return null;
	}

	public CompoundSelection<X> projection(CriteriaBuilder cb, Root<X> p) {
		return null;
	}

}
