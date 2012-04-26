package br.gov.component.demoiselle.xvalidation.event;

public class AfterSaveEvent {
	Object obj;

	public AfterSaveEvent() {
		super();
	}

	public AfterSaveEvent(Object obj) {
		super();
		this.obj = obj;
	}

	public Object getBean() {
		return obj;
	}
}
