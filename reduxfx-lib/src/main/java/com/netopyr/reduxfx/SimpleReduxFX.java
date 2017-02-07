package com.netopyr.reduxfx;

import com.netopyr.reduxfx.mainloop.MainLoop;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class SimpleReduxFX {

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        return new SimpleReduxFX(initialState, updater, view, primaryStage);
    }

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Group group)
    {
        return new SimpleReduxFX(initialState, updater, view, group);
    }

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Pane pane)
    {
        return new SimpleReduxFX(initialState, updater, view, pane);
    }


    private final MainLoop mainLoop;


    private  <STATE> SimpleReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        mainLoop = new MainLoop(initialState, wrap(updater), view, primaryStage);
    }

    private <STATE> SimpleReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Group group)
    {
        mainLoop = new MainLoop(initialState, wrap(updater), view, group);
    }

    private <STATE> SimpleReduxFX(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Pane pane) {
        mainLoop = new MainLoop(initialState, wrap(updater), view, pane);
    }


    public void stop() {
        mainLoop.stop();
    }


    private static <STATE, ACTION> BiFunction<STATE, ACTION, Update<STATE>> wrap(BiFunction<STATE, ACTION, STATE> updater) {
        return (STATE state, ACTION action) -> Update.of(updater.apply(state, action));
    }

}
