package com.netopyr.reduxfx.examples.treeview.actions;

import com.netopyr.reduxfx.examples.treeview.state.TextFieldID;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This Action is used to update textfield values. Instead of creating a new Action class
 * for each TextField, the developer can use this general Action and specify the
 * textfield by providing a {@link TextFieldID} enum value and the new text for this textfield.
 */
public class UpdateTextFieldAction {
	private final TextFieldID textFieldID;
	private final String value;

	public UpdateTextFieldAction(TextFieldID textFieldID, String value) {
		this.textFieldID = textFieldID;
		this.value = value;
	}

	public TextFieldID getTextFieldID() {
		return textFieldID;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.toString();
	}
}
