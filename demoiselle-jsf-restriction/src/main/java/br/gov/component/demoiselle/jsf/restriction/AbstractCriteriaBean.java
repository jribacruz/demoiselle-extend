package br.gov.component.demoiselle.jsf.restriction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.annotation.Restriction;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public abstract class AbstractCriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext criteriaContext;

	@Inject
	private CriteriaProcessorContext processorContext;

	protected Selection<?> projection(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected List<Order> orderBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected List<Expression<?>> groupBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	protected List<Predicate> having(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> p) {
		List<Predicate> predicateList = new ArrayList<Predicate>();

		for (Field field : Utils.getRestrictionFields(this.getClass())) {
			RestrictionBean restrictionBean = (RestrictionBean) Reflections.getFieldValue(field, this);
			String restrictionField = field.getAnnotation(Restriction.class).field();
			if (!StringUtils.isEmpty(restrictionField)) {
				restrictionBean.setField(restrictionField);
			}
			if (restrictionBean != null) {
				if (restrictionBean.getValue() != null) {
					Predicate predicate = Utils.processRestriction(restrictionBean, cb, p);
					if (predicate != null) {
						predicateList.add(predicate);
					}
				} else {
					Predicate predicate = restrictionBean.restriction(cb, p);
					if (predicate != null && !field.getAnnotation(Restriction.class).optional()) {
						predicateList.add(predicate);
					}
				}
			}
		}
		return predicateList;
	}

	@SuppressWarnings("unused")
	private List<Order> getOrders(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = getDataTableOrders(cb, p);

		if (this.orderBy(cb, p) != null) {
			orders.addAll(this.orderBy(cb, p));
		}
		return orders;
	}

	/**
	 * Insere a ordenação padrão do datatable
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	private List<Order> getDataTableOrders(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		if (getSortField() != null && !Strings.isEmpty(getSortField())) {
			if (getSortOrder() == SortOrder.ASCENDING) {
				orders.add(cb.asc(p.get(getSortField())));
			} else {
				orders.add(cb.desc(p.get(getSortField())));
			}
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

	protected List<Expression<?>> by(Expression<?>... expressions) {
		return Arrays.asList(expressions);
	}

	protected List<Predicate> by(Predicate... predicates) {
		return Arrays.asList(predicates);
	}

	protected boolean hasQuery() {
		return !Strings.isEmpty(criteriaContext.getQuery());
	}

}
