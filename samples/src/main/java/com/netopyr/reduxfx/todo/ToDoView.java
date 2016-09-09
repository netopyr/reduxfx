package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import javafx.scene.control.TextField;
import rx.Observer;

import java.util.Optional;

import static com.netopyr.reduxfx.vscenegraph.VScenegraph.node;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.onEvent;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.property;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.ref;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.LIST_VIEW;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.STACK_PANE;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.TEXT_FIELD;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.V_BOX;

public class ToDoView implements View<AppModel, Action> {

    /*
    1. Pass Observer
    2. Pass Callback
    3. Define later
     */

    private Optional<TextField> textField = Optional.empty();

    public VNode view(AppModel state, Observer<Action> actions) {

        // TODO: Implement TableView with completed flag and text
        return
                node(STACK_PANE,
                        node(V_BOX,
                                node(TEXT_FIELD,
                                        ref(tf -> textField = Optional.of((TextField) tf)),
                                        onEvent("action", e -> {
                                            actions.onNext(Actions.addToDo(textField.map(TextField::getText).orElse("")));
                                            textField.ifPresent(tf -> tf.setText(""));
                                        })
                                ),
                                node(LIST_VIEW,
                                        property("items", state.getTodos().map(ToDoEntry::getText))
                                )
                        )
                );
    }


        /*
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

        StackPane()
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
        .end()

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
