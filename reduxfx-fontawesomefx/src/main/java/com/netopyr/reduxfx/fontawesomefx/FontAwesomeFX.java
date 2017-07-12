package com.netopyr.reduxfx.fontawesomefx;

import com.netopyr.reduxfx.fontawesomefx.builders.EmojiOneViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.FontAwesomeIconViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.Icons525ViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.MaterialDesignIconViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.MaterialIconViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.MaterialStackIconViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.OctIconViewBuilder;
import com.netopyr.reduxfx.fontawesomefx.builders.WeatherIconViewBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.Factory;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.icons525.Icons525View;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import de.jensd.fx.glyphs.materialstackicons.MaterialStackIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import de.jensd.fx.glyphs.weathericons.WeatherIconView;
import io.vavr.collection.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
public class FontAwesomeFX {

    private FontAwesomeFX() {}

    public static <B extends FontAwesomeIconViewBuilder<B>> FontAwesomeIconViewBuilder<B> FontAwesomeIconView(Class<? extends FontAwesomeIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new FontAwesomeIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends FontAwesomeIconViewBuilder<B>> FontAwesomeIconViewBuilder<B> FontAwesomeIconView() {
        return FontAwesomeIconView(FontAwesomeIconView.class);
    }


    public static <B extends EmojiOneViewBuilder<B>> EmojiOneViewBuilder<B> EmojiOneView(Class<? extends EmojiOneView> nodeClass) {
        return Factory.node(nodeClass, () -> new EmojiOneViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends EmojiOneViewBuilder<B>> EmojiOneViewBuilder<B> EmojiOneView() {
        return EmojiOneView(EmojiOneView.class);
    }


    public static <B extends Icons525ViewBuilder<B>> Icons525ViewBuilder<B> Icons525View(Class<? extends Icons525View> nodeClass) {
        return Factory.node(nodeClass, () -> new Icons525ViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends Icons525ViewBuilder<B>> Icons525ViewBuilder<B> Icons525View() {
        return Icons525View(Icons525View.class);
    }


    public static <B extends MaterialDesignIconViewBuilder<B>> MaterialDesignIconViewBuilder<B> MaterialDesignIconView(Class<? extends MaterialDesignIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialDesignIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends MaterialDesignIconViewBuilder<B>> MaterialDesignIconViewBuilder<B> MaterialDesignIconView() {
        return MaterialDesignIconView(MaterialDesignIconView.class);
    }


    public static <B extends MaterialIconViewBuilder<B>> MaterialIconViewBuilder<B> MaterialIconView(Class<? extends MaterialIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends MaterialIconViewBuilder<B>> MaterialIconViewBuilder<B> MaterialIconView() {
        return MaterialIconView(MaterialIconView.class);
    }


    public static <B extends MaterialStackIconViewBuilder<B>> MaterialStackIconViewBuilder<B> MaterialStackIconView(Class<? extends MaterialStackIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialStackIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends MaterialStackIconViewBuilder<B>> MaterialStackIconViewBuilder<B> MaterialStackIconView() {
        return MaterialStackIconView(MaterialStackIconView.class);
    }


    public static <B extends OctIconViewBuilder<B>> OctIconViewBuilder<B> OctIconView(Class<? extends OctIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new OctIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends OctIconViewBuilder<B>> OctIconViewBuilder<B> OctIconView() {
        return OctIconView(OctIconView.class);
    }


    public static <B extends WeatherIconViewBuilder<B>> WeatherIconViewBuilder<B> WeatherIconView(Class<? extends WeatherIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new WeatherIconViewBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends WeatherIconViewBuilder<B>> WeatherIconViewBuilder<B> WeatherIconView() {
        return WeatherIconView(WeatherIconView.class);
    }

}
