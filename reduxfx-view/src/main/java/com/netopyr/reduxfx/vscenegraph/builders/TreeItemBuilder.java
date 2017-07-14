package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TreeItemBuilder<BUILDER extends TreeItemBuilder<BUILDER, ELEMENT>, ELEMENT> extends Builder<BUILDER> {

	private static final String VALUE = "value";
	private static final String EXPANDED = "expanded";
	private static final String CHILDREN = "children";

	private final Class<ELEMENT> elementClass;

	public TreeItemBuilder(Class<?> nodeClass,
		Class<ELEMENT> elementClass,
		Map<String, Array<VNode>> childrenMap,
		Map<String, Option<VNode>> singleChildMap,
		Map<String, VProperty> properties,
		Map<VEventType, VEventHandler> eventHandlers) {
		super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
		this.elementClass = elementClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BUILDER create(
		Map<String, Array<VNode>> childrenMap,
		Map<String, Option<VNode>> singleChildMap,
		Map<String, VProperty> properties,
		Map<VEventType, VEventHandler> eventHandlers) {
		return (BUILDER) new TreeItemBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties,
			eventHandlers);
	}

	@SafeVarargs
	public final BUILDER children(TreeItemBuilder<?, ELEMENT>... children) {
		return children(CHILDREN, children == null ? Array.empty() : Array.of(children));
	}

	public final BUILDER children(Iterable<TreeItemBuilder<?, ELEMENT>> children) {
		return children(CHILDREN, children == null ? Array.empty() : Array.ofAll(children));
	}

	public BUILDER value(ELEMENT value) {
		return property(VALUE, value);
	}

	public BUILDER expanded(boolean expanded) {
		return property(EXPANDED, expanded);
	}

	public BUILDER expanded(boolean expanded, VChangeListener<Boolean> listener) {
		return property(EXPANDED, expanded, listener);
	}

	public BUILDER expanded(VChangeListener<? super Boolean> listener) {
		return property(EXPANDED, listener);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
