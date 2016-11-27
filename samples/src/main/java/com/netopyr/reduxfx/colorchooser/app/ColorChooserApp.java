package com.netopyr.reduxfx.colorchooser.app;

import com.netopyr.reduxfx.SimpleReduxFX;
import com.netopyr.reduxfx.colorchooser.app.state.AppModel;
import com.netopyr.reduxfx.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.colorchooser.app.view.MainView;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorChooserApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final AppModel initialState = new AppModel().withColor(Color.VIOLET);

        SimpleReduxFX.start(initialState, Updater::update, MainView::view, primaryStage);

        primaryStage.setTitle("ColorChooser Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
