package com.netopyr.reduxfx.examples.fxml;

import com.netopyr.reduxfx.examples.fxml.state.AppState;
import com.netopyr.reduxfx.examples.fxml.updater.Updater;
import com.netopyr.reduxfx.examples.fxml.view.MainView;
import com.netopyr.reduxfx.fxml.Dispatcher;
import com.netopyr.reduxfx.fxml.ReduxFxml;
import com.netopyr.reduxfx.fxml.Selector;
import com.netopyr.reduxfx.fxml.ViewLoader;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.store.ReduxFXStore;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class FxmlExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppState initialState = AppState.create();

        // Setup the ReduxFX-store passing the initialState and the update-function
        final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());


        // Create an instance of ReduxFxml
		ReduxFxml<AppState> reduxFxml = ReduxFxml.create(store);


		// Setup dependency injection context
		final EasyDI diContext = new EasyDI();

		// When an instance of Selector needs to be injected, use the reduxFxml instance
		diContext.bindInstance(Selector.class, reduxFxml);
		// When an instance of Dispatcher needs to be injected, use the reduxFxml instance
		diContext.bindInstance(Dispatcher.class, reduxFxml);

		// ViewLoad is a util that can load Fxml hierarchies without having to handling path URLs.
		// Instead it is based naming convention and class types.

		// Tell ViewLoader how dependency injection should be done
		ViewLoader.setDependencyInjector(diContext::getInstance);

        // ViewLoader.load() loads the FXML-file and sets up the controller
        final Parent root = ViewLoader.load(MainView.class);


        // Notice: Using the ViewLoader is optional and not required to use ReduxFxml.
		// You can also use the standard FxmlLoader like this:
		/*
		FXMLLoader fxmlLoader = new FXMLLoader();

		fxmlLoader.setControllerFactory(diContext::getInstance);
		fxmlLoader.setLocation(this.getClass().getResource("/com/netopyr/reduxfx/examples/fxml/view/MainView.fxml"));

		final Parent root = fxmlLoader.load();
		*/


        // Setup the stage and show it
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("FXML Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
