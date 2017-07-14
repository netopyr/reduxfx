package com.netopyr.reduxfx.examples.treeview.updater;

import com.netopyr.reduxfx.examples.treeview.actions.AddSprintAction;
import com.netopyr.reduxfx.examples.treeview.actions.AddStoryToSprintAction;
import com.netopyr.reduxfx.examples.treeview.actions.UpdateTextFieldAction;
import com.netopyr.reduxfx.examples.treeview.state.AppState;
import com.netopyr.reduxfx.examples.treeview.state.Project;
import com.netopyr.reduxfx.examples.treeview.state.Sprint;
import com.netopyr.reduxfx.examples.treeview.state.Story;
import com.netopyr.reduxfx.examples.treeview.state.TextFieldID;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.Objects;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class Updater {

	private Updater() {
	}

	/**
	 * Sprints use an integer as identifier.
	 * This function is used to find the last id that was used.
	 *
	 * @param state the appstate
	 * @return the last id that was used or <code>0</code> if no sprint was created yet.
	 */
	private static int getLastSprintId(AppState state) {
		return state
			.getProject()
			.getSprints()
			.map(Sprint::getId)
			.max()
			.getOrElse(0);
	}

	/**
	 * Stories use an integer as identifier.
	 * This function is used to find the last id that was used.
	 *
	 * @param state the appstate
	 * @return the last id that was used or <code>0</code> if no story was created yet.
	 */
	private static int getLastStoryId(AppState state) {
		return state
			.getProject()
			.getSprints()
			.flatMap(Sprint::getStories)
			.map(Story::getId)
			.max()
			.getOrElse(0);
	}


	public static AppState update(AppState state, Object action) {
		Objects.requireNonNull(state, "The parameter 'state' must not be null");
		Objects.requireNonNull(action, "The parameter 'action' must not be null");

		return Match(action).of(
			Case($(instanceOf(AddSprintAction.class)),
				addSprintAction -> {
					// get the text value from the UI
					final String title = state.getTextFieldValue(TextFieldID.SPRINT_TITLE);

					final int lastSprintId = getLastSprintId(state);

					// create a new sprint object
					final Sprint newSprint = Sprint.create()
						.withId(lastSprintId + 1)
						.withTitle(title);

					// append the sprint to the root project. This creates a new project instance
					final Project projectWithNewSprint = state.getProject().appendSprints(newSprint);

					// update the project in the state.
					return state.withProject(projectWithNewSprint)
						// reset the text field
						.withTextFieldValue(TextFieldID.SPRINT_TITLE, "");
				}),

			Case($(instanceOf(AddStoryToSprintAction.class)),
				addStoryToSprintAction -> {
					// new stories are always added to the current/last sprint
					final Option<Sprint> currentSprintOption = state.getProject().getSprints().lastOption();

					// check if we already have a sprint created
					if (currentSprintOption.isDefined()) {
						// if a sprint exists
						final Sprint currentSprint = currentSprintOption.get();

						// get the value from the textfield
						final String title = state.getTextFieldValue(TextFieldID.STORY_TITLE);
						final int lastStoryId = getLastStoryId(state);

						// create a new story object
						final Story newStory = Story.create()
							.withId(lastStoryId + 1)
							.withTitle(title);

						// append the story to the current sprint. This creates a new sprint object.
						final Sprint currentSprintWithNewStory = currentSprint.appendStories(newStory);

						// the project has a list of sprints.
						// Because the current sprint was changed
						// we need to replace it in the project's sprint list.
						final Seq<Sprint> newSprintList = state
							.getProject()
							.getSprints()
							.replace(currentSprint, currentSprintWithNewStory);

						return state
							// update the project with the new sprint list
							.withProject(state.getProject().withSprints(newSprintList))
							// reset the textfield
							.withTextFieldValue(TextFieldID.STORY_TITLE, "");
					} else {
						return state;
					}
				}
			),

			Case($(instanceOf(UpdateTextFieldAction.class)),
				updateTextFieldAction -> {
					final TextFieldID textFieldID = updateTextFieldAction.getTextFieldID();
					final String value = updateTextFieldAction.getValue();

					return state.withTextFieldValue(textFieldID, value);
				}
			),

			Case($(), state)
		);
	}
}
