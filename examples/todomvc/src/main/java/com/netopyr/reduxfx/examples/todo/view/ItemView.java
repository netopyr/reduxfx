package com.netopyr.reduxfx.examples.todo.view;

import com.netopyr.reduxfx.examples.todo.actions.Actions;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.Objects;

import static com.netopyr.reduxfx.fontawesomefx.FontAwesomeFX.FontAwesomeIconView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.CheckBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TextField;

/**
 * The class {@code ItemView} defines how a single entry in the todo-list should be visualized.
 */
class ItemView {

    private ItemView() {}

    /**
     * The method {@code ItemView} calculates a new visual component for the given {@link TodoEntry}.
     * <p>
     * It consists of an {@code HBox} with two parts: a {@code CheckBox} with which the completed-flag can be updated
     * and a {@code StackPane} that either shows a {@code Label} with the text of the {@code TodoEntry} and a
     * delete-button (which is only visible while the mouse hovers over the element). Or the {@code StackPane} shows
     * a {@code TextField}, if the text of the current {@code TodoEntry} is being edited at the moment.
     *
     * @param todoEntry the {@code TodoEntry} that contains the data of this element
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode ItemView(TodoEntry todoEntry) {
        Objects.requireNonNull(todoEntry, "The parameter 'todoEntry' must not be null");

        return HBox()
                .id("root")
                .alignment(Pos.CENTER_LEFT)
                .minWidth(Region.USE_PREF_SIZE)
                .minHeight(Region.USE_PREF_SIZE)
                .styleClass("item_root")
                .stylesheets(ItemView.class.getResource("itemview.css").toString())
                .children(
                        CheckBox()
                                .id("completed")
                                .mnemonicParsing(false)
                                .selected(todoEntry.isCompleted())
                                // This is how an event-lister is defined. The EventListener get the event and has to return
                                // the Action that should be dispatched to the Updater.
                                // If the onAction-event is fired, we want to dispatch a CompleteTodoAction with the id of this element.
                                .onAction(e -> Actions.completeTodo(todoEntry.getId())),
                        StackPane()
                                .alignment(Pos.CENTER_LEFT)
                                .hgrow(Priority.ALWAYS)
                                .children(
                                        HBox()
                                                .id("contentBox")
                                                .visible(!todoEntry.isEditMode())
                                                .styleClass("content_box")
                                                // This is how a ChangeListener can be set.
                                                // The ChangeListener gets the old value and the new value and has to return the
                                                // Action that should be dispatched.
                                                // Here we want to set the hover-flag of this item, while the user hovers over this
                                                // item with the mouse.
                                                .hover((oldValue, newValue) -> Actions.setTodoHover(todoEntry.getId(), Boolean.TRUE.equals(newValue)))
                                                .children(
                                                        Label()
                                                                .id("contentLabel")
                                                                .maxWidth(Double.MAX_VALUE)
                                                                .maxHeight(Double.MAX_VALUE)
                                                                .text(todoEntry.getText())
                                                                .hgrow(Priority.ALWAYS)
                                                                .styleClass("label", todoEntry.isCompleted() ? "strikethrough" : "")
                                                                // If the onMouseClicked-event is fired, we want to dispatch a SetEditModeAction
                                                                // to enable editing of this item (but only if it was a single click).
                                                                .onMouseClicked(e -> e.getClickCount() > 1 ? Actions.setEditMode(todoEntry.getId(), true) : null),
                                                        Button()
                                                                .id("deleteButton")
                                                                .mnemonicParsing(false)
                                                                .visible(todoEntry.isHover())
                                                                .graphic(
                                                                        FontAwesomeIconView()
                                                                                .icon(FontAwesomeIcon.CLOSE)
                                                                                .size("1.5em")
                                                                                .styleClass("close_icon")
                                                                )
                                                                // If the onAction-event is fired, we want to dispatch a DeleteTodoAction
                                                                // to delete the current item.
                                                                .onAction(e -> Actions.deleteTodo(todoEntry.getId()))
                                                ),
                                        TextField()
                                                .id("contentInput")
                                                .visible(todoEntry.isEditMode())
                                                .promptText("What needs to be done?")
                                                // The focus-property is somewhat special. Setting it results in a focus-request for
                                                // this component. We want to try to grab the focus, if we are in edit-mode.
                                                // If the TextField loses focus, we have to clear the editMode-flag.
                                                .focused(todoEntry.isEditMode(), (oldValue, newValue) -> newValue ? null : Actions.setEditMode(todoEntry.getId(), false))
                                                // We set the text of this component to the value that is stored in the state and
                                                // we create an EditTodoAction to change the value of the todo-entry.
                                                .text(todoEntry.getText(), ((oldValue, newValue) -> Actions.editTodo(todoEntry.getId(), newValue)))
                                                // If the onAction-event is fired, we want to dispatch an EditModeAction to leave the
                                                // edit mode.
                                                .onAction(e -> Actions.setEditMode(todoEntry.getId(), false))
                                )
                );
    }
}
