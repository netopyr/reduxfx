package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VScenegraphFactory;
import com.netopyr.reduxfx.vscenegraph.impl.differ.Differ;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import java.util.function.Consumer;
import java.util.function.Function;


public class TreeViewCellFactoryAccessor implements Accessor {

	@SuppressWarnings("unchecked")
	@Override
	public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
		if (!(node instanceof TreeView)) {
			throw new IllegalStateException("Trying to set cellFactory of node " + node);
		}

		final Object value = vProperty.isValueDefined() ? vProperty.getValue() : null;
		if (!(value == null || value instanceof Function)) {
			throw new IllegalStateException(
				String.format("Trying to set a cellFactory on node %s, which is not a function but a %s",
					node, value));
		}

		((TreeView) node).setCellFactory(
			new TreeViewCellFactory(dispatcher, value == null ? Function.identity() : (Function) value));

	}

	private static class TreeViewCellFactory implements Callback<TreeView<Object>, TreeCell<Object>> {

		private final Consumer<Object> dispatcher;
		private final Function<Object, Object> mapping;

		private TreeViewCellFactory(Consumer<Object> dispatcher,
			Function<Object, Object> mapping) {
			this.dispatcher = dispatcher;
			this.mapping = mapping;
		}

		@Override
		public TreeCell<Object> call(TreeView<Object> param) {
			return new ReduxFXTreeCell();
		}

		private class ReduxFXTreeCell extends TreeCell<Object> {
			@Override
			protected void updateItem(Object data, boolean empty) {
				super.updateItem(data, empty);

				if (empty || data == null) {
					setText(null);
					setGraphic(null);
				} else {
					final Object item = mapping.apply(data);

					if (item instanceof VNode) {
						final VNode newVNode = VScenegraphFactory
							.customNode(TreeCell.class).child("graphic", (VNode) item);
						final Option<VNode> oldVNode = Option.of(getGraphic())
							.flatMap(node -> Option.of((VNode) node.getUserData()));

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
