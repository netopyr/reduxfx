package com.netopyr.reduxfx.patcher.patches;

import com.netopyr.reduxfx.vscenegraph.VPropertyType;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttributesPatch extends Patch {

    private final Map<VPropertyType, Object> properties;
    private final Map<String, EventHandler<?>> eventHandlers;
    private final Map<String, ChangeListener<?>> changeListeners;
    private final Map<String, InvalidationListener> invalidationListeners;

    public AttributesPatch(
            int index,
            Map<VPropertyType, Object> properties,
            Map<String, EventHandler<?>> eventHandlers,
            Map<String, ChangeListener<?>> changeListeners,
            Map<String, InvalidationListener> invalidationListenerMaps
            ) {
        super(index);
        this.properties = properties;
        this.eventHandlers = eventHandlers;
        this.changeListeners = changeListeners;
        this.invalidationListeners = invalidationListenerMaps;
    }

    @Override
    public Type getType() {
        return Type.ATTRIBUTES;
    }

    public Map<VPropertyType, Object> getProperties() {
        return properties;
    }

    public Map<String, EventHandler<?>> getEventHandlers() {
        return eventHandlers;
    }

    public Map<String, ChangeListener<?>> getChangeListeners() {
        return changeListeners;
    }

    public Map<String, InvalidationListener> getInvalidationListeners() {
        return invalidationListeners;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .append("changeListeners", changeListeners)
                .append("invalidationListeners", invalidationListeners)
                .toString();
    }
}
