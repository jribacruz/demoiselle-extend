package br.gov.component.demoiselle.jsf.restriction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.restriction.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.restriction.qualifier.Restriction;
import br.gov.component.demoiselle.jsf.restriction.template.RestrictionBean;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.Strings;

public abstract class AbstractCriteriaBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;

	@Inject
	private Logger logger;

	protected abstract Predicate restriction(CriteriaBuilder cb, Root<T> p);

	protected abstract void projection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Predicate> getPredicateList(CriteriaBuilder cb, Root<T> p) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (restriction(cb, p) != null) {
			predicateList.add(restriction(cb, p));
		}
		for (Field field : Reflections.getNonStaticDeclaredFields(this.getClass())) {
			if (field.isAnnotationPresent(Restriction.class)) {
				RestrictionBean restrictionBean = (RestrictionBean) Reflections.getFieldValue(field, this);
				if (restrictionBean.getValue() != null) {
					if (restrictionBean.getValue().getClass() == String.class) {
						if (!Strings.isEmpty((String) restrictionBean.getValue())) {
							if (restrictionBean.restriction(cb, p) != null) {
								predicateList.add(restrictionBean.restriction(cb, p));
							}
						}
					} else {
						if (restrictionBean.restriction(cb, p) != null) {
							predicateList.add(restrictionBean.restriction(cb, p));
						}
					}
				}
			}
		}
		return predicateList;
	}

	public void getProjection(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> p) {
		projection(cb, cq, p);
	}

	public void filter() {
		context.setCriteriaControllerClass(this.getClass());
	}
}
