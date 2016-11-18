package com.netopyr.reduxfx.colorchooser.component.updater;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChanged;
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

public class ColorChooserUpdater {

    private static final Logger LOG = LoggerFactory.getLogger(ColorChooserUpdater.class);

    private ColorChooserUpdater() {
    }

    public static Update<ColorChooserModel> update(ColorChooserModel state, ColorChooserAction action) {
        if (action == null) {
            return Update.of(state);
        }

        final Update<ColorChooserModel> update;
        switch (action.getType()) {

            case UPDATE_RED: {
                final double red = ((UpdateRed) action).getValue();
                final Color color = Color.color(red, state.getGreen(), state.getBlue());
                update = Update.of(
                        state.withRed(red),
                        new ObjectChangedCommand<>("color", color)
                );
            }
            break;

            case UPDATE_GREEN: {
                final double green = ((UpdateGreen) action).getValue();
                final Color color = Color.color(state.getRed(), green, state.getBlue());
                update = Update.of(
                        state.withGreen(green),
                        new ObjectChangedCommand<>("color", color)
                );
            }
            break;

            case UPDATE_BLUE: {
                final double blue = ((UpdateBlue) action).getValue();
                final Color color = Color.color(state.getRed(), state.getGreen(), blue);
                update = Update.of(
                        state.withBlue(blue),
                        new ObjectChangedCommand<>("color", color)
                );
            }
            break;

            case COLOR_CHANGED: {
                final Color color = ((ColorChanged) action).getValue();
                update = Update.of(
                        state.withRed(color.getRed())
                                .withGreen(color.getGreen())
                                .withBlue(color.getBlue()),
                        new ObjectChangedCommand<>("color", color)
                );
            }
            break;

            default:
                update = Update.of(state);
                break;
        }

        LOG.trace("\nColorChooserUpdater Old State:\n{}\nColorChooserUpdater Action:\n{}\nColorChooserUpdater Update:\n{}\n",
                state, action, update);
        return update;
    }
}
