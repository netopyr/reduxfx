package com.netopyr.reduxfx.colorchooser.app.actions;

import javafx.scene.paint.Color;

public final class Actions {

    private Actions() {}

    public static Action updateColor(Color value) {
        return new UpdateColor(value);
    }
}
