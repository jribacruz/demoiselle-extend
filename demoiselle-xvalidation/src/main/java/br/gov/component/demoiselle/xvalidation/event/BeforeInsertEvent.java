package br.gov.component.demoiselle.xvalidation.event;

public class BeforeInsertEvent {
	Object obj;

	public BeforeInsertEvent() {
		super();
	}

	public BeforeInsertEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public Object getBean() {
		return obj;
	}

}
