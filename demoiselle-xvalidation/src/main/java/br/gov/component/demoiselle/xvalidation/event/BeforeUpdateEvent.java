package br.gov.component.demoiselle.xvalidation.event;

public class BeforeUpdateEvent {
	Object obj;

	public BeforeUpdateEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public BeforeUpdateEvent() {
		super();
	}

	public Object getBean() {
		return obj;
	}

}
