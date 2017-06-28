package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VScenegraphFactory;
import com.netopyr.reduxfx.vscenegraph.impl.differ.Differ;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListViewCellFactoryAccessor implements Accessor {

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (! (node instanceof ListView)) {
            throw new IllegalStateException("Trying to set cellFactory of node " + node);
        }
        final Object value = vProperty.isValueDefined()? vProperty.getValue() : null;
        if (! (value == null || value instanceof Function)) {
            throw new IllegalStateException(String.format("Trying to set a cellFactory on node %s, which is not a function but a %s",
                    node, value));
        }

        ((ListView)node).setCellFactory(new ListViewCellFactory(dispatcher, value == null? Function.identity() : (Function) value));
    }


    private static class ListViewCellFactory implements Callback<ListView<Object>, ListCell<Object>> {

        private final Consumer<Object> dispatcher;
        private final Function<Object, Object> mapping;

        private ListViewCellFactory(Consumer<Object> dispatcher, Function<Object, Object> mapping) {
            this.dispatcher = Objects.requireNonNull(dispatcher, "Dispatcher must not be null");
            this.mapping = Objects.requireNonNull(mapping, "Mapping must not be null");
        }

        @Override
        public ListCell<Object> call(ListView<Object> param) {
            return new ReduxFXListCell();
        }


        private class ReduxFXListCell extends ListCell<Object> {

            @SuppressWarnings("unchecked")
            @Override
            protected void updateItem(Object data, boolean empty) {
                super.updateItem(data, empty);

                if (empty || data == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    final Object item = mapping.apply(data);
                    if (item instanceof VNode) {
                        final VNode newVNode = VScenegraphFactory.customNode(ListCell.class).child("graphic", (VNode) item);
                        final Option<VNode> oldVNode = Option.of(getGraphic()).flatMap(node -> Option.of((VNode) node.getUserData()));
                        final Map<Phase, Vector<Patch>> patches = Differ.diff(oldVNode, Option.of(newVNode));
                        Patcher.patch(dispatcher, this, oldVNode, patches);
                        Option.of(getGraphic()).forEach(node -> node.setUserData(newVNode));
                    } else {
                        this.setText(String.valueOf(item));
                        this.setGraphic(null);
                    }
                }
            }
        }
    }


}
