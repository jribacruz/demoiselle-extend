package br.gov.frameworkdemoiselle.restriction.processor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Processor;
import br.gov.frameworkdemoiselle.restriction.annotations.Attribute;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

import com.google.common.collect.ArrayListMultimap;

public class DataTableProcessor<T> implements Processor<T> {

	private DefaultLazyModel<T> defaultLazyCriteria;

	@SuppressWarnings("rawtypes")
	private ArrayListMultimap<Field, RestrictionBean> beansMap;

	public DataTableProcessor(DefaultLazyModel<T> defaultLazyCriteria) {
		super();
		this.beansMap = ArrayListMultimap.create();
		this.defaultLazyCriteria = defaultLazyCriteria;

		init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void apply(CriteriaBuilder cb, Root<T> p, List<Predicate> predicates) {

		Iterator<Field> iterator = beansMap.keySet().iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			List<RestrictionBean> values = beansMap.get(field);
			if (values.size() > 1) {
				List<Predicate> listPredicates = new ArrayList<Predicate>();
				Iterator<RestrictionBean> iterator2 = values.iterator();
				while (iterator2.hasNext()) {
					RestrictionBean restrictionBean = iterator2.next();
					Predicate predicate = restrictionBean.restriction(cb, p);
					if (predicate != null) {
						listPredicates.add(predicate);
					}
				}
				if (!listPredicates.isEmpty()) {
					predicates.add(cb.or(listPredicates.toArray(new Predicate[] {})));
				}

			} else if (values.size() == 1) {
				RestrictionBean restrictionBean = values.get(0);
				Predicate predicate = restrictionBean.restriction(cb, p);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
	}

	/**
	 * Inicializa o map de beans com todos os RestrictionsBeans do
	 * defaultLazyCriteria
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	protected void init() {
		if (beansMap.isEmpty()) {
			/*
			 * Percorre o bean defaultLazyCriteria e retorna todos os atributos
			 * não estaticos
			 */
			for (Field field : Reflections.getNonStaticDeclaredFields(defaultLazyCriteria.getClass())) {
				/*
				 * Testa se o campo é do tipo(ou extend) RestrictionBean
				 */
				if (RestrictionBean.class.isAssignableFrom(field.getType())) {
					RestrictionBean bean = (RestrictionBean) Reflections.getFieldValue(field, defaultLazyCriteria);
					if (bean != null) {
						if (field.isAnnotationPresent(Attribute.class)) {
							String[] fieldNames = field.getAnnotation(Attribute.class).names();
							/*
							 * Percorre a lista de atributos e adiciona no
							 * multimap
							 */
							for (int i = 0; i < fieldNames.length; i++) {
								beansMap.put(field, createRestrictionBeanCopy(bean, field, fieldNames[i]));
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected RestrictionBean createRestrictionBeanCopy(RestrictionBean bean, Field field, String fieldName) {
		RestrictionBean beanCopy = Reflections.instantiate(bean.getClass());
		beanCopy.setValue(bean.getValue());
		beanCopy.setSelection(bean.isSelection());
		beanCopy.setField(fieldName);
		setValue(beanCopy, field);
		return beanCopy;
	}

	/**
	 * Seta o valor no RestrictionBean e converter caso necessário
	 * 
	 * @param bean
	 * @param field
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setValue(RestrictionBean bean, Field field) {
		String value = field.getAnnotation(Attribute.class).value();

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
			} else if (attributeClass == BigDecimal.class) {
				bean.setValue(new BigDecimal(value));
			} else if (attributeClass == BigInteger.class) {
				bean.setValue(new BigInteger(value));
			} else if (attributeClass == Double.class) {
				bean.setValue(new Double(value));
			} else if (attributeClass == Float.class) {
				bean.setValue(new Float(value));
			} else if (attributeClass == Character.class) {
				if (value.length() > 0) {
					bean.setValue(new Character(value.toCharArray()[0]));
				}
			} else if (attributeClass == Short.class) {
				bean.setValue(new Short(value));
			}
		}
	}
}
