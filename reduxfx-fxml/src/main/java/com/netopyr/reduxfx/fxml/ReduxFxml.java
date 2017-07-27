package com.netopyr.reduxfx.fxml;

import io.reactivex.processors.PublishProcessor;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is a util to enable the usage of FXML files together with ReduxFX.
 *
 * @param <S> the state
 */
public class ReduxFxml<S> implements Selector<S>, Dispatcher {

	private static final Logger LOG = LoggerFactory.getLogger(ReduxFxml.class);

	private Publisher<S> statePublisher;

	private final PublishProcessor<Object> actionProcessor = PublishProcessor.create();

	public static <S> ReduxFxml<S> create() {
		return new ReduxFxml<>();
	}

	public void connect(Processor<Object, S> store) {
		connect(store, store);
	}

	public void connect(Publisher<S> statePublisher, Subscriber<Object> actionStream) {
		this.statePublisher = statePublisher;

		actionProcessor.subscribe(actionStream);
	}


	@Override
	public void dispatch(Object action) {
		checkConnected();

		actionProcessor.offer(action);
	}


	@Override
	public <V> ObservableValue<V> select(Function<S, V> selector) {
		checkConnected();

		ObjectProperty<V> observableValue = new SimpleObjectProperty<>();

		addSubscriber(newState -> {
			V newValue = selector.apply(newState);

			observableValue.setValue(newValue);
		});

		return observableValue;
	}

	@Override
	public <V> ObservableList<V> selectList(Function<S, List<V>> selector) {
		checkConnected();

		ObservableList<V> list = FXCollections.observableArrayList();

		addSubscriber(newState -> {
			List<V> newValues = selector.apply(newState);

			list.setAll(newValues);
		});

		return list;
	}

	private void checkConnected() {
		if(statePublisher == null) {
			throw new IllegalStateException("ReduxFxml has not been connected to a ReduxStore yet.");
		}
	}


	protected void addSubscriber(Consumer<S> subscriber) {
		statePublisher.subscribe(new Subscriber<S>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(S state) {
				if (Platform.isFxApplicationThread()) {
					subscriber.accept(state);
				} else {
					Platform.runLater(() -> subscriber.accept(state));
				}
			}

			@Override
			public void onError(Throwable t) {
				LOG.error("Exception in state-stream", t);
			}

			@Override
			public void onComplete() {
				LOG.error("The state-stream was completed. This should not happen");
			}
		});
	}
}
