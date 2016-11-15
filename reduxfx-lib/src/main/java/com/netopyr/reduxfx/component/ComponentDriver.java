package com.netopyr.reduxfx.component;

import com.netopyr.reduxfx.component.command.FireEventCommand;
import com.netopyr.reduxfx.component.command.IntegerChangedCommand;
import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import com.netopyr.reduxfx.component.property.ReduxFXObjectProperty;
import com.netopyr.reduxfx.component.property.ReduxFXReadOnlyIntegerProperty;
import com.netopyr.reduxfx.component.property.ReduxFXReadOnlyObjectProperty;
import com.netopyr.reduxfx.differ.Differ;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javaslang.Tuple;
import javaslang.collection.Vector;
import javaslang.control.Option;
import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ComponentDriver<ACTION> {

    private final Observable<Command> commandObservable;
    private Observable<FireEventCommand<? extends Event>> fireEventCommandObservable;
    private Observable<IntegerChangedCommand> integerChangedCommandObservable;
    private Observable<ObjectChangedCommand<?>> objectChangedCommandObservable;
    private Observer<ACTION> actionObserver;

    public <STATE> ComponentDriver(
            STATE initialState,
            BiFunction<STATE, ACTION, Update<STATE>> updater,
            Function<STATE, VNode<ACTION>> view,
            Parent root
    ) {

        final Subject<ACTION, ACTION> actionSubject = PublishSubject.create();

        final Observable<Update<STATE>> updatesObservable = actionSubject
                .scan(Update.of(initialState), (update, action) -> updater.apply(update.getState(), action));

        final Observable<STATE> stateObservable = updatesObservable.map(Update::getState);

        final Observable<Option<VNode<ACTION>>> vScenegraphObservable = stateObservable
                .map(view::apply)
                .map(Option::of)
                .startWith(Option.<VNode<ACTION>>none());

        final Observable<Vector<Patch>> patchObservable = vScenegraphObservable.zipWith(vScenegraphObservable.skip(1), Differ::diff);

        final Patcher<ACTION> patcher = new Patcher<>(actionSubject::onNext);

        vScenegraphObservable
                .zipWith(patchObservable, Tuple::of)
                .forEach(tuple -> patcher.patch(root, tuple._1, tuple._2));


        commandObservable = updatesObservable
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);

        actionObserver = actionSubject;
    }



    public Observable<Command> getCommandObservable() {
        return commandObservable;
    }

    public Observer<ACTION> getActionObserver() {
        return actionObserver;
    }



    public ReadOnlyIntegerProperty createReadOnlyIntegerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        if (integerChangedCommandObservable == null) {
            integerChangedCommandObservable = commandObservable
                    .filter(command -> command instanceof IntegerChangedCommand)
                    .cast(IntegerChangedCommand.class);
        }
        final Observable<IntegerChangedCommand> propertyObervable =
                integerChangedCommandObservable.filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyIntegerProperty(bean, name, propertyObervable);
    }

    public <T> ReadOnlyObjectProperty<T> createReadOnlyObjectProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        if (objectChangedCommandObservable == null) {
            objectChangedCommandObservable = commandObservable
                    .filter(command -> command instanceof ObjectChangedCommand)
                    .map(command -> (ObjectChangedCommand<?>) command);
        }
        final Observable<ObjectChangedCommand<?>> propertyObervable =
                objectChangedCommandObservable.filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyObjectProperty<>(bean, name, propertyObervable);
    }



    public <T> ObjectProperty<T> createObjectProperty(Object bean, String name, BiFunction<T, T, ACTION> mapper) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(mapper, "Mapper must not be null");

        if (objectChangedCommandObservable == null) {
            objectChangedCommandObservable = commandObservable
                    .filter(command -> command instanceof ObjectChangedCommand)
                    .map(command -> (ObjectChangedCommand<?>) command);
        }
        final Observable<ObjectChangedCommand<?>> propertyObervable =
                objectChangedCommandObservable.filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXObjectProperty<>(bean, name, propertyObervable, mapper, actionObserver);
    }



    public <EVENT extends Event> ObjectProperty<EventHandler<EVENT>> createEventHandlerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        if (fireEventCommandObservable == null) {
            fireEventCommandObservable = commandObservable
                    .filter(command -> command instanceof FireEventCommand)
                    .map(command -> (FireEventCommand<? extends Event>) command);
        }

        final ObjectProperty<EventHandler<EVENT>> property = new SimpleObjectProperty<>(bean, name);
        fireEventCommandObservable.filter(command -> name.equals(command.getEventName()))
                .forEach(command -> {
                    final EventHandler<EVENT> eventHandler = property.get();
                    if (eventHandler != null) {
                        eventHandler.handle((EVENT) command.getEvent());
                    }
                });

        return property;
    }
}
