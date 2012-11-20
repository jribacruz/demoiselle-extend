package br.gov.component.demoiselle.jsf.restriction.annotation;

public @interface PredicateGroup {
	String name();

	OperatorType operator() default OperatorType.AND;
}
