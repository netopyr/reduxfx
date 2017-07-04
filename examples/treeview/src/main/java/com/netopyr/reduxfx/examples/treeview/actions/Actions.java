package com.netopyr.reduxfx.examples.treeview.actions;

import com.netopyr.reduxfx.examples.treeview.state.TextFieldID;

public final class Actions {

	private Actions() {
	}

	public static AddSprintAction addSprint() {
		return new AddSprintAction();
	}

	public static AddStoryToSprintAction addStoryToSprint() {
		return new AddStoryToSprintAction();
	}

	public static UpdateTextFieldAction updateTextField(TextFieldID textFieldID, String value) {
		return new UpdateTextFieldAction(textFieldID, value);
	}
}
