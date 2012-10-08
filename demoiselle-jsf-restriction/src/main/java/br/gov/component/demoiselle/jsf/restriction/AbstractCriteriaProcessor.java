package br.gov.component.demoiselle.jsf.restriction;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;

public abstract class AbstractCriteriaProcessor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Instance<PaginationContext> paginationContext;

	private Pagination pagination;

	@Inject
	private EntityManager entityManager;

	protected <T> Pagination getPagination(Class<T> beanClass) {
		PaginationContext context = paginationContext.get();
		pagination = context.getPagination(beanClass);
		return pagination;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public abstract <T> List<T> getResultList(Class<T> beanClass);

	public abstract <T, I> T load(Class<T> beanClass, I id);

	protected abstract <T> Long countAll(Class<T> beanClass);

}
