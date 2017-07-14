package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.TreeViewCellFactoryAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.function.Function;

public class TreeViewBuilder<B extends TreeViewBuilder<B, E>, E>
	extends ControlBuilder<B> {

	private static final String CELL_FACTORY = "cellFactory";
	private static final String ROOT = "root";
	private static final String SHOW_ROOT = "showRoot";

	private final Class<E> elementClass;

	public TreeViewBuilder(Class<?> nodeClass,
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
		return (B) new TreeViewBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties,
			eventHandlers);
	}

	public B root(TreeItemBuilder root) {
		return child(ROOT, root);
	}

	public B showRoot(boolean value) {
		return property(SHOW_ROOT, value);
	}

	public B cellFactory(Function<? super E, VNode> value) {
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
