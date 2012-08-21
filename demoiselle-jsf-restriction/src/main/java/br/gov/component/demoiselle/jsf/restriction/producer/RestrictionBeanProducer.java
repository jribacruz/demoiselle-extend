package br.gov.component.demoiselle.jsf.restriction.producer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

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
	public <T, X> RestrictionBean<T, X> create(InjectionPoint ip) {
		Field field = (Field) ip.getMember();
		if (field.isAnnotationPresent(Restriction.class)) {
			RestrictionBean<T, X> bean = Beans.getReference(field.getAnnotation(Restriction.class).value());
			return bean;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Produces
	@Restriction(RestrictionBean.class)
	public <T, X> RestrictionBean<T, Collection<X>> create2(InjectionPoint ip) {
		Field field = (Field) ip.getMember();
		if (field.isAnnotationPresent(Restriction.class)) {
			RestrictionBean<T, Collection<X>> bean = Beans.getReference(field.getAnnotation(Restriction.class).value());
			bean.setValue(new ArrayList<X>());
			return bean;
		}
		return null;
	}

}
