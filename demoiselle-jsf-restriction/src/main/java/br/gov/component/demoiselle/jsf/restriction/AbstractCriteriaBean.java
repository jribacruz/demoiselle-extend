package br.gov.component.demoiselle.jsf.restriction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.primefaces.model.SortOrder;
import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.annotation.Restriction;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public abstract class AbstractCriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext criteriaContext;

	@Inject
	private CriteriaProcessorContext processorContext;

	@Inject
	private Logger logger;

	protected Selection<?> projection(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected List<Order> orderBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected Expression<?> groupBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected Predicate having(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p) {
		List<Predicate> predicateList = new ArrayList<Predicate>();

		for (Field field : Reflections.getNonStaticDeclaredFields(this.getClass())) {
			if (field.isAnnotationPresent(Restriction.class)) {
				RestrictionBean restrictionBean = (RestrictionBean) Reflections.getFieldValue(field, this);
				if (restrictionBean.getValue() != null) {
					if (restrictionBean.getValue().getClass() == String.class) {
						if (!Strings.isEmpty((String) restrictionBean.getValue())) {
							if (restrictionBean.restriction(cb, p) != null) {
								predicateList.add(restrictionBean.restriction(cb, p));
							}
						}
					} else if (restrictionBean.getValue() instanceof Collection) {
						Collection collection = (Collection) restrictionBean.getValue();
						if (!collection.isEmpty()) {
							if (restrictionBean.restriction(cb, p) != null) {
								predicateList.add(restrictionBean.restriction(cb, p));
							}
						}
					} else {
						if (restrictionBean.restriction(cb, p) != null) {
							predicateList.add(restrictionBean.restriction(cb, p));
						}
					}
				}
			}
		}
		return predicateList;
	}

	@SuppressWarnings("unused")
	private List<Order> getOrder(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		if (getSortField() != null && !Strings.isEmpty(getSortField())) {
			if (getSortOrder() == SortOrder.ASCENDING) {
				orders.add(cb.asc(p.get(getSortField())));
			} else {
				orders.add(cb.desc(p.get(getSortField())));
			}

		}

		if (this.orderBy(cb, p) != null) {
			orders.addAll(this.orderBy(cb, p));
		}
		return orders;
	}

	public void filter() {
		processorContext.setCriteriaControllerClass(this.getClass());
	}

	private SortOrder getSortOrder() {
		return criteriaContext.getSortOrder();
	}

	private String getSortField() {
		return criteriaContext.getSortField();
	}

	protected List<Order> by(Order... orders) {
		return Arrays.asList(orders);
	}

	protected boolean hasQuery() {
		return !Strings.isEmpty(criteriaContext.getQuery());
	}

}
