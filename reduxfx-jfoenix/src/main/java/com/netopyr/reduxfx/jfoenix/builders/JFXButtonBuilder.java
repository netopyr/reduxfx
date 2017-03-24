package com.netopyr.reduxfx.jfoenix.builders;

import com.jfoenix.controls.JFXButton;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.ButtonBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.paint.Paint;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("unused")
public class JFXButtonBuilder<BUILDER extends JFXButtonBuilder<BUILDER>> extends ButtonBuilder<BUILDER> {

    private static final String BUTTON_TYPE = "buttonType";
    private static final String RIPPLER_FILL = "ripplerFill";

    public JFXButtonBuilder(Class<?> nodeClass,
                            Array<VNode> children,
                            Map<String, VProperty> namedChildren,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new JFXButtonBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER buttonType(JFXButton.ButtonType value) {
        return property(BUTTON_TYPE, value);
    }

    public BUILDER ripplerFill(Paint value) {
        return property(RIPPLER_FILL, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
