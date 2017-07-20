package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.TabPaneSelectionModelAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TabPaneBuilder<B extends TabPaneBuilder<B>> extends ControlBuilder<B> {

	private static final String TAB_MIN_HEIGHT = "tabMinHeight";
	private static final String TAB_MIN_WIDTH = "tabMinWidth";
	private static final String TAB_MAX_HEIGHT = "tabMaxHeight";
	private static final String TAB_MAX_WIDTH = "tabMaxWidth";

	private static final String TAB_CLOSING_POLICY = "tabClosingPolicy";
	private static final String SIDE = "side";
	private static final String ROTATED_GRAPHIC = "rotateGraphic";

	private static final String SELECTION_MODEL = "selectionModel";

	private static final String TABS = "tabs";


	public TabPaneBuilder(Class<?> nodeClass,
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
		return (B) new TabPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
	}

	public B tabs(TabBuilder... values) {
		return children(TABS, Array.of(values));
	}

	public B selectedIndex(int index) {
		Accessors.registerAccessor(getNodeClass(), SELECTION_MODEL, TabPaneSelectionModelAccessor::new);

		return property(SELECTION_MODEL, index);
	}

	public B selectedIndex(int index, VChangeListener<Integer> listener) {
		Accessors.registerAccessor(getNodeClass(), SELECTION_MODEL, TabPaneSelectionModelAccessor::new);

		return property(SELECTION_MODEL, index, listener);
	}

	public B selectedIndex(VChangeListener<Integer> listener) {
		Accessors.registerAccessor(getNodeClass(), SELECTION_MODEL, TabPaneSelectionModelAccessor::new);

		return property(SELECTION_MODEL, listener);
	}

	public B tabClosingPolicy(TabPane.TabClosingPolicy value) {
		return property(TAB_CLOSING_POLICY, value);
	}

	public B side(Side value) {
		return property(SIDE, value);
	}

	public B rotatedGraphic(boolean value) {
		return property(ROTATED_GRAPHIC, value);
	}

	public B tabMaxHeight(double value) {
		return property(TAB_MAX_HEIGHT, value);
	}

	public B tabMaxWidth(double value) {
		return property(TAB_MAX_WIDTH, value);
	}

	public B tabMinHeight(double value) {
		return property(TAB_MIN_HEIGHT, value);
	}

	public B tabMinWidth(double value) {
		return property(TAB_MIN_WIDTH, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
