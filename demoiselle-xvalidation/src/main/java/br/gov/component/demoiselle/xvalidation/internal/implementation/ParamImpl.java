package br.gov.component.demoiselle.xvalidation.internal.implementation;

import javax.enterprise.context.SessionScoped;

import br.gov.component.demoiselle.xvalidation.Param;

@SessionScoped
public class ParamImpl implements Param {
	private static final long serialVersionUID = 1L;

	Object[] params;

	Object[] failureParams;

	Object[] successParams;

	public void add(Object... params) {
		this.params = params;
	}

	public Object[] get() {
		return this.params;
	}

	public void addSuccessParam(Object... params) {
		this.successParams = params;
	}

	public Object[] getSuccessParam() {
		return this.successParams;
	}

	public void addFailureParam(Object... params) {
		this.failureParams = params;
	}

	public Object[] getFailureParam() {
		return this.failureParams;
	}

}
