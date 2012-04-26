package br.gov.component.demoiselle.xvalidation.internal.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import br.gov.component.demoiselle.xvalidation.internal.context.ValidationContext;

@SessionScoped
public class ValidationContextImpl implements ValidationContext {
	private static final long serialVersionUID = 1L;

	private List<Boolean> validations = new ArrayList<Boolean>();

	private Object[] validationParam;

	public void addValidationParam(Object... params) {
		validationParam = params;
	}

	public Object[] getValidationParam() {
		return this.validationParam;
	}

	public void clearValidation() {
		validationParam = null;
		validations.clear();
	}

	public Boolean isAllValid() {
		return !validations.contains(Boolean.FALSE);
	}

	public void addValidation(Boolean value) {
		validations.add(value);
	}

}
