package br.gov.component.demoiselle.xcriteria.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

import br.gov.component.demoiselle.xcriteria.annotation.Mapper;
import br.gov.component.demoiselle.xcriteria.base.XPredicate;
import br.gov.component.demoiselle.xcriteria.collect.XCollection;
import br.gov.component.demoiselle.xcriteria.filters.InFilter;
import br.gov.component.demoiselle.xcriteria.filters.LikeFilter;
import br.gov.frameworkdemoiselle.util.Strings;

public class CriterionBeanProcessor {
	private BeanMap criterionBeanMap;
	private Object criterionBean;
	private List<XPredicate<Predicate>> predicatesRestriction;
	private List<XPredicate<Order>> predicatesOrder;

	public CriterionBeanProcessor(Object criterionBean) {
		this.criterionBeanMap = new BeanMap(criterionBean);
		this.criterionBean = criterionBean;
		registerPredicateRestriction();
		registerPredicateOrder();
	}

	public <X> List<Predicate> getRestriction(CriteriaBuilder cb, Root<X> p) {
		return XCollection.transform(criterionBeanMap, cb, p, predicatesRestriction);
	}

	public <X> List<Order> getOrder(CriteriaBuilder cb, Root<X> p) {
		return XCollection.transform(criterionBeanMap, cb, p, predicatesOrder);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getMapper() {
		Field[] fields = criterionBean.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(Mapper.class) != null) {
				if (criterionBeanMap.getType(field.getName()) == String.class) {
					String search = (String) criterionBeanMap.get(field.getName());
					if (!Strings.isEmpty(search)) {
						return search;
					}
				}
			}
		}
		return null;
	}

	private void registerPredicateRestriction() {
		this.predicatesRestriction = new ArrayList<XPredicate<Predicate>>();
		this.predicatesRestriction.add(new InFilter());
		this.predicatesRestriction.add(new LikeFilter());
	}

	private void registerPredicateOrder() {
		this.predicatesOrder = new ArrayList<XPredicate<Order>>();
	}
}
