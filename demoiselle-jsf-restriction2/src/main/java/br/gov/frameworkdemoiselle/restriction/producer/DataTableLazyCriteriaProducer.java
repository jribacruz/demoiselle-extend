package br.gov.frameworkdemoiselle.restriction.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.restriction.annotations.QueryAttribute;
import br.gov.frameworkdemoiselle.restriction.annotations.SortBy;
import br.gov.frameworkdemoiselle.restriction.custom.criteria.DefaultDataTableCriteria;
import br.gov.frameworkdemoiselle.restriction.custom.models.DataTableLazyModel;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public class DataTableLazyCriteriaProducer {

	@Produces
	@Default
	public <T> DataTableLazyModel<T> create(InjectionPoint ip, @New DefaultDataTableCriteria<T> defaultCriteria) {
		defaultCriteria.setBeanClass(Reflections.<T> getGenericTypeArgument(ip.getMember(), 0));
		setOrders(defaultCriteria, ip.getMember());
		setQueryAttribute(defaultCriteria, ip.getMember());
		return new DataTableLazyModel<T>(defaultCriteria);
	}

	private <T> void setOrders(DefaultDataTableCriteria<T> defaultCriteria, Member member) {
		Field field = (Field) member;
		if (field.isAnnotationPresent(SortBy.class)) {
			String[] asc = field.getAnnotation(SortBy.class).asc();
			String[] desc = field.getAnnotation(SortBy.class).desc();
			for (int i = 0; i < asc.length; i++) {
				if (!Strings.isEmpty(asc[i])) {
					defaultCriteria.addOrder(SortOrder.ASCENDING, asc[i]);
				}
			}
			for (int j = 0; j < desc.length; j++) {
				if (!Strings.isEmpty(desc[j])) {
					defaultCriteria.addOrder(SortOrder.DESCENDING, desc[j]);
				}
			}
		}
	}

	private <T> void setQueryAttribute(DefaultDataTableCriteria<T> defaultCriteria, Member member) {
		Field field = (Field) member;
		if (field.isAnnotationPresent(QueryAttribute.class)) {
			String[] attributes = field.getAnnotation(QueryAttribute.class).value();
			for (int i = 0; i < attributes.length; i++) {
				defaultCriteria.getQuery().getFields().add(attributes[i]);
			}
		}
	}

}
