package com.netopyr.reduxfx.examples.fxml.reduxjavafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Function;

public interface View {

    default void dispatch(Object action) {
        ReduxJavaFX.getSingleton().dispatch(action);
    }

    default <STATE, VALUE> ObservableValue<VALUE> select(Function<STATE, VALUE> selector) {
        ObjectProperty<VALUE> observableValue = new SimpleObjectProperty<>();

        ReduxJavaFX.<STATE>getSingleton().addSubscriber(newState -> {

            VALUE newValue = selector.apply(newState);
            observableValue.setValue(newValue);
        });

        return observableValue;
    }

    default <STATE, VALUE> ObservableList<VALUE> selectList(Function<STATE, List<VALUE>> selector) {
        ObservableList<VALUE> list = FXCollections.observableArrayList();

        ReduxJavaFX.<STATE>getSingleton().addSubscriber(newState -> {
            List<VALUE> newValues = selector.apply(newState);

            list.setAll(newValues);
        });

        return list;
    }


}