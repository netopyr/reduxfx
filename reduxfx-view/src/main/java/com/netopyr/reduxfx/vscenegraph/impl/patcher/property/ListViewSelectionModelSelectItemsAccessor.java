package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.ListView;

import java.util.function.Consumer;

public class ListViewSelectionModelSelectItemsAccessor extends ListenerHandlingAccessor {

	@SuppressWarnings("unchecked")
	@Override
	public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
		if(! (node instanceof ListView)) {
			throw new IllegalStateException("Trying to set selectionModel of node " + node);
		}

		final ListView listView = (ListView) node;

		final ReadOnlyObjectProperty selectedItemProperty = listView.getSelectionModel().selectedItemProperty();

		clearListeners(node, selectedItemProperty);

		final Object value = vProperty.isValueDefined()? vProperty.getValue() : null;
		listView.getSelectionModel().select(value);

		if(vProperty.getChangeListener().isDefined()) {
			setChangeListener(dispatcher, node, selectedItemProperty, vProperty.getChangeListener().get());
		}

		if(vProperty.getInvalidationListener().isDefined()) {
			setInvalidationListener(dispatcher, node, selectedItemProperty, vProperty.getInvalidationListener().get());
		}
	}

	@Override
	protected Object fxToV(Object value) {
		return value;
	}

	@Override
	protected Object vToFX(Object value) {
		return value;
	}
}