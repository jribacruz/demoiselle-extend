package br.gov.component.demoiselle.xvalidation.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.component.demoiselle.xvalidation.event.AfterDeleteEvent;
import br.gov.component.demoiselle.xvalidation.event.AfterInsertEvent;
import br.gov.component.demoiselle.xvalidation.event.AfterSaveEvent;
import br.gov.component.demoiselle.xvalidation.event.AfterUpdateEvent;
import br.gov.component.demoiselle.xvalidation.event.BeforeDeleteEvent;
import br.gov.component.demoiselle.xvalidation.event.BeforeInsertEvent;
import br.gov.component.demoiselle.xvalidation.event.BeforeSaveEvent;
import br.gov.component.demoiselle.xvalidation.event.BeforeUpdateEvent;
import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;
import br.gov.component.demoiselle.xvalidation.internal.implementation.DomainQualifierImpl;
import br.gov.frameworkdemoiselle.template.Crud;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.util.Reflections;

@Decorator
public class DelegateCrudDecorator<T, I, C extends Crud<T, I>> extends DelegateCrud<T, I, C> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Delegate
	@Any
	private DelegateCrud<T, I, C> bc;

	@Inject
	@Any
	private Event<BeforeSaveEvent> beforeSaveEvent;

	@Inject
	@Any
	private Event<AfterSaveEvent> AfterSaveEvent;

	@Inject
	@Any
	private Event<BeforeInsertEvent> beforeInsertEvent;

	@Inject
	@Any
	private Event<AfterInsertEvent> afterInsertEvent;

	@Inject
	@Any
	private Event<BeforeUpdateEvent> beforeUpdateEvent;

	@Inject
	@Any
	private Event<AfterUpdateEvent> afterUpdateEvent;

	@Inject
	@Any
	private Event<BeforeDeleteEvent> beforeDeleEvent;

	@Inject
	@Any
	private Event<AfterDeleteEvent> afterDeleteEvent;

	@Inject
	private Logger logger;

	@Inject
	private ValidationContext validationContext;

	private Class<T> beanClass;

	@Override
	public void delete(I id) {
		fire(beforeDeleEvent, new BeforeDeleteEvent(id), id);

		if (validationContext.isAllValid()) {
			bc.delete(id);
		}

		fire(afterDeleteEvent, new AfterDeleteEvent(id), id);
		// validationContext.clearValidation();
	}

	@Override
	public void delete(List<I> idList) {
		fire(beforeDeleEvent, new BeforeDeleteEvent(idList), idList);
		if (validationContext.isAllValid()) {
			bc.delete(idList);
		}
		fire(afterDeleteEvent, new AfterDeleteEvent(idList), idList);
		// validationContext.clearValidation();
	}

	@Override
	public void insert(T bean) {
		fire(beforeInsertEvent, new BeforeInsertEvent(bean), bean);
		fire(beforeSaveEvent, new BeforeSaveEvent(bean), bean);
		if (validationContext.isAllValid()) {
			bc.insert(bean);
		}
		fire(AfterSaveEvent, new AfterSaveEvent(bean), bean);
		fire(afterInsertEvent, new AfterInsertEvent(bean), bean);
		// validationContext.clearValidation();
	}

	@Override
	public void update(T bean) {
		fire(beforeUpdateEvent, new BeforeUpdateEvent(bean), bean);
		fire(beforeSaveEvent, new BeforeSaveEvent(bean), bean);
		if (validationContext.isAllValid()) {
			bc.update(bean);
		}
		fire(AfterSaveEvent, new AfterSaveEvent(bean), bean);
		fire(afterUpdateEvent, new AfterUpdateEvent(bean), bean);
		// validationContext.clearValidation();
	}

	@Override
	public List<T> findAll() {
		return bc.findAll();
	}

	@Override
	public T load(I id) {
		return bc.load(id);
	}

	private Class<?> getDomainClass() {
		if (beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(bc.getClass(), 0);
		}
		return this.beanClass;
	}

	private <K> void fire(Event<K> event, K eventKlass, Object arg) {
		logger.debug("Disparando evento {} para domain {} ", eventKlass, arg);
		event.select(new DomainQualifierImpl(getDomainClass())).fire(eventKlass);
	}

}
