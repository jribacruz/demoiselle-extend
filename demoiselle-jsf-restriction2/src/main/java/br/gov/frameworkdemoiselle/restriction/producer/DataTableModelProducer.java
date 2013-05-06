package br.gov.frameworkdemoiselle.restriction.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.restriction.annotations.InitModel;
import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.models.DataTableLazyModel;
import br.gov.frameworkdemoiselle.restriction.models.MultipleSelectionDataTableLazyModel;

public class DataTableModelProducer {

	@Produces
	public <T> DataTableLazyModel<T> create(InjectionPoint ip, EntityManager em) {
		ModelContext<T> context = new ModelContext<T>();
		this.initModel(context, ip.getMember());
		initBeanClass(context, ip.getMember());
		DataTableLazyModel<T> dataTableLazyModel = new DataTableLazyModel<T>(context, em);
		return dataTableLazyModel;
	}

	@Produces
	public <T> MultipleSelectionDataTableLazyModel<T> create2(InjectionPoint ip, EntityManager em) {
		ModelContext<T> context = new ModelContext<T>();
		this.initBeanClass(context, ip.getMember());
		initBeanClass(context, ip.getMember());
		MultipleSelectionDataTableLazyModel<T> dataTableLazyModel = new MultipleSelectionDataTableLazyModel<T>(context, em);
		return dataTableLazyModel;
	}

	private <T> void initBeanClass(ModelContext<T> context, Member member) {
		Field field = (Field) member;
		context.setFieldBeanClass(field);
	}

	private <T> void initModel(ModelContext<T> context, Member member) {
		Field field = (Field) member;
		if (field.isAnnotationPresent(InitModel.class)) {
			context.setAsc(field.getAnnotation(InitModel.class).asc());
			context.setDesc(field.getAnnotation(InitModel.class).desc());
			context.setMaxResults(field.getAnnotation(InitModel.class).maxResults());
			context.setQueryAttributes(field.getAnnotation(InitModel.class).queryAttributes());
		}
	}

}
