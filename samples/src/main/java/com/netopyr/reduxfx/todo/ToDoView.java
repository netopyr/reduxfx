package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.control.TextField;
import rx.Observer;

import static com.netopyr.reduxfx.VScenegraphFactory.ListView;
import static com.netopyr.reduxfx.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.VScenegraphFactory.TextField;
import static com.netopyr.reduxfx.VScenegraphFactory.VBox;
import static com.netopyr.reduxfx.VScenegraphFactory.onEvent;
import static com.netopyr.reduxfx.VScenegraphFactory.property;
import static com.netopyr.reduxfx.VScenegraphFactory.ref;

public class ToDoView implements View<AppModel, Action> {

    /*
    1. Pass Observer
    2. Pass Callback
    3. Define later
     */

    private javafx.scene.control.TextField textField;

    public VNode view(AppModel state, Observer<Action> actions) {

        // TODO: Implement TableView with completed flag and text
        return
                StackPane(
                        VBox(
                                TextField(
                                        ref(tf -> textField = (TextField) tf),
                                        onEvent("action", e -> {
                                            if (textField != null) {
                                                actions.onNext(Actions.addToDo(textField.getText()));
                                                textField.setText("");
                                            }
                                        })
                                ),
                                ListView(
                                        property("items", state.getTodos().map(ToDoEntry::getText))
                                )
                        )
                );
    }


        /*
        new StackPane(
                new VBox(
                        new TextField(
                                ref(tf -> textField = Optional.of((TextField) tf)),
                                onEvent("action", e -> {
                                    actions.onNext(Actions.addToDo(textField.map(TextField::getText).orElse("")));
                                    textField.ifPresent(tf -> tf.setText(""));
                                })
                        ),
                        new ListView(
                                property("items", state.getTodos().map(ToDoEntry::getText))
                        )
                )
        );

        StackPane(
            VBox(
                HBox(
                    TextField(
                        property("text", text),
                        onChange("text", onTextChange)

                        text(text),
                        onTextChange(onTextChange)

                        text(text, onTextChange)
                    ),
                    Button(
                        text("Create"),
                        disable(true)
                    )
                ),
                ListView(
                    items(...)
                )
            )
        )

        StackPane.create()
            .VBox()
                .HBox()
                    .TextField()
                        .text(text, onTextChange)
                    .end()
                    .Button()
                        .text("Create")
                        .disable(true)
                    .end()
                .end()
                .ListView()
                    .items(...)
                .()
            .end()
        .build()

        <StackPane>
            <VBox>
                <HBox>
                    <TextField text="${text}" onTextChange="${onTextChange}" />
                    <Button text="Create" disable="true"/>
                </HBox>
                <ListView>
            </VBox>
        </StackPane>
         */

}
