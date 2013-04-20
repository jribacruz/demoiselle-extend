package br.gov.frameworkdemoiselle.restriction.producer;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.frameworkdemoiselle.restriction.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.annotations.CriteriaBy;
import br.gov.frameworkdemoiselle.restriction.annotations.InitModel;
import br.gov.frameworkdemoiselle.restriction.context.ModelContext;
import br.gov.frameworkdemoiselle.restriction.custom.models.DataTableLazyModel;

public class DataTableLazyModelProducer {

	@Produces
	public <T> DataTableLazyModel<T> create(InjectionPoint ip) {
		ModelContext<T> context = new ModelContext<T>();
		this.initModel(context, ip.getMember());
		this.initCriteria(context, ip.getMember());
		DataTableLazyModel<T> dataTableLazyModel = new DataTableLazyModel<T>(context);
		return dataTableLazyModel;
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

	@SuppressWarnings("unchecked")
	private <T> void initCriteria(ModelContext<T> context, Member member) {
		Field field = (Field) member;
		if (field.isAnnotationPresent(CriteriaBy.class)) {
			context.setCriteriaClass((Class<? extends CriteriaBean<T>>) field.getAnnotation(CriteriaBy.class).value());
		}
	}

}
