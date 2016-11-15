package com.netopyr.reduxfx.colorchooser.app.updater;

import com.netopyr.reduxfx.colorchooser.app.actions.Action;
import com.netopyr.reduxfx.colorchooser.app.actions.UpdateColor;
import com.netopyr.reduxfx.colorchooser.app.state.AppModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    private Updater() {}

    public static AppModel update(AppModel oldState, Action action) {
        if (action == null) {
            return oldState;
        }

        LOG.trace("\n\n\n\nUpdater Old State: " + oldState + "\n\nUpdater Action: " + action);

        final AppModel newState;
        switch (action.getType()) {

            case UPDATE_COLOR:
                newState = oldState.withColor(((UpdateColor) action).getValue());
                break;

            default:
                newState = oldState;
                break;
        }

        LOG.trace("\n\nUpdater New State: " + newState);
        return newState;
    }
}
