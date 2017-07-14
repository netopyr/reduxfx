package com.netopyr.reduxfx.examples.treeview.view;

import com.netopyr.reduxfx.examples.treeview.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

/**
 * The {@link MainView} is the root ui container of the application and composes other ui containers.
 */
public class MainView {

	private MainView() {
	}

	public static VNode view(AppState state) {
		Objects.requireNonNull(state);

		return Stage()
			.title("Treeview Example: Scrum Sprint Planning")
			.showing(true)
			.scene(
				Scene()
					.root(
						VBox()
							.children(
								Label()
									.style("-fx-font-size:30")
									.text("Treeview Example: Scrum Sprint Planning"),
								AddView.view(state),
								SprintsTreeView.view(state)
							)
					)
			);
	}
}