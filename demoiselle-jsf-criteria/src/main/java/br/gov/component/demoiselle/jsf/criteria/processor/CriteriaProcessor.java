package br.gov.component.demoiselle.jsf.criteria.processor;

import java.io.Serializable;
import java.util.List;

public interface CriteriaProcessor extends Serializable {
	public <T> List<T> getResultList(Class<T> beanClass);

	public <T, I> T load(Class<T> beanClass, I id);
}
