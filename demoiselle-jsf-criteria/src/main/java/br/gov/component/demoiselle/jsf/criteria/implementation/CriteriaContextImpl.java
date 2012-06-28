package br.gov.component.demoiselle.jsf.criteria.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.component.demoiselle.jsf.criteria.annotation.OrderBy;
import br.gov.component.demoiselle.jsf.criteria.annotation.Projection;
import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.criteria.template.ICriteria;
import br.gov.component.demoiselle.jsf.criteria.template.IOrder;
import br.gov.component.demoiselle.jsf.criteria.template.IProjection;
import br.gov.component.demoiselle.jsf.criteria.template.IRestriction;
import br.gov.component.demoiselle.jsf.criteria.template.IRestrictionQuery;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;

@SessionScoped
public class CriteriaContextImpl implements CriteriaContext {
	private static final long serialVersionUID = 1L;

	private Class<?> criteria;

	private int pageSize;

	private String query;

	public void setCriteria(Class<?> criteriaClass) {
		this.criteria = criteriaClass;
	}

	public void setCriteria(Class<?> criteriaClass, int pageSize) {
		this.criteria = criteriaClass;
		this.pageSize = pageSize;
	}

	@SuppressWarnings("unchecked")
	public <T> ICriteria<T> getCriteria() {
		return (ICriteria<T>) (criteria != null && ICriteria.class.isAssignableFrom(criteria) ? Beans.getReference(criteria)
				: null);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int size) {
		this.pageSize = size;
	}

	public <T> Predicate[] getRestriction(CriteriaBuilder cb, Root<T> p) {
		ICriteria<T> criteria = getCriteria();
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (criteria != null) {
			if (criteria instanceof IRestriction) {
				IRestriction<T> restriction = (IRestriction<T>) criteria;
				Predicate predicate = restriction.restriction(cb, p);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}

			if (criteria instanceof IRestrictionQuery && !Strings.isEmpty(query)) {
				IRestrictionQuery<T> restrictionQuery = (IRestrictionQuery<T>) criteria;
				Predicate predicate = restrictionQuery.restriction(cb, p, query);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}

		}
		return predicates.toArray(new Predicate[] {});
	}

	public <T> List<Order> getOrder(CriteriaBuilder cb, Root<T> p) {
		ICriteria<T> criteria = getCriteria();
		List<Order> orderList = new ArrayList<Order>();
		if (criteria != null) {
			if (criteria instanceof IOrder) {
				IOrder<T> order = (IOrder<T>) criteria;
				Order order2 = order.order(cb, p);
				if (order2 != null) {
					orderList.add(order2);
				}
			} else {
				if (criteria instanceof IRestriction) {
					try {
						Method method = criteria.getClass().getMethod("restriction", CriteriaBuilder.class, Root.class);
						if (method.isAnnotationPresent(OrderBy.class)) {
							OrderBy orderBy = method.getAnnotation(OrderBy.class);
							for (String orderItemAsc : orderBy.asc()) {
								if (!Strings.isEmpty(orderItemAsc)) {
									orderList.add(cb.asc(p.get(orderItemAsc)));
								}
							}
							for (String orderItemDesc : orderBy.desc()) {
								if (!Strings.isEmpty(orderItemDesc)) {
									orderList.add(cb.desc(p.get(orderItemDesc)));
								}
							}

						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}

				if (criteria instanceof IRestrictionQuery) {
					try {
						Method method = criteria.getClass().getMethod("restriction", CriteriaBuilder.class, Root.class,
								String.class);
						if (method.isAnnotationPresent(OrderBy.class)) {
							OrderBy orderBy = method.getAnnotation(OrderBy.class);
							for (String orderItemAsc : orderBy.asc()) {
								if (!Strings.isEmpty(orderItemAsc)) {
									orderList.add(cb.asc(p.get(orderItemAsc)));
								}
							}
							for (String orderItemDesc : orderBy.desc()) {
								if (!Strings.isEmpty(orderItemDesc)) {
									orderList.add(cb.desc(p.get(orderItemDesc)));
								}
							}

						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return orderList;
	}

	public <T> CompoundSelection<T> getProjection(CriteriaBuilder cb, Root<T> p) {
		ICriteria<T> criteria = getCriteria();
		if (criteria != null) {
			if (criteria instanceof IProjection) {
				IProjection<T> projection = (IProjection<T>) criteria;
				return projection.projection(cb, p);
			} else {
				if (criteria instanceof IRestriction) {
					try {
						Method method = criteria.getClass().getMethod("restriction", CriteriaBuilder.class, Root.class);
						if (method.isAnnotationPresent(Projection.class)) {
							Projection projection = method.getAnnotation(Projection.class);
							String[] projectionItemList = projection.value();

						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

}
