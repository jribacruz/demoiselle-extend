package br.gov.component.demoiselle.ui.helper.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface UICrudMessages {
	String onSave() default "{entity.save.success}";

	String onUpdate() default "{entity.update.success}";

	String onInsert() default "{entity.insert.success}";

	String onDelete() default "{entity.delete.success}";

	String onDeleteSelection() default "{entity.delete.selection.success}";
}
