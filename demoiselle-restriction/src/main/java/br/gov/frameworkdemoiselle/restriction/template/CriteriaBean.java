package br.gov.frameworkdemoiselle.restriction.template;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.context.CriteriaContext;

public class CriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	/**
	 * Define se a consulta deve ou nao ser paginada
	 */
	private boolean paginated;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		context.registerCriteria(this.getClass(), this);
	}

	/**
	 * Define os projeção da query
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	public CompoundSelection<T> projection(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	/**
	 * Define a ordenação da query
	 * 
	 * @param cb
	 * @param p
	 * @return
	 */
	public List<Order> orderBy(CriteriaBuilder cb, Root<T> p) {
		return null;
	}

	public boolean isPaginated() {
		return paginated;
	}

	public void setPaginated(boolean paginated) {
		this.paginated = paginated;
	}

}
