package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.control.TabPane;

import java.util.function.Consumer;

public class TabPaneSelectionModelAccessor extends ListenerHandlingAccessor {

	@SuppressWarnings("unchecked")
	@Override
	public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
		if(! (node instanceof TabPane)) {
			throw new IllegalStateException("Trying to set selectionModel of node " + node);
		}

		final TabPane tabPane = (TabPane) node;

		final ReadOnlyIntegerProperty selectedIndexProperty = tabPane.getSelectionModel().selectedIndexProperty();

		clearListeners(node, selectedIndexProperty);

		if (vProperty.isValueDefined()) {
			tabPane.getSelectionModel().select((int) vProperty.getValue());
		}

		if(vProperty.getChangeListener().isDefined()) {
			setChangeListener(dispatcher, node, selectedIndexProperty, vProperty.getChangeListener().get());
		}

		if(vProperty.getInvalidationListener().isDefined()) {
			setInvalidationListener(dispatcher, node, selectedIndexProperty, vProperty.getInvalidationListener().get());
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
