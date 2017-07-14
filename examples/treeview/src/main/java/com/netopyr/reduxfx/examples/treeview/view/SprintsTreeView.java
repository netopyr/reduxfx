package com.netopyr.reduxfx.examples.treeview.view;

import com.netopyr.reduxfx.examples.treeview.state.AppState;
import com.netopyr.reduxfx.examples.treeview.state.Project;
import com.netopyr.reduxfx.examples.treeview.state.Sprint;
import com.netopyr.reduxfx.examples.treeview.state.Story;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TreeItem;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TreeView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;


/**
 * This view shows the sprint data in a {@link TreeView}.
 * <p/>
 * In JavaFX the {@link TreeView} is created out of {@link TreeItem} objects.
 * TreeItem and TreeView have a generic type that is used for the wrapped data objects.
 * This generic type has to be the same for the whole tree.
 * However, in this example project we have a tree structure that consists of different types
 * of objects ({@link Project}, {@link Sprint}, {@link Story}) we use place {@link Object}
 * as generic type for the TreeView.
 * For this approach to work we define a cellFactory that chooses a suitable string representation
 * for each type of tree item value.
 */
public class SprintsTreeView {
	private SprintsTreeView() {
	}

	public static VNode view(AppState state) {
		Objects.requireNonNull(state);

		return VBox()
			.vgrow(Priority.ALWAYS)
			.children(
				TreeView(Object.class)
					.vgrow(Priority.ALWAYS)
					.showRoot(false) // the root object is hidden in this example
					// the cellFactory defines how each tree item is rendered based on the wrapped
					// data object.
					.cellFactory(item -> Label().text(
						// pattern-match to decide how each type of object is rendered
						Match(item).of(
							Case($(instanceOf(Sprint.class)), Sprint::getTitle),
							Case($(instanceOf(Story.class)), Story::getTitle),
							Case($(), Object::toString)
						))
					)
					.root(
						TreeItem(Object.class) // the root object itself is a tree item too
							.value(state.getProject())
							.expanded(true)
							.children(
								state.getProject()
									.getSprints()
									.map(sprint ->
										TreeItem(Object.class)
											.expanded(true)
											.value(sprint)
											.children(sprint
												.getStories()
												.map(story -> TreeItem(Object.class)
													.expanded(true)
													.value(story)
												)
											)
									)
							)
					)
			);
	}
}
