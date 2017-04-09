package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.control.Alert;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;

@SuppressWarnings("unused")
public class AlertBuilder<BUILDER extends AlertBuilder<BUILDER>> extends DialogBuilder<BUILDER> {

    private static final String ALERT_TYPE = "alertType";


    public AlertBuilder(Class<?> nodeClass,
                        Map<String, Array<VNode>> childrenMap,
                        Map<String, Option<VNode>> singleChildMap,
                        Map<String, VProperty> properties,
                        Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new AlertBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }

    @Override
    public Option<Object> produce() {
        if (Alert.class.equals(getNodeClass())) {
            final Option<VProperty> alertType = getProperties().get(ALERT_TYPE);
            if (alertType.isDefined() && alertType.get().isValueDefined() && alertType.get().getValue() instanceof Alert.AlertType) {
                return Option.of(new Alert((Alert.AlertType) alertType.get().getValue()));
            }
            return Option.of(new Alert(Alert.AlertType.NONE));
        } else {
            return super.produce();
        }
    }


    public BUILDER alertType(Alert.AlertType value) {
        return property(ALERT_TYPE, value);
    }
}
