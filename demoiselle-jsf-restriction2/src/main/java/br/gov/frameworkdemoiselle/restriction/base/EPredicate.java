package br.gov.frameworkdemoiselle.restriction.base;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

public class EPredicate extends HashSet<Predicate> {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(Predicate e) {
		if (e == null) {
			return false;
		}
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends Predicate> c) {
		if (c == null) {
			return false;
		}
		return super.addAll(c);
	}

	public Predicate[] asPredicate() {
		return this.toArray(new Predicate[] {});
	}

	public <T> void apply(CriteriaQuery<T> cq) {
		if (!this.isEmpty()) {
			cq.where(this.asPredicate());
		}
	}

}
