package br.gov.component.demoiselle.xvalidation.event;

public class AfterInsertEvent {
	Object obj;

	public AfterInsertEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public AfterInsertEvent() {
		super();
	}

	public Object getBean() {
		return obj;
	}

}
