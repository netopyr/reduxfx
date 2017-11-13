package com.netopyr.reduxfx.examples.treeview;

import com.netopyr.reduxfx.examples.treeview.state.AppState;
import com.netopyr.reduxfx.examples.treeview.updater.Updater;
import com.netopyr.reduxfx.examples.treeview.view.MainView;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.stage.Stage;

public class TreeViewExample extends Application {


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final AppState initialState = AppState.create();

		final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState,
			(appState, action) -> Update.of(Updater.update(appState, action)));

		ReduxFXView.createStage(store, MainView::view, primaryStage);
	}
}
