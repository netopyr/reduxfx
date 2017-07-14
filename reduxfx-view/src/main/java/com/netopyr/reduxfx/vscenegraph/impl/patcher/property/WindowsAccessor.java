package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.DialogBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.WindowBuilder;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeBuilder;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.Window;
import io.vavr.collection.Array;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.function.Consumer;

public class WindowsAccessor implements NodeListAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(WindowsAccessor.class);

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object parent, String name, Array<VNode> vNodes) {
        if (!(parent instanceof Window)) {
            LOG.warn("Tried to set windows-list to non-window {}", parent);
            return;
        }
        final Window window = (Window) parent;
        final ArrayList<Object> windowList = (ArrayList<Object>) window.getProperties().computeIfAbsent(name, key -> new ArrayList<>());
        vNodes.forEach(vNode -> {
                    if (!(vNode instanceof WindowBuilder || vNode instanceof DialogBuilder)) {
                        LOG.warn("Tried to add non-Window {} to windows-list of {}", vNode, parent);
                        return;
                    }
                    final Option<Object> nodeOption = NodeBuilder.create(vNode);
                    if (nodeOption.isEmpty()) {
                        LOG.warn("Unable to create window for {}", vNode);
                        return;
                    }

                    final Object node = nodeOption.get();
                    if (node instanceof Stage) {
                        ((Stage) node).initOwner(window);
                    } else if (node instanceof Dialog) {
                        ((Dialog) node).initOwner(window);
                    } else {
                        LOG.warn("Unable to handle windows of type " + node.getClass());
                        return;
                    }
                    NodeBuilder.init(dispatcher, node, vNode);
                    windowList.add(node);
                }
        );
    }
}
