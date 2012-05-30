package br.gov.component.demoiselle.xcriteria.context;

import java.util.List;

public interface CriteriaProcessor {

	/**
	 * Busca onde é retornado a listagem com filtragem pelo criterionClass. O
	 * criterion passado pelo CriteriaContext não é utilizado
	 * 
	 * @param beanClass
	 * @param criteionClass
	 * @return
	 */
	public <T> List<T> getResultList(Class<T> beanClass);

}
