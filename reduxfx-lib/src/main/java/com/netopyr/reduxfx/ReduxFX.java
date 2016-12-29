package com.netopyr.reduxfx;

import com.netopyr.reduxfx.driver.ActionSupplier;
import com.netopyr.reduxfx.driver.CommandConsumer;
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
import javaslang.Function2;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ReduxFX<STATE, ACTION> {

    public static <STATE, ACTION> ReduxFX<STATE, ACTION> start(STATE initialState, Function2<STATE, ACTION, Update<STATE>> updater, Function<STATE, VNode<ACTION>> view, Stage stage) {
        final StackPane root = new StackPane();
        root.setMinWidth(Region.USE_PREF_SIZE);
        root.setMinHeight(Region.USE_PREF_SIZE);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        stage.setScene(new Scene(root));

        return new ReduxFX<>(initialState, updater, view, root);
    }

    public static <STATE, ACTION> ReduxFX<STATE, ACTION> start(STATE initialState, BiFunction<STATE, ACTION, Update<STATE>> updater, Function<STATE, VNode<ACTION>> view, Group root) {
        return new ReduxFX<>(initialState, updater, view, root);
    }

    public static <STATE, ACTION> ReduxFX<STATE, ACTION> start(STATE initialState, BiFunction<STATE, ACTION, Update<STATE>> updater, Function<STATE, VNode<ACTION>> view, Pane root) {
        return new ReduxFX<>(initialState, updater, view, root);
    }

    private final MainLoop<ACTION> mainLoop;

    protected ReduxFX(STATE initialState, BiFunction<STATE, ACTION, Update<STATE>> updater, Function<STATE, VNode<ACTION>> view, Parent root) {
        mainLoop = new MainLoop<>(initialState, updater, view, root);
    }

    public void start() {
        mainLoop.start();
    }

    public void stop() {
        mainLoop.stop();
    }

    public void dispatch(ACTION action) {
        mainLoop.dispatch(action);
    }

    public void register(Driver<ACTION> driver) {
        mainLoop.getCommandObservable().subscribe(driver.getCommandObserver());
        driver.getActionObservable().forEach(mainLoop::dispatch);
    }

    public void register(ActionSupplier<ACTION> actionSupplier) {
        actionSupplier.getActionObservable().forEach(mainLoop::dispatch);
    }

    public void register(CommandConsumer commandConsumer) {
        mainLoop.getCommandObservable().subscribe(commandConsumer.getCommandObserver());
    }

}
