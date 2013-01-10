package br.gov.frameworkdemoiselle.restriction.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.context.CriteriaContext;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.util.Reflections;

@PersistenceController
public class CriteriaPersistence<T, I> extends JPACrud<T, I> {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Override
	public List<T> findAll() {

		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
		Root<T> p = cq.from(getBeanClass());

		TypedQuery<T> query = this.getEntityManager().createQuery(cq);

		return query.getResultList();
	}

	/*
	 * Retorna o tipo do bean atraves da classe criteria registrada no contexto
	 */
	@Override
	protected Class<T> getBeanClass() {
		return Reflections.<T> getGenericTypeArgument(context.getCriteriaClass(), 0);
	}
}
