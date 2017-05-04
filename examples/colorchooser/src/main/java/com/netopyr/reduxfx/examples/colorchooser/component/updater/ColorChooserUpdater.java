package com.netopyr.reduxfx.examples.colorchooser.component.updater;

import com.netopyr.reduxfx.component.ComponentBase;
import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent;
import com.netopyr.reduxfx.examples.colorchooser.component.actions.ColorChangedAction;
import com.netopyr.reduxfx.examples.colorchooser.component.actions.UpdateBlueAction;
import com.netopyr.reduxfx.examples.colorchooser.component.actions.UpdateGreenAction;
import com.netopyr.reduxfx.examples.colorchooser.component.actions.UpdateRedAction;
import com.netopyr.reduxfx.examples.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.updater.Update;
import javafx.scene.paint.Color;

import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * The {@code ColorChooserUpdater} is the heart of the
 * {@link ColorChooserComponent}. This is where the main logic resides.
 * <p>
 * A {@code ColorChooserUpdater} consists of a single function ({@link #update(ColorChooserModel, Object)}
 * in this class), which takes the current state (an instance of {@link ColorChooserModel}) and an
 * action and calculates the new state from that.
 * <p>
 * Optionally it can also create an arbitrary number of commands, which are processed by a
 * {@link com.netopyr.reduxfx.driver.Driver}. Usually such a {@code Driver} has to be registered with the
 * {@link com.netopyr.reduxfx.store.ReduxFXStore}-instance explicitly, but {@code ColorChooserComponent} uses a
 * {@link ComponentBase} which registers a driver for component-specific commands and actions automatically.
 * See {@link ColorChooserComponent} for more details.
 * <p>
 * Please note that {@code ColorChooserUpdater} has no internal state. Everything that is needed for {@code update}
 * is passed in the parameters.
 */
public class ColorChooserUpdater {

    private ColorChooserUpdater() {
    }

    /**
     * The method {@code update} is the central piece of the
     * {@link ColorChooserComponent}. The whole logic is implemented here.
     * <p>
     * This method takes the current state (an instance of {@link ColorChooserModel}) and an action
     * and calculates the new state from that and optionally all of the commands which are needed to speak to the
     * outside world.
     * <p>
     * Please note that {@code update} does not require any internal state. Everything that is needed, is passed in the
     * parameters. Also {@code update} has no side effects. It is a pure function.
     * <p>
     * Also please note, that {@code ColorChooserModel} is an immutable data structure. This means that {@code update}
     * does not modify the old state, but instead creates a new instance of {@code ColorChooserModel}, if anything changes.
     *
     * @param state  the current state
     * @param action the {@code ColorChooserAction} that needs to be performed
     * @return the new state
     * @throws NullPointerException if state or action are {@code null}
     */
    public static Update<ColorChooserModel> update(ColorChooserModel state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        // This is part of Javaslang's pattern-matching API. It works similar to the regular switch-case
        // in Java, except that it is much more flexible and that it can be used as an expression.
        // We check which kind of action was received and in that case-branch we specify the value that
        // will be assigned to newState.
        return Match(action).of(

                // If the action is a UpdateRedAction, we return a new ColorChooserModel with the
                // property red set to the new value.
                // We also return an ObjectChangedCommand, which makes sure the color-property of this
                // component is updated.
                Case(instanceOf(UpdateRedAction.class),
                        updateRedAction -> {
                            final Color color = Color.rgb(updateRedAction.getValue(), state.getGreen(), state.getBlue());
                            return Update.of(
                                    state.withRed(updateRedAction.getValue()),
                                    new ObjectChangedCommand<>("color", color)
                            );
                        }),

                // If the action is a UpdateGreenAction, we return a new ColorChooserModel with the
                // property green set to the new value.
                // We also return an ObjectChangedCommand, which makes sure the color-property of this
                // component is updated.
                Case(instanceOf(UpdateGreenAction.class),
                        updateGreenAction -> {
                            final Color color = Color.rgb(state.getRed(), updateGreenAction.getValue(), state.getBlue());
                            return Update.of(
                                    state.withGreen(updateGreenAction.getValue()),
                                    new ObjectChangedCommand<>("color", color)
                            );
                        }),

                // If the action is a UpdateBlueAction, we return a new ColorChooserModel with the
                // property blue set to the new value.
                // We also return an ObjectChangedCommand, which makes sure the color-property of this
                // component is updated.
                Case(instanceOf(UpdateBlueAction.class),
                        updateBlueAction -> {
                            final Color color = Color.rgb(state.getRed(), state.getGreen(), updateBlueAction.getValue());
                            return Update.of(
                                    state.withBlue(updateBlueAction.getValue()),
                                    new ObjectChangedCommand<>("color", color)
                            );
                        }),

                // A ColorChangedAction is received, when the color-property of this component was changed
                // from the outside. We return a new ColorChooserModel with all color values updated accordingly.
                Case(instanceOf(ColorChangedAction.class),
                        colorChangedAction -> {
                            final Color newColor = colorChangedAction.getNewColor();
                            return Update.of(
                                    state.withRed((int) (255 * newColor.getRed()))
                                            .withGreen((int) (255 * newColor.getGreen()))
                                            .withBlue((int) (255 * newColor.getBlue()))
                            );
                        }),

                // This is the default branch of this switch-case. If an unknown Action was passed to the
                // updater, we simple return the old state. This is a convention, that is not needed right
                // now, but will help once you start to decompose your updater.
                Case($(), Update.of(state))

        );
    }
}
