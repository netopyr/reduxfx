package com.netopyr.reduxfx.examples.colorchooser.component;

import com.netopyr.reduxfx.examples.colorchooser.app.state.AppModel;
import com.netopyr.reduxfx.examples.colorchooser.component.actions.ColorChooserActions;
import com.netopyr.reduxfx.examples.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import com.netopyr.reduxfx.examples.colorchooser.component.view.ColorChooserView;
import com.netopyr.reduxfx.component.ComponentBase;
import com.netopyr.reduxfx.examples.colorchooser.app.view.MainView;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import com.netopyr.reduxfx.vscenegraph.builders.Factory;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javaslang.collection.HashMap;

/**
 * The class {@code ColorChooserComponent} is the main class of this component.
 *
 * It extends {@code VBox} and adds one additional JavaFX property {@link #colorProperty()}. As usual in ReduxFX,
 * we do not want to modify state directly and this also applies to JavaFX properties of the public interface.
 * Instead we use a {@link com.netopyr.reduxfx.driver.Driver} for that. We can send commands to the driver to update
 * the value of the JavaFX property. Commands are created in the {@link ColorChooserUpdater} together with the state
 * changes. If the value of a JavaFX property changes, our updater receives an action, which can be specified here.
 */
public class ColorChooserComponent extends VBox {

    /**
     * This is a helper function, to make the code in {@link MainView#view(AppModel)} more readable.
     *
     * @param <CLASS> Parameter required to implement inheritable builders
     * @return the VirtualScenegraph-node of a {@code ColorChooserComponent}, which is also a {@link ColorChooserBuilder}
     */
    public static <CLASS extends ColorChooserBuilder<CLASS>> ColorChooserBuilder<CLASS> ColorChooser() {
        return Factory.node(ColorChooserComponent.class, () -> new ColorChooserBuilder<CLASS>(ColorChooserComponent.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }


    /**
     * The default constructor of {@code ColorChooserComponent}.
     *
     * All components that should be used with ReduxFX must have a default constructor.
     */
    public ColorChooserComponent() {

        // Setup the initial state
        final ColorChooserModel initialData = new ColorChooserModel();

        // A ComponentBase is the central piece of every component implemented with ReduxFX. It creates a separate
        // event-cycle for the component and acts as the factory for all JavaFX properties.
        final ComponentBase<ColorChooserModel> componentBase = new ComponentBase<>(this, initialData, ColorChooserUpdater::update, new LoggingMiddleware<>());
        final ReduxFXView<ColorChooserModel> view = ReduxFXView.create(ColorChooserView::view, this);
        view.connect(componentBase.createActionSubscriber(), componentBase.getStatePublisher());

        // This sets up the JavaFX property of this component. The value can be changed by dispatching
        // ObjectChangedCommands in the ColorChooserUpdater (alongside any required state changes). The VChangeListener
        // passed as the third parameter is triggered every time the value of the property changes from outside.
        // The returned action will be passed to the ColorChooserUpdater
        color = componentBase.createObjectProperty(this, "color", (oldValue, newValue) -> ColorChooserActions.colorChanged(newValue));
    }


    /**
     * The {@code color}-property contains the {@code Color} that is currently selected in the {@code ColorChooserComponent}
     */
    private final ObjectProperty<Color> color;

    /**
     * The getter of the {@code color}-property
     * @return the current value
     */
    public final Color getColor() {
        return color.get();
    }

    /**
     * The setter of the {@code color}-property
     * @param value the new value
     */
    public final void setColor(Color value) {
        color.set(value);
    }

    /**
     * The getter of the JavaFX {@code Property} {@code color}
     * @return the {@code ObjectProperty}
     */
    public final ObjectProperty<Color> colorProperty() {
        return color;
    }
}
