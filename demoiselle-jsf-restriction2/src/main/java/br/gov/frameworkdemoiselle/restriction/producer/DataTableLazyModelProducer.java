package br.gov.frameworkdemoiselle.restriction.producer;

import javax.enterprise.inject.New;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.custom.models.DataTableLazyModel;

public class DataTableLazyModelProducer {

	public <T> DataTableLazyModel<T> create(InjectionPoint ip, @New EntityManager em) {
		return new DataTableLazyModel<T>(em);
	}

}
