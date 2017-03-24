package com.netopyr.reduxfx.jfoenix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.netopyr.reduxfx.jfoenix.builders.JFXButtonBuilder;
import com.netopyr.reduxfx.jfoenix.builders.JFXProgressBarBuilder;
import com.netopyr.reduxfx.jfoenix.builders.JFXTextFieldBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.Factory;
import javaslang.collection.Array;
import javaslang.collection.HashMap;

@SuppressWarnings({"WeakerAccess", "unused"})
public class JFoenix {

    public static <CLASS extends JFXButtonBuilder<CLASS>> JFXButtonBuilder<CLASS> JFXButton(Class<? extends JFXButton> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXButtonBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends JFXButtonBuilder<CLASS>> JFXButtonBuilder<CLASS> JFXButton() {
        return JFXButton(JFXButton.class);
    }


    public static <CLASS extends JFXProgressBarBuilder<CLASS>> JFXProgressBarBuilder<CLASS> JFXProgressBar(Class<? extends JFXProgressBar> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXProgressBarBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends JFXProgressBarBuilder<CLASS>> JFXProgressBarBuilder<CLASS> JFXProgressBar() {
        return JFXProgressBar(JFXProgressBar.class);
    }


    public static <CLASS extends JFXTextFieldBuilder<CLASS>> JFXTextFieldBuilder<CLASS> JFXTextField(Class<? extends JFXTextField> nodeClass) {
        return Factory.node(nodeClass, () -> new JFXTextFieldBuilder<>(nodeClass, Array.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <CLASS extends JFXTextFieldBuilder<CLASS>> JFXTextFieldBuilder<CLASS> JFXTextField() {
        return JFXTextField(JFXTextField.class);
    }

}
