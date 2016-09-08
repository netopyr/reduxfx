package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import rx.Observer;

import java.util.stream.Collectors;

import static com.netopyr.reduxfx.vscenegraph.VScenegraph.node;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.onChange;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.onEvent;
import static com.netopyr.reduxfx.vscenegraph.VScenegraph.property;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.BUTTON;
import static com.netopyr.reduxfx.vscenegraph.elements.VNodeType.H_BOX;
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

    public VNode view(AppModel state, Observer<Action> actions) {
        // TODO: Implement TableView with completed flag and text
        return
                node(STACK_PANE,
                        node(V_BOX,
                                node(H_BOX,
                                        node(TEXT_FIELD,
                                                property("text", state.getNewToDoText()),
                                                onChange("text", (source, oldValue, newValue) -> actions.onNext(Actions.newTextFieldChanged((String) newValue)))
                                        ),
                                        node(BUTTON,
                                                property("text", "Create"),
                                                property("disable", state.getNewToDoText().isEmpty()),
                                                onEvent("action", e -> actions.onNext(Actions.addToDo()))
                                        )
                                ),
                                node(LIST_VIEW,
                                        property("items", state.getTodos().stream()
                                                .map(ToDoEntry::getText)
                                                .collect(Collectors.toList())
                                        )
                                )
                        )
                );
    }


        /*
        StackPane(
            VBox(
                HBox(
                    TextField(
                        text(
                            state.getNewToDoText(),
                             (source, oldValue, newValue) -> actions.onNext(Actions.newTextFieldChanged((String) newValue))
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

        <StackPane>
            <VBox>
                <HBox>
                    <TextField/>
                    <Button text="Create" disable="true"/>
                </HBox>
                <ListView>
            </VBox>
        </StackPane>
         */

}
