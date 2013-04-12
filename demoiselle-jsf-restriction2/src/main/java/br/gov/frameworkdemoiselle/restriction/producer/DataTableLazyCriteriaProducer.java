package br.gov.frameworkdemoiselle.restriction.producer;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.core.Criteria;
import br.gov.frameworkdemoiselle.restriction.custom.models.DataTableLazyModel;

public class DataTableLazyCriteriaProducer {

	@Produces
	@Default
	public <T> DataTableLazyModel<T> create(InjectionPoint ip, @New JPABuilder<T> builder, @New Criteria<T> defaultCriteria) {
		return new DataTableLazyModel<T>(defaultCriteria);
	}

}
