package com.netopyr.reduxfx;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.mainloop.MainLoop;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ReduxFX<STATE> {

    public static <STATE> ReduxFX<STATE> start(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater, Function<STATE, VNode> view, Stage stage) {
        final StackPane root = new StackPane();
        root.setMinWidth(Region.USE_PREF_SIZE);
        root.setMinHeight(Region.USE_PREF_SIZE);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        stage.setScene(new Scene(root));

        return new ReduxFX<>(initialState, updater, view, root);
    }

    public static <STATE> ReduxFX<STATE> start(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater, Function<STATE, VNode> view, Group root) {
        return new ReduxFX<>(initialState, updater, view, root);
    }

    public static <STATE> ReduxFX<STATE> start(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater, Function<STATE, VNode> view, Pane root) {
        return new ReduxFX<>(initialState, updater, view, root);
    }

    private final MainLoop mainLoop;

    protected ReduxFX(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater, Function<STATE, VNode> view, Parent root) {
        mainLoop = new MainLoop(initialState, updater, view, root);
    }

    public void start() {
        mainLoop.start();
    }

    public void stop() {
        mainLoop.stop();
    }

    public void dispatch(Object action) {
        mainLoop.dispatch(action);
    }

    public void registerDriver(Driver driver) {
        mainLoop.getCommandObservable().subscribe(driver.getCommandObserver());
        driver.getActionObservable().forEach(mainLoop::dispatch);
    }

}
