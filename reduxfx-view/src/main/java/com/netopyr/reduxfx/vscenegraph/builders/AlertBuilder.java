package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.control.Alert;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;

@SuppressWarnings("unused")
public class AlertBuilder<B extends AlertBuilder<B>> extends DialogBuilder<B> {

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
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new AlertBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
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


    public B alertType(Alert.AlertType value) {
        return property(ALERT_TYPE, value);
    }
}
