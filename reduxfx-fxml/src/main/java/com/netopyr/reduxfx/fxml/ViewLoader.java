package com.netopyr.reduxfx.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * {@link ViewLoader} is a generic JavaFX FXML util that can load
 * FXML hierarchies based on a root Controller class.
 * <p>
 * The standard JavaFX {@link FXMLLoader} uses {@link java.net.URL} to define
 * the FXML file that should be loaded. However, this results in Code that
 * has to handle the path of the FXML file with Strings.
 * This can be error-prone and annoying.
 * <p>
 * In contrast to the {@link FXMLLoader} the {@link ViewLoader} don't uses
 * Strings to define the FXML file that should be loaded.
 * Instead it is based on a naming convention and the usage of {@link Class} references.
 * <p>
 * To load an FXML file you use a class reference of the Controller class of this FXML file
 * as argument to the {@link ViewLoader#load(Class)}.
 * In order to get this to work the FXML file has to be placed in the same package as the
 * Controller class and the Controller class has to have the same name as the FXML file (excluding file extension).
 * <p>
 * For example imagine a controller class <code>com.netopyr.reduxfx.fxml.MyView</code>.
 * The Fxml file has to be <code>/com/netopyr/reduxfx/fxml/MyView.fxml</code>.
 *
 */
public class ViewLoader {

    private ViewLoader() {}

    public static class Tuple <T> {
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

	/**
	 * Set the resource bundle that is used by the {@link ViewLoader}.
	 * @param resourceBundle the resource bundle to use.
	 */
	public static void setResourceBundle(ResourceBundle resourceBundle) {
        ViewLoader.resourceBundle = resourceBundle;
    }

	/**
	 * Load the FXML file for the given controller class reference.
	 *
	 * @param viewClass a reference to the controller class. The class name and package has to match the FXML filename and path.
	 * @param <T> The type of the root element in the FXML file or any parent class.
	 * @return the loading JavaFX node.
	 */
    public static <T extends Parent> T load(Class<?> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


	/**
	 * This method is similar to {@link #load(Class)} with the exception that
	 * this method returns a tuple that not only contains the loaded root element
	 * but also the controller instance of the root view.
	 *
	 * @param viewClass a reference to the controller class. The class name and package has to match the FXML filename and path.
	 * @param <T> The type of the root element in the FXML file or any parent class.
	 * @return a tuple of the controller class and the root node.
	 */
    public static <T> Tuple<T> loadTuple(Class<T> viewClass) {
        final FXMLLoader fxmlLoader = createFxmlLoader(viewClass);

        try {
            return new Tuple<>(fxmlLoader.load(), fxmlLoader.getController());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static FXMLLoader createFxmlLoader(Class<?> viewClass) {
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