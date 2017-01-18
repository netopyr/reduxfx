package com.netopyr.reduxfx.component.driver;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.component.command.FireEventCommand;
import com.netopyr.reduxfx.component.command.IntegerChangedCommand;
import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import com.netopyr.reduxfx.component.property.ReduxFXObjectProperty;
import com.netopyr.reduxfx.component.property.ReduxFXReadOnlyIntegerProperty;
import com.netopyr.reduxfx.component.property.ReduxFXReadOnlyObjectProperty;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.Objects;
import java.util.function.BiConsumer;

public class ComponentDriver implements Driver {

    private final Subject<Command> commandSubject = PublishSubject.create();
    private final Subject<Object> actionSubject = PublishSubject.create();

    private Observable<FireEventCommand<? extends Event>> fireEventCommandObservable;
    private Observable<IntegerChangedCommand> integerChangedCommandObservable;
    private Observable<ObjectChangedCommand<?>> objectChangedCommandObservable;


    @Override
    public Observer<Command> getCommandObserver() {
        return commandSubject;
    }

    @Override
    public Observable<Object> getActionObservable() {
        return actionSubject;
    }


    private Observable<IntegerChangedCommand> getIntegerChangedCommandObservable() {
        if (integerChangedCommandObservable == null) {
            final Subject<IntegerChangedCommand> subject = PublishSubject.create();
            commandSubject
                    .filter(command -> command instanceof IntegerChangedCommand)
                    .map(command -> (IntegerChangedCommand) command)
                    .subscribe(subject);
            integerChangedCommandObservable = subject;
        }
        return integerChangedCommandObservable;
    }

    private Observable<ObjectChangedCommand<?>> getObjectChangedCommandObservable() {
        if (objectChangedCommandObservable == null) {
            final Subject<ObjectChangedCommand<?>> subject = PublishSubject.create();
            commandSubject
                    .filter(command -> command instanceof ObjectChangedCommand)
                    .map(command -> (ObjectChangedCommand<?>) command)
                    .subscribe(subject);
            objectChangedCommandObservable = subject;
        }
        return objectChangedCommandObservable;
    }

    private Observable<FireEventCommand<? extends Event>> getFireEventCommandObservable() {
        if (fireEventCommandObservable == null) {
            final Subject<FireEventCommand<? extends Event>> subject = PublishSubject.create();
            commandSubject
                    .filter(command -> command instanceof FireEventCommand)
                    .map(command -> (FireEventCommand<? extends Event>) command)
                    .subscribe(subject);
            fireEventCommandObservable = subject;
        }
        return fireEventCommandObservable;
    }


    public ReadOnlyIntegerProperty createReadOnlyIntegerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        final Observable<IntegerChangedCommand> propertyObervable =
                getIntegerChangedCommandObservable().filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyIntegerProperty(bean, name, propertyObervable);
    }

    public <T> ReadOnlyObjectProperty<T> createReadOnlyObjectProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        final Observable<ObjectChangedCommand<?>> propertyObervable =
                getObjectChangedCommandObservable().filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyObjectProperty<>(bean, name, propertyObervable);
    }


    public <T> ObjectProperty<T> createObjectProperty(Object bean, String name, VChangeListener<T> listener) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(listener, "Listener must not be null");

        final Observable<ObjectChangedCommand<?>> propertyObervable =
                getObjectChangedCommandObservable().filter(command -> name.equals(command.getPropertyName()));
        final BiConsumer<T, T> dispatcher = (oldValue, newValue) -> actionSubject.onNext(listener.onChange(oldValue, newValue));
        return new ReduxFXObjectProperty<>(bean, name, propertyObervable, dispatcher);
    }


    public <EVENT extends Event> ObjectProperty<EventHandler<EVENT>> createEventHandlerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, "Bean must not be null");
        Objects.requireNonNull(name, "Name must not be null");

        final ObjectProperty<EventHandler<EVENT>> property = new SimpleObjectProperty<>(bean, name);
        getFireEventCommandObservable()
                .filter(command -> name.equals(command.getEventName()))
                .forEach(command -> {
                    final EventHandler<EVENT> eventHandler = property.get();
                    if (eventHandler != null) {
                        eventHandler.handle((EVENT) command.getEvent());
                    }
                });

        return property;
    }
}
