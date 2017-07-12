package com.netopyr.reduxfx.jfoenix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.netopyr.reduxfx.jfoenix.builders.JFXButtonBuilder;
import com.netopyr.reduxfx.jfoenix.builders.JFXProgressBarBuilder;
import com.netopyr.reduxfx.jfoenix.builders.JFXTextFieldBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.Factory;
import io.vavr.collection.HashMap;

@SuppressWarnings({"WeakerAccess", "unused"})
public class JFoenix {

    private JFoenix() {}

    public static <B extends JFXButtonBuilder<B>> JFXButtonBuilder<B> JFXButton(Class<? extends JFXButton> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXButtonBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends JFXButtonBuilder<B>> JFXButtonBuilder<B> JFXButton() {
        return JFXButton(JFXButton.class);
    }


    public static <B extends JFXProgressBarBuilder<B>> JFXProgressBarBuilder<B> JFXProgressBar(Class<? extends JFXProgressBar> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXProgressBarBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends JFXProgressBarBuilder<B>> JFXProgressBarBuilder<B> JFXProgressBar() {
        return JFXProgressBar(JFXProgressBar.class);
    }


    public static <B extends JFXTextFieldBuilder<B>> JFXTextFieldBuilder<B> JFXTextField(Class<? extends JFXTextField> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXTextFieldBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends JFXTextFieldBuilder<B>> JFXTextFieldBuilder<B> JFXTextField() {
        return JFXTextField(JFXTextField.class);
    }

}
