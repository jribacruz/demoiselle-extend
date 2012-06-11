package br.gov.component.demoiselle.xcriteria;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ICriterion<X> {
	/**
	 * Define a restricao que sera aplicada a lista retornada pelo
	 * CriteriaProcessor
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param p
	 *            Root
	 * @return Predicado
	 */
	public Predicate restriction(CriteriaBuilder cb, Root<X> p);

	/**
	 * Define a ordem que sera aplicada a lista retornado pelo CriteriaProcessor
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param p
	 *            Root
	 * @return Order
	 */
	public Order order(CriteriaBuilder cb, Root<X> p);

	/**
	 * Retorna os itens do construtor do objeto do tipo X
	 * 
	 * @param cb CriteriaBuilder
	 * @param p Root
	 * @return CompoundSelection
	 */
	public CompoundSelection<X> projection(CriteriaBuilder cb, Root<X> p);

	/**
	 * Retorna a restricao que ser aplicada a lista retornada pelo CriteriaProcessor.
	 * Essa restricao e chamada para a filtragem dinamica baseada em texto.
	 * 
	 * @param cb
	 * @param p
	 * @param q String do criterion annotado com @Mapper
	 * @return
	 */
	public Predicate restriction(CriteriaBuilder cb, Root<X> p, String q);
}
