package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TreeItemBuilder<B extends TreeItemBuilder<B, E>, E> extends Builder<B> {

	private static final String VALUE = "value";
	private static final String EXPANDED = "expanded";
	private static final String CHILDREN = "children";

	private final Class<E> elementClass;

	public TreeItemBuilder(Class<?> nodeClass,
		Class<E> elementClass,
		Map<String, Array<VNode>> childrenMap,
		Map<String, Option<VNode>> singleChildMap,
		Map<String, VProperty> properties,
		Map<VEventType, VEventHandler> eventHandlers) {
		super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
		this.elementClass = elementClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected B create(
		Map<String, Array<VNode>> childrenMap,
		Map<String, Option<VNode>> singleChildMap,
		Map<String, VProperty> properties,
		Map<VEventType, VEventHandler> eventHandlers) {
		return (B) new TreeItemBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties,
			eventHandlers);
	}

	@SafeVarargs
	public final B children(TreeItemBuilder<?, E>... children) {
		return children(CHILDREN, children == null ? Array.empty() : Array.of(children));
	}

	public final B children(Iterable<TreeItemBuilder<?, E>> children) {
		return children(CHILDREN, children == null ? Array.empty() : Array.ofAll(children));
	}

	public B value(E value) {
		return property(VALUE, value);
	}

	public B expanded(boolean expanded) {
		return property(EXPANDED, expanded);
	}

	public B expanded(boolean expanded, VChangeListener<Boolean> listener) {
		return property(EXPANDED, expanded, listener);
	}

	public B expanded(VChangeListener<? super Boolean> listener) {
		return property(EXPANDED, listener);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
