package br.gov.component.demoiselle.xvalidation.internal.context;

import java.io.Serializable;

public interface ValidationContext extends Serializable {
	void addValidationParam(Object... params);

	Object[] getValidationParam();

	void clearValidation();

	Boolean isAllValid();

	void addValidation(Boolean value);
}
