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
import javaslang.collection.Array;
import javaslang.collection.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
public class FontAwesomeFX {

    public static <CLASS extends FontAwesomeIconViewBuilder<CLASS>> FontAwesomeIconViewBuilder<CLASS> FontAwesomeIconView(Class<? extends FontAwesomeIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new FontAwesomeIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends FontAwesomeIconViewBuilder<CLASS>> FontAwesomeIconViewBuilder<CLASS> FontAwesomeIconView() {
        return FontAwesomeIconView(FontAwesomeIconView.class);
    }


    public static <CLASS extends EmojiOneViewBuilder<CLASS>> EmojiOneViewBuilder<CLASS> EmojiOneView(Class<? extends EmojiOneView> nodeClass) {
        return Factory.node(nodeClass, () -> new EmojiOneViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends EmojiOneViewBuilder<CLASS>> EmojiOneViewBuilder<CLASS> EmojiOneView() {
        return EmojiOneView(EmojiOneView.class);
    }


    public static <CLASS extends Icons525ViewBuilder<CLASS>> Icons525ViewBuilder<CLASS> Icons525View(Class<? extends Icons525View> nodeClass) {
        return Factory.node(nodeClass, () -> new Icons525ViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends Icons525ViewBuilder<CLASS>> Icons525ViewBuilder<CLASS> Icons525View() {
        return Icons525View(Icons525View.class);
    }


    public static <CLASS extends MaterialDesignIconViewBuilder<CLASS>> MaterialDesignIconViewBuilder<CLASS> MaterialDesignIconView(Class<? extends MaterialDesignIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialDesignIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends MaterialDesignIconViewBuilder<CLASS>> MaterialDesignIconViewBuilder<CLASS> MaterialDesignIconView() {
        return MaterialDesignIconView(MaterialDesignIconView.class);
    }


    public static <CLASS extends MaterialIconViewBuilder<CLASS>> MaterialIconViewBuilder<CLASS> MaterialIconView(Class<? extends MaterialIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends MaterialIconViewBuilder<CLASS>> MaterialIconViewBuilder<CLASS> MaterialIconView() {
        return MaterialIconView(MaterialIconView.class);
    }


    public static <CLASS extends MaterialStackIconViewBuilder<CLASS>> MaterialStackIconViewBuilder<CLASS> MaterialStackIconView(Class<? extends MaterialStackIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new MaterialStackIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends MaterialStackIconViewBuilder<CLASS>> MaterialStackIconViewBuilder<CLASS> MaterialStackIconView() {
        return MaterialStackIconView(MaterialStackIconView.class);
    }


    public static <CLASS extends OctIconViewBuilder<CLASS>> OctIconViewBuilder<CLASS> OctIconView(Class<? extends OctIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new OctIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends OctIconViewBuilder<CLASS>> OctIconViewBuilder<CLASS> OctIconView() {
        return OctIconView(OctIconView.class);
    }


    public static <CLASS extends WeatherIconViewBuilder<CLASS>> WeatherIconViewBuilder<CLASS> WeatherIconView(Class<? extends WeatherIconView> nodeClass) {
        return Factory.node(nodeClass, () -> new WeatherIconViewBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends WeatherIconViewBuilder<CLASS>> WeatherIconViewBuilder<CLASS> WeatherIconView() {
        return WeatherIconView(WeatherIconView.class);
    }


}
