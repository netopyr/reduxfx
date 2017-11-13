package com.netopyr.reduxfx.component.impl;

import com.netopyr.reduxfx.component.ComponentBase;
import com.netopyr.reduxfx.component.command.FireEventCommand;
import com.netopyr.reduxfx.component.command.IntegerChangedCommand;
import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import com.netopyr.reduxfx.store.Driver;
import com.netopyr.reduxfx.updater.Command;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Objects;
import java.util.function.BiConsumer;

public class ComponentDriver implements Driver {

    private static final String BEAN_MUST_NOT_BE_NULL = "Bean must not be null";
    private static final String NAME_MUST_NOT_BE_NULL = "Name must not be null";

    private final FlowableProcessor<Command> commandProcessor = PublishProcessor.create();
    private final FlowableProcessor<Object> actionProcessor = PublishProcessor.create();

    private Flowable<FireEventCommand<? extends Event>> fireEventCommandFlowable;
    private Flowable<IntegerChangedCommand> integerChangedCommandFlowable;
    private Flowable<ObjectChangedCommand<?>> objectChangedCommandFlowable;

    private FlowableEmitter<Command> commandEmitter;

    public ComponentDriver() {
        final Flowable<Command> commandInBox = Flowable.create(emitter -> commandEmitter = emitter, BackpressureStrategy.BUFFER);
        commandInBox.subscribe(commandProcessor);
    }

    @Override
    public void dispatch(Command command) {
        commandEmitter.onNext(command);
    }

    @Override
    public void subscribe(Subscriber<? super Object> subscriber) {
        actionProcessor.subscribe(subscriber);
    }


    private Flowable<IntegerChangedCommand> getIntegerChangedCommandFlowable() {
        if (integerChangedCommandFlowable == null) {
            final FlowableProcessor<IntegerChangedCommand> processor = PublishProcessor.create();
            commandProcessor
                    .filter(command -> command instanceof IntegerChangedCommand)
                    .map(command -> (IntegerChangedCommand) command)
                    .subscribe(processor);
            integerChangedCommandFlowable = processor;
        }
        return integerChangedCommandFlowable;
    }

    private Flowable<ObjectChangedCommand<?>> getObjectChangedCommandFlowable() {
        if (objectChangedCommandFlowable == null) {
            final FlowableProcessor<ObjectChangedCommand<?>> processor = PublishProcessor.create();
            commandProcessor
                    .filter(command -> command instanceof ObjectChangedCommand)
                    .map(command -> (ObjectChangedCommand<?>) command)
                    .subscribe(processor);
            objectChangedCommandFlowable = processor;
        }
        return objectChangedCommandFlowable;
    }

    private Flowable<FireEventCommand<? extends Event>> getFireEventCommandFlowable() {
        if (fireEventCommandFlowable == null) {
            final FlowableProcessor<FireEventCommand<? extends Event>> processor = PublishProcessor.create();
            commandProcessor
                    .filter(command -> command instanceof FireEventCommand)
                    .map(command -> (FireEventCommand<? extends Event>) command)
                    .subscribe(processor);
            fireEventCommandFlowable = processor;
        }
        return fireEventCommandFlowable;
    }


    public ReadOnlyIntegerProperty createReadOnlyIntegerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, BEAN_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);

        final Publisher<IntegerChangedCommand> propertyPublisher =
                getIntegerChangedCommandFlowable().filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyIntegerProperty(bean, name, propertyPublisher);
    }

    public <T> ReadOnlyObjectProperty<T> createReadOnlyObjectProperty(Object bean, String name) {
        Objects.requireNonNull(bean, BEAN_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);

        final Publisher<ObjectChangedCommand<?>> propertyObervable =
                getObjectChangedCommandFlowable().filter(command -> name.equals(command.getPropertyName()));
        return new ReduxFXReadOnlyObjectProperty<>(bean, name, propertyObervable);
    }


    public <T> ObjectProperty<T> createObjectProperty(Object bean, String name, ComponentBase.ChangeListener<T> listener) {
        Objects.requireNonNull(bean, BEAN_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);
        Objects.requireNonNull(listener, "Listener must not be null");

        final Publisher<ObjectChangedCommand<?>> propertyObervable =
                getObjectChangedCommandFlowable().filter(command -> name.equals(command.getPropertyName()));
        final BiConsumer<T, T> dispatcher = (oldValue, newValue) -> actionProcessor.onNext(listener.onChange(oldValue, newValue));
        return new ReduxFXObjectProperty<>(bean, name, propertyObervable, dispatcher);
    }


    @SuppressWarnings("unchecked")
    public <E extends Event> ObjectProperty<EventHandler<E>> createEventHandlerProperty(Object bean, String name) {
        Objects.requireNonNull(bean, BEAN_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);

        final ObjectProperty<EventHandler<E>> property = new SimpleObjectProperty<>(bean, name);
        getFireEventCommandFlowable()
                .filter(command -> name.equals(command.getEventName()))
                .forEach(command -> {
                    final EventHandler<E> eventHandler = property.get();
                    if (eventHandler != null) {
                        eventHandler.handle((E) command.getEvent());
                    }
                });

        return property;
    }
}
