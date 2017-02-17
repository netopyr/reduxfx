package com.netopyr.reduxfx;

import com.netopyr.reduxfx.driver.ActionSupplier;
import com.netopyr.reduxfx.driver.CommandConsumer;
import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.impl.mainloop.MainLoop;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ReduxFX {

    public static <STATE> ReduxFX startStages(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.createStages(initialState, updater, view, primaryStage);
        return new ReduxFX(mainLoop);
    }

    public static <STATE> ReduxFX startStage(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.createStage(initialState, updater, view, primaryStage);
        return new ReduxFX(mainLoop);
    }

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, updater, view, primaryStage);
        return new ReduxFX(mainLoop);
    }

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Group group
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, updater, view, group);
        return new ReduxFX(mainLoop);
    }

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Pane pane
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, updater, view, pane);
        return new ReduxFX(mainLoop);
    }


    private final MainLoop mainLoop;


    protected ReduxFX(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
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

    public void register(Driver driver) {
        mainLoop.getCommandProcessor().subscribe(driver.getCommandObserver());
        driver.getActionObservable().forEach(mainLoop::dispatch);
    }

    public void register(ActionSupplier actionSupplier) {
        actionSupplier.getActionObservable().forEach(mainLoop::dispatch);
    }

    public void register(CommandConsumer commandConsumer) {
        mainLoop.getCommandProcessor().subscribe(commandConsumer.getCommandObserver());
    }

}
