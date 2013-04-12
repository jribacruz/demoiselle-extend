package br.gov.frameworkdemoiselle.restriction.processor;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Processor;
import br.gov.frameworkdemoiselle.restriction.annotations.QueryBy;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyCriteria;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class RestrictionProcessor<T> implements Processor {

	private DefaultLazyCriteria<T> defaultLazyCriteria;

	@SuppressWarnings("rawtypes")
	private Map<Field, RestrictionBean> beans;

	@SuppressWarnings("rawtypes")
	public RestrictionProcessor(DefaultLazyCriteria<T> defaultLazyCriteria) {
		super();
		this.beans = new HashMap<Field, RestrictionBean>();
		this.defaultLazyCriteria = defaultLazyCriteria;
		init();
	}

	@SuppressWarnings("rawtypes")
	public <X> void apply(CriteriaBuilder cb, Root<X> p, List<Predicate> predicates) {
		Iterator<Field> iterator = beans.keySet().iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			RestrictionBean bean = beans.get(field);
			this.setValue(bean, field);
		}
	}

	@SuppressWarnings("rawtypes")
	protected void init() {
		if (beans.isEmpty()) {
			for (Field field : Reflections.getNonStaticDeclaredFields(defaultLazyCriteria.getClass())) {
				if (RestrictionBean.class.isAssignableFrom(field.getType())) {
					RestrictionBean bean = (RestrictionBean) Reflections.getFieldValue(field, defaultLazyCriteria);
					if (bean != null) {
						beans.put(field, bean);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setValue(RestrictionBean bean, Field field) {
		String value = field.getAnnotation(QueryBy.class).value();

		if (!Strings.isEmpty(value)) {
			Class attributeClass = Reflections.getGenericTypeArgument(bean.getClass(), 1);
			if (attributeClass == String.class) {
				bean.setValue(value);
			} else if (Enum.class.isAssignableFrom(attributeClass)) {
				bean.setValue(Enum.valueOf(attributeClass, value));
			} else if (attributeClass == Long.class) {
				bean.setValue(new Long(value));
			} else if (attributeClass == Integer.class) {
				bean.setValue(new Integer(value));
			} else if (attributeClass == Boolean.class) {
				bean.setValue(new Boolean(value));
			}
		}

	}
}
