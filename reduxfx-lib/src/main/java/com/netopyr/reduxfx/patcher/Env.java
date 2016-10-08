package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.scene.control.ToggleGroup;
import javaslang.control.Option;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private Map<String, ToggleGroup> toggleGroups = new HashMap<>();

    ToggleGroup getToggleGroup(String id) {
        return toggleGroups.computeIfAbsent(id, key -> new ToggleGroup());
    }

    public void cleanup(VNode<?> root) {
        final Env newEnv = new Env();
        doCleanup(newEnv, root);
        toggleGroups = newEnv.toggleGroups;
    }

    private void doCleanup(Env newEnv, VNode<?> node) {
        final Option<? extends VProperty<?, ?>> toggleGroupProperty = node.getProperties().get(VPropertyType.TOGGLE_GROUP);
        if (toggleGroupProperty.isDefined()) {
            final String id = (String) toggleGroupProperty.get().getValue();
            newEnv.toggleGroups.put(id, toggleGroups.get(id));
        }

        for (final VNode<?> child : node.getChildren()) {
            doCleanup(newEnv, child);
        }
    }
}
