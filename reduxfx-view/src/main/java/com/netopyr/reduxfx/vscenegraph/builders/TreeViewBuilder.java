package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.TreeViewCellFactoryAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.function.Function;

public class TreeViewBuilder<BUILDER extends TreeViewBuilder<BUILDER, ELEMENT>, ELEMENT>
	extends ControlBuilder<BUILDER> {

	private static final String CELL_FACTORY = "cellFactory";
	private static final String ROOT = "root";
	private static final String SHOW_ROOT = "showRoot";

	private final Class<ELEMENT> elementClass;

	public TreeViewBuilder(Class<?> nodeClass,
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
		return (BUILDER) new TreeViewBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties,
			eventHandlers);
	}

	public BUILDER root(TreeItemBuilder root) {
		return child(ROOT, root);
	}

	public BUILDER showRoot(boolean value) {
		return property(SHOW_ROOT, value);
	}

	public BUILDER cellFactory(Function<? super ELEMENT, VNode> value) {
		Accessors.registerAccessor(getNodeClass(), "cellFactory", TreeViewCellFactoryAccessor::new);
		return property(CELL_FACTORY, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
