package br.gov.component.demoiselle.jsf.validation.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.component.demoiselle.jsf.validation.processor.Validator;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.template.Crud;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@Decorator
public class ValidationDecorator<T, I, C extends Crud<T, I>> extends DelegateCrud<T, I, C> {
	private static final long serialVersionUID = 1L;

	@Inject
	@Any
	@Delegate
	private DelegateCrud<T, I, C> bc;

	@Inject
	private Validator validator;

	@Inject
	private FacesContext facesContext;

	@Inject
	private MessageContext messageContext;

	@Inject
	private Logger log;

	@Override
	public void delete(I id) {
		messageContext.clear();
		if (validator.validateOnDelete(bc, id)) {
			bc.delete(id);
		} else {
			facesContext.validationFailed();
		}
	}

	@Override
	public void delete(List<I> idList) {
		messageContext.clear();
		if (validator.validateOnDelete(bc, idList)) {
			bc.delete(idList);
		} else {
			facesContext.validationFailed();
		}
	}

	@Override
	public void insert(T bean) {
		messageContext.clear();
		boolean validOnInsert = validator.validateOnInsert(bc, bean);
		boolean validOnSave = validator.validateOnSave(bc, bean);
		if (validOnSave && validOnInsert) {
			bc.insert(bean);
		} else {
			facesContext.validationFailed();
		}
	}

	@Override
	public void update(T bean) {
		messageContext.clear();
		boolean validOnInsert = validator.validateOnInsert(bc, bean);
		boolean validOnSave = validator.validateOnSave(bc, bean);
		if (validOnSave && validOnInsert) {
			bc.update(bean);
		} else {
			facesContext.validationFailed();
		}
	}

	@Override
	public List<T> findAll() {
		return bc.findAll();
	}

	@Override
	public T load(I id) {
		return bc.load(id);
	}

}
