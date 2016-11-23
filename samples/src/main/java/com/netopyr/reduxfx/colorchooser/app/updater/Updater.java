package com.netopyr.reduxfx.colorchooser.app.updater;

import com.netopyr.reduxfx.colorchooser.app.actions.Action;
import com.netopyr.reduxfx.colorchooser.app.actions.UpdateColor;
import com.netopyr.reduxfx.colorchooser.app.state.AppModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    private Updater() {
    }

    public static AppModel update(AppModel oldState, Action action) {
        if (action == null) {
            return oldState;
        }

        final AppModel newState =
                Match(action).of(

                        Case(instanceOf(UpdateColor.class),
                                updateColor -> oldState.withColor(updateColor.getValue())
                        ),

                        Case($(), oldState)
                );

        LOG.trace("\nUpdater Old State:\n{}\nUpdater Action:\n{}\nUpdater New State:\n{}\n\n",
                oldState, action, newState);
        return newState;
    }
}
