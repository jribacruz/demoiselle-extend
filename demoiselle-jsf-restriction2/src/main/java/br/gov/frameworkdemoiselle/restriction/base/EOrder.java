package br.gov.frameworkdemoiselle.restriction.base;

import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.primefaces.model.SortOrder;

import br.gov.frameworkdemoiselle.util.Strings;

public class EOrder extends HashMap<SortOrder, String> {
	private static final long serialVersionUID = 1L;

	@Override
	public String put(SortOrder arg0, String arg1) {
		if (arg0 != SortOrder.UNSORTED && !Strings.isEmpty(arg1)) {
			return super.put(arg0, arg1);
		}
		return new String();
	}

	public <T> void apply(CriteriaBuilder cb, CriteriaQuery<T> cq) {

	}

}
