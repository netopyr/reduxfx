package com.netopyr.reduxfx.examples.treeview.state;

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class AppState {

	/**
	 * The project to which we will add Scrum Sprints later.
	 * <p>
	 * In JavaFX the TreeView needs exactly one root item for which we will use this project.
	 */
	private final Project project;

	/**
	 * This map holds the current values for all textfields in the application.
	 * This is done by mapping from {@link TextFieldID} enum to the actual value.
	 */
	private final Map<TextFieldID, String> textFieldValues;

	private AppState(Project project, Map<TextFieldID, String> textFieldValues) {
		this.project = project;
		this.textFieldValues = textFieldValues;
	}

	public static AppState create() {
		return new AppState(Project.create(),
			HashMap.ofEntries(
				Tuple.of(TextFieldID.SPRINT_TITLE, ""),
				Tuple.of(TextFieldID.STORY_TITLE, "")
			)
		);
	}

	public Project getProject() {
		return project;
	}

	public AppState withProject(Project project) {
		return new AppState(project, this.textFieldValues);
	}

	/**
	 * Update the value of a textfield.
	 */
	public AppState withTextFieldValue(TextFieldID textFieldID, String value) {
		return new AppState(this.project, this.textFieldValues.put(textFieldID, value));
	}

	public String getTextFieldValue(TextFieldID textFieldID) {
		return textFieldValues.get(textFieldID).getOrElse("");
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.toString();
	}
}
