package br.gov.component.demoiselle.jsf.restriction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.gov.component.demoiselle.jsf.restriction.annotation.OperatorType;
import br.gov.component.demoiselle.jsf.restriction.annotation.PredicateGroup;
import br.gov.component.demoiselle.jsf.restriction.annotation.Restriction;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.context.CriteriaProcessorContext;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.component.demoiselle.jsf.restriction.value.ByteValue;
import br.gov.component.demoiselle.jsf.restriction.value.CharValue;
import br.gov.component.demoiselle.jsf.restriction.value.DoubleValue;
import br.gov.component.demoiselle.jsf.restriction.value.FloatValue;
import br.gov.component.demoiselle.jsf.restriction.value.IntegerValue;
import br.gov.component.demoiselle.jsf.restriction.value.LongValue;
import br.gov.component.demoiselle.jsf.restriction.value.StringValue;
import br.gov.component.demoiselle.jsf.restrictions.util.Utils;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public abstract class AbstractCriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext criteriaContext;

	@Inject
	private CriteriaProcessorContext processorContext;

	protected CompoundSelection<T> projection(CriteriaBuilder cb, Root<T> p) {
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
		Multimap<String, Predicate> andMap = ArrayListMultimap.create();
		Multimap<String, Predicate> orMap = ArrayListMultimap.create();

		for (Field field : Utils.getRestrictionFields(this.getClass())) {
			RestrictionBean restrictionBean = (RestrictionBean) Reflections.getFieldValue(field, this);
			if (restrictionBean != null) {
				setRestrictionField(field, restrictionBean);
				setRestrictionValue(field, restrictionBean);
				setDatatableFilterColumnValue(field, restrictionBean);
				if ((restrictionBean.getValue() != null && !field.getAnnotation(Restriction.class).selectionMode())
						|| (restrictionBean.getValue() != null && hasSelectionMode(field, restrictionBean))) {
					Predicate predicate = Utils.processRestriction(restrictionBean, cb, p);
					if (predicate != null) {
						if (hasPredicateGroup(field)) {
							if (getPredicateGroupOperator(field).equals(OperatorType.OR)) {
								orMap.put(getPredicateGroupName(field), predicate);
							} else if (getPredicateGroupOperator(field).equals(OperatorType.AND)) {
								andMap.put(getPredicateGroupName(field), predicate);
							}
						} else {
							predicateList.add(predicate);
						}
					}
				} else {
					if (!field.getAnnotation(Restriction.class).optional()) {
						Predicate predicate = restrictionBean.restriction(cb, p);
						if (predicate != null) {
							if (hasPredicateGroup(field)) {
								if (getPredicateGroupOperator(field).equals(OperatorType.OR)) {
									orMap.put(getPredicateGroupName(field), predicate);
								} else if (getPredicateGroupOperator(field).equals(OperatorType.AND)) {
									andMap.put(getPredicateGroupName(field), predicate);
								}
							} else {
								predicateList.add(predicate);
							}
						}
					}
				}
			}
		}

		andPredicateGroupProcess(cb, predicateList, andMap);
		orPredicateGroupProcess(cb, predicateList, orMap);

		return predicateList;
	}

	private void andPredicateGroupProcess(CriteriaBuilder cb, List<Predicate> predicateList, Multimap<String, Predicate> andMap) {
		Iterator<String> iter = andMap.keys().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Collection<Predicate> andPredicates = andMap.get(key);
			predicateList.add(cb.and(andPredicates.toArray(new Predicate[] {})));
		}
	}

	private void orPredicateGroupProcess(CriteriaBuilder cb, List<Predicate> predicateList, Multimap<String, Predicate> orMap) {
		Iterator<String> iter = orMap.keys().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Collection<Predicate> orPredicates = orMap.get(key);
			predicateList.add(cb.or(orPredicates.toArray(new Predicate[] {})));
		}
	}

	@SuppressWarnings("rawtypes")
	private void setRestrictionField(Field field, RestrictionBean restrictionBean) {
		String restrictionField = field.getAnnotation(Restriction.class).field();
		if (!Strings.isEmpty(restrictionField)) {
			restrictionBean.setField(restrictionField);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean hasSelectionMode(Field field, RestrictionBean restrictionBean) {
		return field.getAnnotation(Restriction.class).selectionMode() && restrictionBean.isSelection();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setDatatableFilterColumnValue(Field field, RestrictionBean restrictionBean) {
		String restrictionField = field.getAnnotation(Restriction.class).field();
		if (!Strings.isEmpty(restrictionField) && field.getAnnotation(Restriction.class).datatableFilterColumn()) {
			if (criteriaContext.getFilters().containsKey(restrictionField)) {
				String value = criteriaContext.getFilters().get(restrictionField);
				if (!Strings.isEmpty(value)) {
					restrictionBean.setField(restrictionField);
					restrictionBean.setValue(value);
				}
			} else {
				restrictionBean.setValue(null);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setRestrictionValue(Field field, RestrictionBean restrictionBean) {
		if (field.isAnnotationPresent(StringValue.class)) {
			restrictionBean.setValue(field.getAnnotation(StringValue.class).value());
		} else if (field.isAnnotationPresent(ByteValue.class)) {
			restrictionBean.setValue(field.getAnnotation(ByteValue.class).value());
		} else if (field.isAnnotationPresent(CharValue.class)) {
			restrictionBean.setValue(field.getAnnotation(CharValue.class).value());
		} else if (field.isAnnotationPresent(DoubleValue.class)) {
			restrictionBean.setValue(field.getAnnotation(DoubleValue.class).value());
		} else if (field.isAnnotationPresent(FloatValue.class)) {
			restrictionBean.setValue(field.getAnnotation(FloatValue.class).value());
		} else if (field.isAnnotationPresent(IntegerValue.class)) {
			restrictionBean.setValue(field.getAnnotation(IntegerValue.class).value());
		} else if (field.isAnnotationPresent(LongValue.class)) {
			restrictionBean.setValue(field.getAnnotation(LongValue.class).value());
		}
	}

	private boolean hasPredicateGroup(Field field) {
		return field.isAnnotationPresent(PredicateGroup.class);
	}

	private OperatorType getPredicateGroupOperator(Field field) {
		return field.getAnnotation(PredicateGroup.class).operator();
	}

	private String getPredicateGroupName(Field field) {
		return field.getAnnotation(PredicateGroup.class).name();
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
