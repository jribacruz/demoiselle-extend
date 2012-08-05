package br.gov.component.demoiselle.jsf.restriction.producer;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.component.demoiselle.jsf.restriction.annotation.Restriction;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Beans;

public class RestrictionBeanProducer implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Produces
	@Restriction(RestrictionBean.class)
	public <T,X> RestrictionBean<T, X> create(InjectionPoint ip) {
		Field field= (Field) ip.getMember();
		if(field.isAnnotationPresent(Restriction.class)) {
			RestrictionBean<T,X> bean = Beans.getReference(field.getAnnotation(Restriction.class).value());
			return bean;
		}
		return null;
	}

}
