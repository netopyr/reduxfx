package com.netopyr.reduxfx.fxml;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Function;

/**
 * {@link Selector} is a util to connect JavaFX Properties to the ReduxFX store.
 * This approach is inspired by the
 * <a href="http://redux.js.org/docs/recipes/ComputingDerivedData.html">Selector pattern from the original redux.js</a>
 * and the <a href="https://github.com/angular-redux/store#in-depth-usage">Angular redux project</a>.
 * <p>
 * You define a "selector function" which is a {@link Function} that takes the current state as argument and returns
 * a value from the state. For example, if your state contains a list of some items you could define the following
 * selector function that returns the number of items:
 * <p>
 * <pre>
 * class AppState {
 * 	private List{@code<String>} items = new ArrayList{@code<>}();
 *
 * 	public List{@code<String>} getItems() {
 *		return Collections.unmodifieableList(items);
 * 	}
 * 	...
 * }
 *
 *
 * Function{@code<AppState, Integer>} selectCount = appState -&gt; appState.getItems().size();
 * </pre>
 * <p>
 * You can now use this selector function to create a JavaFx {@link ObservableValue}:
 *
 * <pre>
 * Selector selector = ...
 *
 * ObservableValue{@code<Integer>} count = selector.select(selectCount);
 *
 * </pre>
 * <p>
 * If the state changes because of dispatched actions this JavaFX Observable will get the new value based on the selector function.
 *
 * This way you can use the selector to connect your JavaFX Controls to the state of ReduxFX.
 * <p>
 *
 * To get an instance of the {@link Selector} you will typically use a dependency injection library that can inject
 * an instance into your Controller class. The dependency injection library has to use the {@link ReduxFxml} instance
 * in this case. {@link ReduxFxml} implements this interface and connects it to the ReduxFX store.
 *
 * @param <S> the state
 */
public interface Selector<S> {

	/**
	 * Create an {@link ObservableValue} that will get the value returned by the given
	 * selector function from the current ReduxFX state.
	 * When the state updates, this observable value will also get updated.
	 *
	 * @param selector a function that returns a slice of data from the ReduxFX state.
	 * @param <V> the generic type of the value that is returned by the selector function
	 * @return an observable value
	 */
	<V> ObservableValue<V> select(Function<S, V> selector);

	/**
	 * Create an {@link ObservableList} that will get the list of values returned by the
	 * given selector function from the current ReduxFX state.
	 * When the state updates, this observable list will also get updated.
	 *
	 * @param selector a function that returns a slice of data from the ReduxFX state.
	 * @param <V> the generic type of the value that is returned by the selector function
	 * @return an observable list
	 */
	<V> ObservableList<V> selectList(Function<S, List<V>> selector);

}
