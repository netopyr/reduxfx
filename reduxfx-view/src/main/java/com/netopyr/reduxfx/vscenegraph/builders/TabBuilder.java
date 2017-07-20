package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.StyleClassAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TabBuilder<B extends TabBuilder<B>> extends Builder<B> {

	private static final String CLOSABLE = "closable";
	private static final String CONTENT = "content";
	private static final String CONTEXT_MENU = "contextMenu";
	private static final String GRAPHIC = "graphic";
	private static final String TEXT = "text";

//	TODO
//	private static final String TOOLTIP = "tooltip";

	private static final String ID = "id";
	private static final String DISABLE = "disable";
	private static final String STYLE = "style";
	private static final String STYLE_CLASS = "styleClass";



	public TabBuilder(Class<?> nodeClass,
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
		return (B) new TabBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
	}

	public B content(VNode value) {
		return child(CONTENT, value);
	}

	public B text(String value) {
		return property(TEXT, value);
	}

	public B closable(boolean value) {
		return property(CLOSABLE, value);
	}

	public B graphic(VNode value) {
		return child(GRAPHIC, value);
	}

	public B contextMenu(ContextMenuBuilder value) {
		return child(CONTEXT_MENU, value);
	}

	public B id(String value) {
		return property(ID, value);
	}

	public B style(String value) {
		return property(STYLE, value);
	}

	public B styleClass(String... value) {
		Accessors.registerAccessor(getNodeClass(), STYLE_CLASS, StyleClassAccessor::new);
		return property(STYLE_CLASS, value == null ? Array.empty() : Array.of(value));
	}

	public B disable(boolean value) {
		return property(DISABLE, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.appendSuper(super.toString())
			.toString();
	}
}
