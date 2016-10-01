package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.VScenegraphFactory.*;

public class ToDoView implements View<AppModel, Action> {

    public VNode<Action> view(AppModel state) {

        return
                StackPane(
                        VBox(
                                HBox(
                                        TextField(
                                                text(
                                                        state.getNewToDoText(),
                                                        (oldValue, newValue) -> Actions.newTextFieldChanged(newValue)
                                                )
                                        ),
                                        Button(
                                                text("Create"),
                                                disable(state.getNewToDoText().isEmpty()),
                                                onAction(e -> Actions.addToDo())
                                        )
                                ),
                                ListView(
                                        items(state.getTodos()
                                                .filter(toDoEntry -> {
                                                    switch (state.getFilter()) {
                                                        case ACTIVE:
                                                            return ! toDoEntry.isCompleted();
                                                        case COMPLETED:
                                                            return toDoEntry.isCompleted();
                                                        default:
                                                            return true;
                                                    }
                                                })
                                                .map(ToDoEntry::getText))
                                ),
                                HBox(
                                        ToggleButton(
                                                text("All"),
                                                toggleGroup("filterButtonGroup"),
                                                onAction(e -> Actions.setFilter(Filter.ALL))
                                        ),
                                        ToggleButton(
                                                text("Active"),
                                                toggleGroup("filterButtonGroup"),
                                                onAction(e -> Actions.setFilter(Filter.ACTIVE))
                                        ),
                                        ToggleButton(
                                                text("Completed"),
                                                toggleGroup("filterButtonGroup"),
                                                onAction(e -> Actions.setFilter(Filter.COMPLETED))
                                        )
                                )
                        )
                );
    }


        /*
                StackPane(
                        VBox(
                                HBox(
                                        TextField()
                                                .text(state.getNewToDoText())
                                                .onChange((oldValue, newValue) -> Actions.newTextFieldChanged(newValue))
                                        ),
                                        Button(
                                                text("Create"),
                                                disable(state.getNewToDoText().isEmpty()),
                                                onAction(e -> Actions.addToDo())
                                        )
                                ),
                                ListView(
                                        items(state.getTodos().map(ToDoEntry::getText))
                                )
                        )
                );


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
                        ListView(
                                property(ITEMS, state.getTodos().map(ToDoEntry::getText))
                        )
                        ListView(
                                items(state.getTodos().map(ToDoEntry::getText))
                        )

                        ListView(
                                items(
                                        value(state.getTodos().map(ToDoEntry::getText)),
                                        onChange(bla -> blabla)
                                )
                        )
                        ListView(
                                items(state.getTodos().map(ToDoEntry::getText)),
                                onItemsChanged(bla -> blabla)
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
