package br.gov.frameworkdemoiselle.restriction.internal.transformers;

import java.lang.reflect.Field;

import br.gov.frameworkdemoiselle.restriction.template.CriteriaBean;
import br.gov.frameworkdemoiselle.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;

import com.google.common.base.Function;

public class FieldToRestrictionBeanTransformer<T, X> implements Function<Field, RestrictionBean<T, X>> {

	private CriteriaBean<T> bean;

	public FieldToRestrictionBeanTransformer(CriteriaBean<T> bean) {
		super();
		this.bean = bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RestrictionBean<T, X> apply(Field field) {
		return (RestrictionBean<T, X>) Reflections.getFieldValue(field, bean);
	}

}
