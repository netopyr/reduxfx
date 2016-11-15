package com.netopyr.reduxfx.colorchooser.app.state;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final Color color;

    public AppModel() {
        this(Color.BLACK);
    }
    private AppModel(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public AppModel withColor(Color color) {
        return new AppModel(color);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("color", color)
                .toString();
    }
}
