package br.gov.frameworkdemoiselle.restriction.processor;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.frameworkdemoiselle.restriction.Processor;
import br.gov.frameworkdemoiselle.restriction.core.DefaultLazyModel;
import br.gov.frameworkdemoiselle.restriction.type.RestrictionBean;

import com.google.common.collect.ArrayListMultimap;

public class DataTableProcessor<T> implements Processor<T> {

	private DefaultLazyModel<T> defaultLazyCriteria;

	@SuppressWarnings("rawtypes")
	private ArrayListMultimap<Field, RestrictionBean> beansMap;

	public DataTableProcessor(DefaultLazyModel<T> defaultLazyCriteria) {
		super();
		this.beansMap = ArrayListMultimap.create();
		this.defaultLazyCriteria = defaultLazyCriteria;

	}

	@Override
	public void apply(CriteriaBuilder cb, Root<T> p, List<Predicate> predicates) {
		
	}

}
