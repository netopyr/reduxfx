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

    public static <STATE> SimpleReduxFX startStages(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.createStages(initialState, wrap(updater), view, primaryStage);
        return new SimpleReduxFX(mainLoop);
    }

    public static <STATE> SimpleReduxFX startStage(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.createStage(initialState, wrap(updater), view, primaryStage);
        return new SimpleReduxFX(mainLoop);
    }

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Stage primaryStage
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, wrap(updater), view, primaryStage);
        return new SimpleReduxFX(mainLoop);
    }

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Group group
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, wrap(updater), view, group);
        return new SimpleReduxFX(mainLoop);
    }

    public static <STATE> SimpleReduxFX start(
            STATE initialState,
            BiFunction<STATE, Object, STATE> updater,
            Function<STATE, VNode> view,
            Pane pane
    ) {
        final MainLoop mainLoop = MainLoop.create(initialState, wrap(updater), view, pane);
        return new SimpleReduxFX(mainLoop);
    }


    private final MainLoop mainLoop;


    private  SimpleReduxFX(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
    }


    public void stop() {
        mainLoop.stop();
    }


    private static <STATE, ACTION> BiFunction<STATE, ACTION, Update<STATE>> wrap(BiFunction<STATE, ACTION, STATE> updater) {
        return (STATE state, ACTION action) -> Update.of(updater.apply(state, action));
    }

}
