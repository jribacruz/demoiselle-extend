package br.gov.frameworkdemoiselle.restriction.producer;

import java.lang.reflect.Field;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.restriction.annotations.CriteriaBy;
import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.core.Criteria;
import br.gov.frameworkdemoiselle.restriction.custom.models.DataTableLazyModel;

public class DataTableLazyCriteriaProducer {

	@Produces
	@Default
	public <T> DataTableLazyModel<T> create(InjectionPoint ip, @New JPABuilder<T> builder, @New Criteria<T> defaultCriteria) {
		Criteria<T> criteria = defaultCriteria;
		Field field = (Field) ip.getMember();
		if (field.isAnnotationPresent(CriteriaBy.class)) {
			
		}
		return new DataTableLazyModel<T>(criteria);
	}

}
