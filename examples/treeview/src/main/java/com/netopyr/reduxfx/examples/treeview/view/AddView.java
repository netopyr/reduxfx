package com.netopyr.reduxfx.examples.treeview.view;

import com.netopyr.reduxfx.examples.treeview.actions.Actions;
import com.netopyr.reduxfx.examples.treeview.state.AppState;
import com.netopyr.reduxfx.examples.treeview.state.TextFieldID;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TextField;

/**
 * The {@link AddView} is responsible for creating new Sprints and Stories.
 * Therefore it consists of a simple form.
 */
public class AddView {
	private AddView() {
	}

	public static VNode view(AppState state) {
		Objects.requireNonNull(state);
		return HBox()
			.spacing(5)
			.padding(5)
			.children(
				HBox()
					.alignment(Pos.BASELINE_LEFT)
					.spacing(2)
					.children(
						Label().text("Sprint title:"),
						TextField()
							.text(
								state.getTextFieldValue(TextFieldID.SPRINT_TITLE),
								(oldValue, newValue) -> Actions
									.updateTextField(TextFieldID.SPRINT_TITLE, newValue)
							),
						Button()
							.text("Add Sprint")
							.onAction(event -> Actions.addSprint())
					),
				HBox()
					.alignment(Pos.BASELINE_LEFT)
					.spacing(2)
					.children(
						Label().text("Story title:"),
						TextField()
							.text(
								state.getTextFieldValue(TextFieldID.STORY_TITLE),
								((oldValue, newValue) -> Actions
									.updateTextField(TextFieldID.STORY_TITLE, newValue))
							),
						Button()
							.text("Add Story to current Sprint")
							.disable(state.getProject().getSprints().isEmpty())
							.onAction(event -> Actions.addStoryToSprint())
					)
			);
	}
}
