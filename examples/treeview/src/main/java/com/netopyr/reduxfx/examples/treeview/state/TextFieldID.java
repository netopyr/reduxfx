package com.netopyr.reduxfx.examples.treeview.state;

/**
 * This enum is used to simplify the handling of {@link TextFieldID} values via reduxfx.
 * Instead of keeping the current value for each textfield in the store and create
 * new UpdateActions classes for each textfield,
 * the developer can use a general action {@link com.netopyr.reduxfx.examples.treeview.actions.UpdateTextFieldAction}
 * instead.
 * <p/>
 *
 * When new Textfields are added to the application, add a new entry to this enum.
 */
public enum TextFieldID {
	SPRINT_TITLE,
	STORY_TITLE
}
