package br.gov.component.demoiselle.ui.helper.base;

import java.io.Serializable;

import javax.inject.Inject;

import br.gov.component.demoiselle.ui.helper.annotations.UICrudHelper;
import br.gov.component.demoiselle.ui.helper.annotations.UICrudMessages;
import br.gov.component.demoiselle.ui.helper.config.UIPageConfig;
import br.gov.component.demoiselle.ui.helper.core.UIHelper;
import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

public class UIPageHelper<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UIPageConfig config;
	@Inject
	private ResourceBundle bundle;
	@Inject
	private UIHelper helper;

	private Class<T> beanClass;

	public UIPageHelper() {
		super();
	}

	/**
	 * Atualiza os compenentes visuais apos salvar
	 * 
	 */
	public String updateUIAfterSave() {
		if (!helper.isValidationFailed()) {
			hideDialog();
			updateDataTable();
			addSaveMessage();
			showGrowl();
			return null;
		}
		showGlobalMessageDialog();
		return null;
	}

	public String updateUIAfterInsert() {
		if (!helper.isValidationFailed()) {
			hideDialog();
			updateDataTable();
			addInsertMessage();
			showGrowl();
			return null;
		}
		showGlobalMessageDialog();
		return null;
	}

	public String updateUIAfterUpdate() {
		if (!helper.isValidationFailed()) {
			hideDialog();
			updateDataTable();
			addUpdateMessage();
			showGrowl();
			return null;
		}
		showGlobalMessageDialog();
		return null;
	}

	/**
	 * Atualiza os componentes visuais apos deletar
	 */
	public String updateUIAfterDelete() {
		if (!helper.isValidationFailed()) {
			updateDataTable();
			addDeleteMessage();
			updateDataTableToolbar();
			showGrowl();
			return null;
		}
		return null;
	}

	public String updateUIAfterDeleteSelection() {
		if (!helper.isValidationFailed()) {
			updateDataTable();
			addDeleteSelectionMessage();
			updateDataTableToolbar();
			showGrowl();
			return null;
		}
		return null;
	}

	/**
	 * Atualiza os componentes visuais apos carregar
	 */
	public String updateUIAfterLoad() {
		updateDialogBody();
		showDialog();
		return null;
	}

	/**
	 * Atualiza os componentes apos criar
	 * 
	 */
	public String updateUIAfterCreate() {
		updateDialogBody();
		showDialog();
		return null;
	}

	/**
	 * Atualiza o dataTable
	 */
	public void updateDataTable() {
		helper.update(getFormDataTableId(), getDataTableId());
	}

	/**
	 * Atualiza o corpo do dialog
	 */
	public void updateDialogBody() {
		helper.update(getFormDialogId(), getDialogBodyId());
	}

	public void updateDataTableToolbar() {
		helper.update(getFormToolbarId(), getToolbarId());
	}

	/**
	 * Abre o dialog
	 */
	public void showDialog() {
		showDialog(getDialogWVar());
	}

	/**
	 * Fecha o dialog
	 */
	public void hideDialog() {
		hideDialog(getDialogWVar());
	}

	/**
	 * Abre o dialog de messages globais
	 */
	public void showGlobalMessageDialog() {
		showDialog(config.getGlobalMessagesWVar());
	}

	/**
	 * Fechar o dialog de messages gloais
	 */
	public void hideGlobalMessageDialog() {
		hideDialog(config.getGlobalMessagesWVar());
	}

	/**
	 * Exibe uma notificação
	 */
	public void showGrowl() {
		helper.update(config.getGrowlId());
	}

	private void addSaveMessage() {
		helper.addMessage(getOnSaveMessage(), getClassName());
	}

	private void addDeleteMessage() {
		helper.addMessage(getOnDeleteMessage(), getClassName());
	}

	private void addDeleteSelectionMessage() {
		helper.addMessage(getOnDeleteSelectionMessage(), getClassName());
	}

	private void addInsertMessage() {
		helper.addMessage(getOnInsertMessage(), getClassName());
	}

	private void addUpdateMessage() {
		helper.addMessage(getOnUpdateMessage(), getClassName());
	}

	public void showDialog(String wvar) {
		helper.execute(wvar.concat(".show()"));
	}

	public void hideDialog(String wvar) {
		helper.execute(wvar.concat(".hide()"));
	}

	/* Ids */

	public String getFormDataTableId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].formDataTableId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].formDataTableId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getFormDatatableIdSuffix());
	}

	public String getFormDialogId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].formDialogId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].formDialogId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getFormDialogIdSuffix());
	}

	public String getFormToolbarId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].formToolbarId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].formToolbarId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getFormToolbarIdSuffix());
	}

	public String getToolbarId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].toolbarId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].toolbarId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getToolbarIdSuffix());
	}

	public String getDialogId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].formDialogId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].dialogId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getDialogIdSuffix());
	}

	public String getDialogBodyId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].dialogBodyId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].dialogBodyId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getDialogBodyIdSuffix());
	}

	public String getDialogWVar() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].dialogWVar().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].dialogWVar();
		}
		return getClassName().toLowerCase().concat("_").concat(config.getDialogWVarSuffix());
	}

	public String getDataTableId() {
		if (this.getClass().getAnnotation(UICrudHelper.class).components().length > 0
				&& !this.getClass().getAnnotation(UICrudHelper.class).components()[0].dataTableId().isEmpty()) {
			return this.getClass().getAnnotation(UICrudHelper.class).components()[0].dataTableId();
		}
		return getClassName().toLowerCase().concat("-").concat(config.getDataTableIdSuffix());
	}

	/* Messages */
	public String getOnSaveMessage() {
		if (this.getClass().getAnnotation(UICrudHelper.class).messages().length > 0
				&& !this.getClass().getAnnotation(UICrudMessages.class).onSave().isEmpty()) {
			return this.getClass().getAnnotation(UICrudMessages.class).onSave();
		}
		return bundle.getString("entity.save.success");
	}

	public String getOnInsertMessage() {
		if (this.getClass().getAnnotation(UICrudHelper.class).messages().length > 0
				&& !this.getClass().getAnnotation(UICrudMessages.class).onInsert().isEmpty()) {
			return this.getClass().getAnnotation(UICrudMessages.class).onInsert();
		}
		return bundle.getString("entity.insert.success");
	}

	public String getOnUpdateMessage() {
		if (this.getClass().getAnnotation(UICrudHelper.class).messages().length > 0
				&& !this.getClass().getAnnotation(UICrudMessages.class).onUpdate().isEmpty()) {
			return this.getClass().getAnnotation(UICrudMessages.class).onUpdate();
		}
		return bundle.getString("entity.update.success");
	}

	public String getOnDeleteMessage() {
		if (this.getClass().getAnnotation(UICrudHelper.class).messages().length > 0
				&& !this.getClass().getAnnotation(UICrudMessages.class).onDelete().isEmpty()) {
			return this.getClass().getAnnotation(UICrudMessages.class).onDelete();
		}
		return bundle.getString("entity.delete.success");
	}

	public String getOnDeleteSelectionMessage() {
		if (this.getClass().getAnnotation(UICrudHelper.class).messages().length > 0
				&& !this.getClass().getAnnotation(UICrudMessages.class).onDeleteSelection().isEmpty()) {
			return this.getClass().getAnnotation(UICrudMessages.class).onDeleteSelection();
		}
		return bundle.getString("entity.delete.selection.success");
	}

	private String getClassName() {
		return this.getBeanClass().getSimpleName();
	}

	public UIPageConfig getConfig() {
		return config;
	}

	public void setConfig(UIPageConfig config) {
		this.config = config;
	}

	protected Class<T> getBeanClass() {

		if (this.beanClass == null) {
			this.beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
		}

		return this.beanClass;
	}

}
