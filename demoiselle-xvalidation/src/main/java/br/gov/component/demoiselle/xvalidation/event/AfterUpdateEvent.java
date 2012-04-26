package br.gov.component.demoiselle.xvalidation.event;

public class AfterUpdateEvent {
	Object obj;

	public AfterUpdateEvent() {
		super();
	}

	public AfterUpdateEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public Object getBean() {
		return obj;
	}

}
