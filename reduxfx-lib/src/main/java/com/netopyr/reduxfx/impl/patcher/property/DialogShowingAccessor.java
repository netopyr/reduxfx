package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeUtilities;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class DialogShowingAccessor extends AbstractNoConversionAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(DialogShowingAccessor.class);

    public DialogShowingAccessor() {
        super(NodeUtilities.getPropertyGetter(Dialog.class, "showing").get());
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        final Object bean = property.getBean();
        if (bean instanceof Stage) {
            final Stage stage = (Stage) bean;
            if (Boolean.TRUE.equals(value)) {
                stage.show();
            } else {
                stage.hide();
            }
        } else {
            LOG.warn("Unsupported Dialog-type encountered: {}", bean.getClass());
        }
    }
}
