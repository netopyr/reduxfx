package com.netopyr.reduxfx;

import com.netopyr.reduxfx.driver.ActionSupplier;
import com.netopyr.reduxfx.driver.CommandConsumer;
import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.mainloop.MainLoop;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ReduxFX {

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        return new ReduxFX(initialState, updater, view, primaryStage);
    }

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Group group)
    {
        return new ReduxFX(initialState, updater, view, group);
    }

    public static <STATE> ReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Pane pane)
    {
        return new ReduxFX(initialState, updater, view, pane);
    }


    private final MainLoop mainLoop;


    private  <STATE> ReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        mainLoop = new MainLoop(initialState, updater, view, primaryStage);
    }

    protected  <STATE> ReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Group group)
    {
        mainLoop = new MainLoop(initialState, updater, view, group);
    }

    protected  <STATE> ReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Pane pane) {
        mainLoop = new MainLoop(initialState, updater, view, pane);
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
