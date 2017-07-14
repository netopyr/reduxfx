package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Orientation;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SplitPaneBuilder<B extends SplitPaneBuilder<B>> extends ControlBuilder<B> {

	private static final String ORIENTATION = "orientation";
	private static final String ITEMS = "items";
	private static final String DIVIDER_POSITIONS = "dividerPositions";

	public SplitPaneBuilder(Class<?> nodeClass,
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
		return (B) new SplitPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
	}

	public B orientation(Orientation orientation) {
		return property(ORIENTATION, orientation);
	}

	public B items(VNode... nodes) {
		return children(ITEMS, nodes == null ? Array.empty() : Array.of(nodes));
	}

	public B items(Iterable<VNode> nodes) {
		return children(ITEMS, nodes == null ? Array.empty() : Array.ofAll(nodes));
	}

	public B dividerPositions(double... positions) {
		return property(DIVIDER_POSITIONS, positions);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
