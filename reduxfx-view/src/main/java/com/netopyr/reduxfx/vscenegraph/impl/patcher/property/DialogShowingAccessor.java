package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.builders.DialogBuilder;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import io.vavr.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.netopyr.reduxfx.vscenegraph.builders.DialogBuilder.MODAL;

public class DialogShowingAccessor extends ListenerHandlingAccessor {

    private final Supplier<Option<Object>> producer;
    private final MethodHandle propertyGetter = NodeUtilities.getPropertyGetter(Dialog.class, DialogBuilder.SHOWING).get();

    public DialogShowingAccessor(Supplier<Option<Object>> producer) {
        this.producer = producer;
    }

    @Override
    protected Object fxToV(Object value) {
        return value;
    }

    @Override
    protected Object vToFX(Object value) {
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object originalNode, String name, VProperty vProperty) {

        Object node = originalNode;
        final Dialog dialog = (Dialog) node;

        final ReadOnlyProperty property;
        try {
            property = (ReadOnlyProperty) propertyGetter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + name + " from Node-class " + node.getClass(), throwable);
        }

        clearListeners(node, property);

        if (vProperty.isValueDefined()) {
            if (Boolean.TRUE.equals(vProperty.getValue())) {
                if (!dialog.isShowing()) {
                    final Object modality = NodeUtilities.getProperties(dialog).get(MODAL);
                    if (modality instanceof Modality) {
                        dialog.initModality((Modality) modality);
                    }
                    dialog.show();
                }
            } else {
                dialog.close();
                final ArrayList<Object> dialogs = (ArrayList<Object>) NodeUtilities.getProperties(dialog.getOwner()).get("dialogs");
                final Dialog newDialog = (Dialog) producer.get().get();
                newDialog.initOwner(dialog.getOwner());
                newDialog.initModality(dialog.getModality());
                newDialog.setContentText(dialog.getContentText());
                newDialog.setHeaderText(dialog.getHeaderText());
                newDialog.setGraphic(dialog.getGraphic());
                dialogs.replaceAll(oldDialog -> newDialog);
                node = newDialog;
            }
        }

        if (vProperty.getChangeListener().isDefined()) {
            setChangeListener(dispatcher, node, property, vProperty.getChangeListener().get());
        }

        if (vProperty.getInvalidationListener().isDefined()) {
            setInvalidationListener(dispatcher, node, property, vProperty.getInvalidationListener().get());
        }
    }

}
