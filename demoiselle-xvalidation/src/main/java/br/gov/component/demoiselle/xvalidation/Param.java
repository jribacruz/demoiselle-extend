package br.gov.component.demoiselle.xvalidation;

import java.io.Serializable;

public interface Param extends Serializable {
	void add(Object... params);

	Object[] get();

	void addSuccessParam(Object... params);

	Object[] getSuccessParam();

	void addFailureParam(Object... params);

	Object[] getFailureParam();
}
