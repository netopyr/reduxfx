package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class DialogModalityAccessor extends AbstractNoConversionAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(DialogModalityAccessor.class);

    public DialogModalityAccessor() {
        super(NodeUtilities.getPropertyGetter(Dialog.class, "showing").get());
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        final Object bean = property.getBean();
        if (bean instanceof Stage) {
            final Stage stage = (Stage) bean;
            if (stage.isShowing()) {
                stage.hide();
                stage.initModality((Modality) value);
                stage.show();
            } else {
                stage.initModality((Modality) value);
            }
        } else {
            LOG.warn("Unsupported Dialog-type encountered: {}", bean.getClass());
        }
    }
}
