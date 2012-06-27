package br.gov.component.demoiselle.jsf.criteria.implementation;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.component.demoiselle.jsf.criteria.context.CriteriaContext;
import br.gov.component.demoiselle.jsf.criteria.processor.CriteriaProcessor;

@SessionScoped
public class CriteriaProcessorImpl implements CriteriaProcessor {
	private static final long serialVersionUID = 1L;

	@Inject
	private CriteriaContext context;


}
