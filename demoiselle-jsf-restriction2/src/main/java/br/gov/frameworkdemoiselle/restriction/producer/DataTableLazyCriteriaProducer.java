package br.gov.frameworkdemoiselle.restriction.producer;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.restriction.builder.JPABuilder;
import br.gov.frameworkdemoiselle.restriction.models.DataTableLazyModel;
import br.gov.frameworkdemoiselle.util.Reflections;

public class DataTableLazyCriteriaProducer {

	@Produces
	@Default
	public <T> DataTableLazyModel<T> create(InjectionPoint ip, @New JPABuilder<T> builder) {
		Class<T> beanClass = Reflections.getGenericTypeArgument(ip.getMember(), 0);
		return new DataTableLazyModel<T>(beanClass, builder);
	}

}
