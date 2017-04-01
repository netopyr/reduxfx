package com.netopyr.reduxfx.impl.patcher;

import com.netopyr.reduxfx.impl.differ.Differ;
import com.netopyr.reduxfx.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VScenegraphFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javaslang.collection.Vector;
import javaslang.control.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReduxFXListView extends ListView<Object> {

    private final Patcher patcher;

    private ObjectProperty<ObservableList<Object>> data = new SimpleObjectProperty<>(this, "data", FXCollections.observableArrayList());

    public final ObservableList<Object> getData() {
        return data.get();
    }

    public final ObjectProperty<ObservableList<Object>> dataProperty() {
        return data;
    }


    private ObjectProperty<Function<Object, Object>> mapping;

    public final void setMapping(Function<Object, Object> value) {
        mappingProperty().set(value);
    }

    public final Function<Object, Object> getMapping() {
        return mapping == null ? null : mapping.get();
    }

    public final ObjectProperty<Function<Object, Object>> mappingProperty() {
        if (mapping == null) {
            mapping = new SimpleObjectProperty<>(this, "mapping");
        }
        return mapping;
    }


    public ReduxFXListView(){
        this.patcher = Patcher.getInstance();
        setCellFactory(listView -> new ReduxFXListCell());
        setItems(new ReduxFXTransformationList(null));
        mappingProperty().addListener(((observable, oldValue, newValue) -> {
            setItems(new ReduxFXTransformationList(newValue));
        }));
    }


    private class ReduxFXListCell extends ListCell<Object> {
        @SuppressWarnings("unchecked")
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (item instanceof VNode) {
                    final VNode newVNode = VScenegraphFactory.customNode(ListCell.class).child("graphic", (VNode) item);
                    final Option<VNode> oldVNode = Option.of(getGraphic()).flatMap(node -> Option.of((VNode) node.getUserData()));
                    final Vector<Patch> patches = Differ.diff(oldVNode, Option.of(newVNode));
                    ReduxFXListView.this.patcher.patch(this, oldVNode, patches);
                    Option.of(getGraphic()).peek(node -> node.setUserData(newVNode));
                } else {
                    this.setText(String.valueOf(item));
                    this.setGraphic(null);
                }
            }
        }
    }

    private class ReduxFXTransformationList extends TransformationList<Object, Object> {

        private final Function<Object, Object> mapping;

        private ReduxFXTransformationList(Function<Object, Object> mapping) {
            super(getData());
            this.mapping = mapping;
        }

        @Override
        protected void sourceChanged(ListChangeListener.Change<?> c) {
            beginChange();
            while (c.next()) {
                final int from = c.getFrom();
                final int to = c.getTo();
                if (c.wasPermutated()) {
                    final int[] permutation = new int[to - from];
                    for (int i = from; i < to; i++) {
                        permutation[i - from] = c.getPermutation(i);
                    }
                    nextPermutation(c.getFrom(), c.getTo(), permutation);
                } else if (c.wasUpdated()) {
                    for (int i = from; i < to; i++) {
                        nextUpdate(i);
                    }
                } else {
                    final ArrayList removed = new ArrayList();
                    for (Object removedObject : c.getRemoved()) {
                        removed.add(getMapping().apply(removedObject));
                    }
                    nextReplace(from, to, removed);
                }
            }
            endChange();
        }

        @Override
        public int getSourceIndex(int index) {
            return index;
        }

        @Override
        public Object get(int index) {
            final Object sourceObject = getSource().get(index);
            return mapping == null ? sourceObject : mapping.apply(sourceObject);
        }

        @Override
        public int size() {
            final List list = ReduxFXListView.this.getData();
            return list == null ? 0 : list.size();
        }
    }
}
