package br.gov.component.demoiselle.xcriteria.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanMap;

import br.gov.component.demoiselle.xcriteria.annotation.Like;
import br.gov.component.demoiselle.xcriteria.annotation.Mapper;
import br.gov.frameworkdemoiselle.util.Strings;

public class CriterionBeanProcessor {
	private BeanMap criterionBeanMap;
	private Object criterionBean;

	public CriterionBeanProcessor(Object criterionBean) {
		this.criterionBeanMap = new BeanMap(criterionBean);
		this.criterionBean = criterionBean;
	}

	public <X> List<Predicate> getRestricition(CriteriaBuilder cb, Root<X> p) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (Field field : criterionBean.getClass().getFields()) {
			Object value = criterionBeanMap.get(field.getName());
			if (value != null) {
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation.getClass() == Like.class) {

					}
				}
			}
		}
		return predicates;
	}

	public <T> List<Order> getOrder(CriteriaBuilder cb, Root<T> p) {
		List<Order> orders = new ArrayList<Order>();
		return orders;
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

}
