package com.netopyr.reduxfx.examples.fxml.reduxjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewLoader {

    public static class Tuple <T extends View> {
        private Parent parent;
        private T controller;

        Tuple(Parent parent, T controller) {
            this.parent = parent;
            this.controller = controller;
        }

        public Parent getParent() {
            return parent;
        }

        public T getController() {
            return controller;
        }
    }


    private static Callback<Class<?>, Object> injector;

    private static ResourceBundle resourceBundle;


    /**
     * Define a function that acts as dependency injection mechanism.
     * This is similar to the "ControllerFactory" that can be defined for JavaFX {@link FXMLLoader} with {@link FXMLLoader#setControllerFactory(Callback)}.
     *
     * @param injector the injector function.
     */
    public static void setDependencyInjector(Callback<Class<?>, Object> injector){
        ViewLoader.injector = injector;
    }

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        ViewLoader.resourceBundle = resourceBundle;
    }

    public static <T extends Parent> T load(Class<? extends View> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T extends View> Tuple<T> loadTuple(Class<T> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return new Tuple<>(fxmlLoader.load(), fxmlLoader.getController());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static FXMLLoader createFxmlLoader(Class<? extends View> viewClass) {
        final String fxmlPathAsString = createFxmlPath(viewClass);
        final URL fxmlPath = ViewLoader.class.getResource(fxmlPathAsString);

        if(fxmlPath == null) {
            throw new IllegalArgumentException("Can't load View " + viewClass + ". FXML File not found under:" + fxmlPathAsString);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
        fxmlLoader.setLocation(fxmlPath);
        fxmlLoader.setResources(resourceBundle);

        if(injector != null) {
            fxmlLoader.setControllerFactory(injector);
        }
        return fxmlLoader;
    }

    /**
     * Taken from mvvmFX
     */
    private static String createFxmlPath(Class<?> viewType) {
        final StringBuilder pathBuilder = new StringBuilder();

        pathBuilder.append("/");

        if (viewType.getPackage() != null) {
            pathBuilder.append(viewType.getPackage().getName().replaceAll("\\.", "/"));
            pathBuilder.append("/");
        }

        pathBuilder.append(viewType.getSimpleName());
        pathBuilder.append(".fxml");

        return pathBuilder.toString();
    }
}