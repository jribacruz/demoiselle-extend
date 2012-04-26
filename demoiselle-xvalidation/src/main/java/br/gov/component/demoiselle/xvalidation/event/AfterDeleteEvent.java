package br.gov.component.demoiselle.xvalidation.event;

public class AfterDeleteEvent {
	Object obj;

	public AfterDeleteEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public AfterDeleteEvent() {
		super();
	}

	public Object getBean() {
		return obj;
	}
}
