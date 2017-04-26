package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.DialogShowingAccessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.VirtualPropertyAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.stage.Modality;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;

@SuppressWarnings("unused")
public class DialogBuilder<BUILDER extends DialogBuilder<BUILDER>> extends Builder<BUILDER> {

    public static final String CONTENT_TEXT = "contentText";
    public static final String HEADER_TEXT = "headerText";
    public static final String MODAL = "modal";
    public static final String SHOWING = "showing";


    public DialogBuilder(Class<?> nodeClass,
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
        return (BUILDER) new DialogBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER contentText(String value) {
        return property(CONTENT_TEXT, value);
    }


    public BUILDER headerText(String value) {
        return property(HEADER_TEXT, value);
    }

    public BUILDER modal(Modality value) {
        Accessors.registerAccessor(getNodeClass(), MODAL, VirtualPropertyAccessor::new);
        return property(MODAL, value);
    }

    public BUILDER showing(boolean value, VChangeListener<Boolean> changeListener) {
        Accessors.registerAccessor(getNodeClass(), SHOWING, () -> new DialogShowingAccessor(this::produce));
        return property(value? VProperty.Phase.SHOW_STAGE : VProperty.Phase.HIDE_STAGE, SHOWING, value, changeListener);
    }
    public BUILDER showing(boolean value) {
        Accessors.registerAccessor(getNodeClass(), SHOWING, () -> new DialogShowingAccessor(this::produce));
        return property(value? VProperty.Phase.SHOW_STAGE : VProperty.Phase.HIDE_STAGE, SHOWING, value);
    }

}
