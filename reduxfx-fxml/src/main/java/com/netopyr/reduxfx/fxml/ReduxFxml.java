package com.netopyr.reduxfx.fxml;

import com.netopyr.reduxfx.store.ReduxFXStore;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	private ReduxFXStore<S> store;

	private ReduxFxml(ReduxFXStore<S> store) {
		this.store = store;
	}

	public static <S> ReduxFxml<S> create(ReduxFXStore<S> store) {
		return new ReduxFxml<>(store);
	}


	@Override
	public void dispatch(Object action) {
		store.dispatch(action);
	}


	@Override
	public <V> ObservableValue<V> select(Function<S, V> selector) {
		ObjectProperty<V> observableValue = new SimpleObjectProperty<>();

		addSubscriber(newState -> {
			V newValue = selector.apply(newState);

			observableValue.setValue(newValue);
		});

		return observableValue;
	}

	@Override
	public <V> ObservableList<V> selectList(Function<S, List<V>> selector) {
		ObservableList<V> list = FXCollections.observableArrayList();

		addSubscriber(newState -> {
			List<V> newValues = selector.apply(newState);

			list.setAll(newValues);
		});

		return list;
	}


	protected void addSubscriber(Consumer<S> subscriber) {
		store.subscribe(new Subscriber<S>() {

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
