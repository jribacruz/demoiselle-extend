package br.gov.component.demoiselle.xvalidation.event;

public class BeforeDeleteEvent {
	Object obj;

	public BeforeDeleteEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public BeforeDeleteEvent() {
		super();
	}

	public Object getBean() {
		return obj;
	}

}
