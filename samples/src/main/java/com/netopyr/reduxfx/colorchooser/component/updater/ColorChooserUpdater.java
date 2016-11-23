package com.netopyr.reduxfx.colorchooser.component.updater;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction;
import com.netopyr.reduxfx.colorchooser.component.actions.UpdateBlue;
import com.netopyr.reduxfx.colorchooser.component.actions.UpdateGreen;
import com.netopyr.reduxfx.colorchooser.component.actions.UpdateRed;
import com.netopyr.reduxfx.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import com.netopyr.reduxfx.updater.Update;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class ColorChooserUpdater {

    private static final Logger LOG = LoggerFactory.getLogger(ColorChooserUpdater.class);

    private ColorChooserUpdater() {
    }

    public static Update<ColorChooserModel> update(ColorChooserModel state, ColorChooserAction action) {
        if (action == null) {
            return Update.of(state);
        }

        final Update<ColorChooserModel> update =
                Match(action).of(

                        Case(instanceOf(UpdateRed.class),
                                updateRed -> {
                                    final Color color = Color.color(updateRed.getValue(), state.getGreen(), state.getBlue());
                                    return Update.of(
                                            state.withRed(updateRed.getValue()),
                                            new ObjectChangedCommand<>("color", color)
                                    );
                                }),

                        Case(instanceOf(UpdateGreen.class),
                                updateGreen -> {
                                    final Color color = Color.color(state.getRed(), updateGreen.getValue(), state.getBlue());
                                    return Update.of(
                                            state.withGreen(updateGreen.getValue()),
                                            new ObjectChangedCommand<>("color", color)
                                    );
                                }),

                        Case(instanceOf(UpdateBlue.class),
                                updateBlue -> {
                                    final Color color = Color.color(state.getRed(), state.getGreen(), updateBlue.getValue());
                                    return Update.of(
                                            state.withBlue(updateBlue.getValue()),
                                            new ObjectChangedCommand<>("color", color)
                                    );
                                }),

                        Case($(), Update.of(state))

                );

        LOG.trace("\nColorChooserUpdater Old State:\n{}\nColorChooserUpdater Action:\n{}\nColorChooserUpdater Update:\n{}\n",
                state, action, update);
        return update;
    }
}
