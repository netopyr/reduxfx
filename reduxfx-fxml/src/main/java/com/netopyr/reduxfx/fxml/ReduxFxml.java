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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is a util to enable the usage of FXML files together with ReduxFX.
 *
 * @param <STATE>
 */
public class ReduxFxml<STATE> implements Selector<STATE>, Dispatcher {

	private Publisher<STATE> statePublisher;

	private final PublishProcessor<Object> actionProcessor = PublishProcessor.create();

	public static <STATE> ReduxFxml<STATE> create() {
		return new ReduxFxml<STATE>();
	}

	public void connect(Processor<Object, STATE> store) {
		connect(store, store);
	}

	public void connect(Publisher<STATE> statePublisher, Subscriber<Object> actionStream) {
		this.statePublisher = statePublisher;

		actionProcessor.subscribe(actionStream);
	}


	@Override
	public void dispatch(Object action) {
		checkConnected();

		actionProcessor.offer(action);
	}


	@Override
	public <VALUE> ObservableValue<VALUE> select(Function<STATE, VALUE> selector) {
		checkConnected();

		ObjectProperty<VALUE> observableValue = new SimpleObjectProperty<>();

		addSubscriber(newState -> {
			VALUE newValue = selector.apply(newState);

			observableValue.setValue(newValue);
		});

		return observableValue;
	}

	@Override
	public <VALUE> ObservableList<VALUE> selectList(Function<STATE, List<VALUE>> selector) {
		checkConnected();

		ObservableList<VALUE> list = FXCollections.observableArrayList();

		addSubscriber(newState -> {
			List<VALUE> newValues = selector.apply(newState);

			list.setAll(newValues);
		});

		return list;
	}

	private void checkConnected() {
		if(statePublisher == null) {
			throw new IllegalStateException("ReduxFxml has not been connected to a ReduxStore yet.");
		}
	}


	protected void addSubscriber(Consumer<STATE> subscriber) {
		statePublisher.subscribe(new Subscriber<STATE>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(STATE state) {
				if (Platform.isFxApplicationThread()) {
					subscriber.accept(state);
				} else {
					Platform.runLater(() -> subscriber.accept(state));
				}
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onComplete() {
			}
		});
	}
}
