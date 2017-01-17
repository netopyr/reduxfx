ReduxFX
=======

[Redux][]-Architecture for JavaFX

ReduxFX is an experiment to apply patterns from functional reactive UI programming to JavaFX. Modern frameworks like Redux for [React.js][], [Elm][], and [Cycle.js][] introduced a new approach to building user interfaces. This approach has many [advantages](#advantages) and it is worth exploring how it can be applied to JavaFX.

The following section is a brief introduction of the core concepts of functional reactive UI programming taken from my article ["MVC is dead - what comes next?"][mvc is dead] If you are familiar with functional reactive UI programming and already bought into it, you can jump straight to the [TodoMVC JavaFX example](#todomvc-javafx-example).

Functional Reactive UI Programming
----------------------------------
On the face of it, frameworks like React.js with the Redux-architecture, Elm, and Cycle.js seem quite different. But under the hood, all of them share a simple idea, which is sketched in the following picture:

![alt Overview functional reactive UI programming][fruip cycle]

The first thing to note when looking at the picture is that everything – all changes, events, and updates – flow in a single direction to form a cycle. The cycle consists of four data-structures (State, Virtual DOM, Event, and Action) and four components (View()-Function, DOM-Driver, ActionCreator, and Updater). The DOM-Driver is provided by the framework, while the other components have to be implemented by the application developer.

Let’s assume our application, a todo-list, is already running for a while and the user presses a button to create a new entry in the todo-list. This will result in a button-clicked event in the DOM, which is captured by the DOM-Driver and forwarded to one of our ActionCreators.

The ActionCreator takes the DOM-event and maps it to an action. Actions are an implementation of the Command Pattern, i.e. they describe what should be done, but do not modify anything themselves. In our example, we create an AddToDoItemAction and pass it to the Updater.

The Updater contains the application logic. It keeps a reference to the current state of the application. Every time it receives an action from one of the ActionCreators, it generates the new state. In our example, if the current state contains three todo-items and we receive the AddToDoItemAction, the Updater will create a new state that contains the existing todo-items plus a new one.

The state is passed to the View()-Function, which generates the so-called Virtual DOM. As the name suggests, the Virtual DOM is not the real DOM, but it is a data-structure that only describes the DOM.

The Virtual DOM is passed to the DOM-Driver which will update the DOM and wait for the next user input. With this, the cycle ends.

Advantages
----------
Functional reactive UI Programming has three major advantages over traditional approaches, all of them are huge: straightforward testing, a comprehensive flow of events, and time travels (yes, seriously).

### Straightforward testing
The View()-Function and the ActionCreators are simple mappings, while the Updater performs a fold (also often called a reduce) on the Actions it receives.

> All components are pure functions and pure functions are extremely easy to test.

The outcome of a pure function depends only on the input parameters and they do not have any side effects. To test a pure function, it is sufficient to create the input parameter, run the “function under test” and compare the outcome. No mockups, no dependency injection, no complex setup, and no other techniques are necessary that take the fun out of testing.

### Comprehensive Flow of Events
Reactive programming is a lot of fun – except when it is not. The control flow of graphical user interfaces is inherently event-based. An application has to react to button-clicks, keyboard input, and other events from users, sensors, and servers. Applying reactive techniques, be it the Observer Pattern, data-bindings, or reactive streams, comes naturally.

Unfortunately, these techniques come with a price. If a component A calls a component B, it is simple to see the connection in your IDE or debugger. But if both components are connected via events, the relationship is not as obvious. The larger the application becomes, the harder it gets to understand its internals.

The architecture of a functional reactive application avoids these problems by defining a simple flow of events that all components must follow.

> No matter how large your application grows, the flow of events will never change.

### Time Travel
Functional reactive applications allow you to travel back and forth in time – at least in the context of your application. If we store the initial state and all actions, we can use a technique called “Event Sourcing”. By replaying the actions, we can recalculate every state the application was in. If we replay only the last n-1, n-2, n-3… actions, we can actually step back in time. And by modifying the recorded stream of actions while applying them, we can even change the past. As you can imagine this can be very handy during development and bugfixing.

> The first time-traveling debuggers have been built, but I think we have only started to understand the possibilities, and more amazing tools will be released in the near future.

TodoMVC JavaFX Example
----------------------
All data structures in the TodoMVC JavaFX Example are immutable. Unfortunately the standard collections in the JDK are problematic, if you want to enforce immutability. Therefore in the example makes heavy use of [Javaslang][javaslang] and its truly immutable collections.

### State
The package [com.netopyr.reduxfx.todo.state][package state] contains all classes related to the state of the TodoMVC JavaFX Example. The main class is AppModel.

```java
public final class AppModel {

    private final String newTodoText;
    private final Seq<TodoEntry> todos;
    private final Filter filter;
    
    // constructor, getters, and toString()
    // ...

}
```

The property todos is of type Seq, which is part of Javaslang. Seq is similar to Iterable in the JDK, because it is the supertype of all sequential data structures in Javaslang, though Seq offers much more functionality. In other words todos is a list of TodoEntries.

The property newTodoText contains the text stored in the TextField for new todo-entries. Last but not least, the property filter stores the filter which is currently set (ALL, ACTIVE or COMPLETED).

```java
public final class TodoEntry {

    private final int id;
    private final String text;
    private final boolean completed;
    private final boolean hover;
    private final boolean editMode;
    
    // constructor, getters, and toString()
    // ...

}    
```

A TodoEntry has five properties, which store the id, the text describing the todo-item, a flag to signal if it is completed, and two flags that define if the user is currently hovering over the entry and if the todo-item is in edit mode.

### View-Function

The View-Function kept in the package [com.netopyr.reduxfx.todo.view][package view] defines the view of the TodoMVC JavaFX example. This is the least stable part of ReduxFX and you can expect significant changes in the future in this area.

The view in a functional reactive UI application is defined as a mapping from the application state (AppModel in this example) to the corresponding VirtualScenegraph. The VirtualScenegraph is an immutable data structure that defines what the real JavaFX should look like.

The current API of ReduxFX is an attempt to combine the readability of a template-like approach with the expressiveness of a functional approach. The entry point is the class MainView.

```java
public class MainView {

    private MainView() {}

    public static VNode<Action> view(AppModel state) {
        return
            VBox(
                alignment(Pos.CENTER),
                minWidth(Region.USE_PREF_SIZE),
                minHeight(Region.USE_PREF_SIZE),
                maxWidth(Region.USE_PREF_SIZE),
                maxHeight(Region.USE_PREF_SIZE),
                stylesheets("main.css"),

                Label(
                        id("title"),
                        text("todos")
                ),

                AddItemView(state),
                ItemOverviewView(state),
                ControlsView(state)
            );
    }
}
```

There is just one method view(), which takes an AppModel and returns a VNode, the root node of the VirtualScenegraph. Within the view()-method three kind of methods to define the VirtualScenegraph are used. VBox() and Label() define regular JavaFX Nodes. The methods alignment(), stylesheets(), minWidth() etc. set properties of these nodes. Last but not least there are AddItemView(), ItemOverviewView(), and ControlsView() which specify custom components of the application. The most interesting custom component is probably ItemOverviewView.

```java
class ItemOverviewView {

    static VNode<Action> ItemOverviewView(AppModel state) {
        return AnchorPane(
            minWidth(Region.USE_PREF_SIZE),
            // more properties
            // ...
            ListView(
                // more properties
                // ...
                items(state.getTodos()
                    .filter(todoEntry -> {
                        switch (state.getFilter()) {
                            case COMPLETED:
                                return todoEntry.isCompleted();
                            case ACTIVE:
                                return !todoEntry.isCompleted();
                            default:
                                return true;
                        }
                    })
                ),
                cellFactory(todoEntry -> ItemView((TodoEntry) todoEntry))
            )
        );
    }
}
```

The method ItemOverviewView() is very similar to the MainView-method. It takes the current application state AppModel and returns the root node of that part of the VirtualScenegraph that specifies the list of items. The interesting part is the definition of a ListView. There are two main properties items and cellFactory.

The property items contains the list of todo-items that should be shown. Depending on the filter setting, we want to show all items or filter either the completed or active entries. As you can see, using a declarative approach makes it pretty simple to define such a filtered list.

With the cellFactory, we can setup a mapping between an entry in the items-list and the VirtualScenegraph. In the example we want to show an ItemView for each entry, which is another custom component.

#### Some Thoughts for the Future

1. In Redux-applications we usually split components into presentational components and container components. Does it make sense to do the same here?
2. Can we set properties of our components similar to JavaFX components instead of passing the state?
3. Can we limit the allowed properties to only those that are defined for a JavaFX component? For example the compiler should warn us, if we try to use text() within VBox().
4. How can we apply memoization transparently? Large parts of the AppModel do not change and the corresponding parts of the VirtualScenegraph should be reused, too.

### Action Creators

Action Creators map UI-events to application specific actions. The package [com.netopyr.reduxfx.todo.actions][package actions] contains everything related to actions.

All Actions in this example implement the interface Action.

```java
public interface Action {

    enum ActionType {
        ADD_TODO,
        NEW_TEXTFIELD_CHANGED,
        DELETE_TODO,
        EDIT_TODO,
        COMPLETE_TODO,
        COMPLETE_ALL,
        CLEAR_COMPLETED,
        SET_FILTER,
        SET_EDIT_MODE,
        SET_TODO_HOVER
    }

    ActionType getType();
}
```

The interface Action contains a single method getType(). It returns an ActionType, which is an enum of all possible kind of actions. This makes it easier to find the appropriate action later in the updater.

Each actions requires two parts, a builder method and an implementation. Both are defined in the Actions class. The following code snippet shows the builder method and implementation of the DeleteTodoAction.

```java
public final class Actions {

    private Actions() {}
    
    public static Action deleteTodo(int id) {
        return new DeleteTodo(id);
    }
    
    public static final class DeleteTodo implements Action {

        private final int id;

        public ActionType getType() {
            return ActionType.DELETE_TODO;
        }

        // constructor, getter, and toString()
        // ...
    }

    // more builder methods and Action-classes
    // ...
}
```

DeleteTodo is immutable and contains a single property id, which references the todo-entry that needs to be deleted. It also implements the method getType() and returns the appropriate type from the enum ActionType. Adding the builder method helps to hide the actual Actions implementation from the event-handlers.

### Updater

The heart of every ReduxFX-application is the updater. It is a BiFunction, which takes the old state and an action and calculates the new state. In this application, it is defined in the class [Updater][class updater].

Usually the updater is implemented as a huge switch-case statement with one branch per action-type. Below you can see an excerpt from the updater of the example application. It shows the cases of the actions NewTextfieldChanged and AddTodo.

The action NewTextFieldChanged is triggered when the text in the textfield above the todo-list is changed. The action has a single property text which contains the new value. A new AppModel is created with the updated value for newTodoText, while the todo-entries and filter are copied from the old state. This is a typical implementation of the updater. Only a small section is changed, while most parts are just copied.

```java
switch (action.getType()) {
    case NEW_TEXTFIELD_CHANGED:
        newState = new AppModel(
                ((NewTextFieldChanged) action).getText(),
                oldState.getTodos(),
                oldState.getFilter()
        );
        break;

    case ADD_TODO:
        newState = new AppModel(
                "",
                oldState.getTodos().append(
                        new TodoEntry(
                                oldState.getTodos()
                                        .map(TodoEntry::getId)
                                        .max()
                                        .getOrElse(-1) + 1,
                                oldState.getNewTodoText(),
                                false,
                                false,
                                false
                        )
                ),
                oldState.getFilter()
        );
        break;
        
    // more branches
    // ...
```

The AddTodo-action is slightly more complex. We define the new state, for which we clear the newTodoText, i.e. we clear the Textfield. For the todo-entries we take the old list and add a new TodoEntry. The collection returned from oldState.getTodos() is a Javaslang collection. Calling append() does not modify the original list, but returns the new list.

The id of the new TodoEntry is the current maximum id + 1. The text is taken from newTodoText of the old state. (The newTodoText of the new state is cleared, while the old state still points to the current value.) All flags are set to false initially. The filter is again just copied from the old state.

### Launcher

The launcher [TodoMVC][class todomvc] is the last part of the example. As usual in JavaFX, we extend the Application class. To start a ReduxFX application, we need to define the initial state. In our case it has an empty newTodoText, zero todo-items, and the filter is set to show all entries. Next we need to call ReduxFX.start with the initial state, the updater-function, the view-function, and the stage where the SceneGraph should be shown.

```
public class Launcher extends Application {

    public void start(Stage primaryStage) throws Exception {

        final AppModel initialState = new AppModel("", Array.empty(), Filter.ALL);

        ReduxFX.start(initialState, Todos::update, MainView::view, primaryStage);

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### Summary

The experiment was successful. Not only did it show that one is able to write a JavaFX application with a functional reactive approach, but it even turned out to be quite a lot of fun. 

There are certainly still areas that need improvement. Sometimes Java's verbosity is annoying. On the other hand being able to define truly immutable data structures and to take advantage of static types makes some parts even more suitable for this approach than a JavaScript implementation.

If you have any comments, suggestions or questions, please [create a new issue][issue tracking]. If you want to help spread the word, I am certainly also happy about any tweet, post or other mentioning of ReduxFX.

### Further Reading

[ReduxFX and the outside world][article driver]

[redux]: https://github.com/reactjs/redux/
[react.js]: https://facebook.github.io/react/
[elm]: http://elm-lang.org
[cycle.js]: https://cycle.js.org
[mvc is dead]: https://blog.netopyr.com/2016/10/11/mvc-dead-comes-next/
[fruip cycle]: doc/frp_cycle.jpg
[javaslang]: http://www.javaslang.io/
[package state]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/todo/state
[package view]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/todo/view
[package actions]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/todo/actions
[class updater]: https://github.com/netopyr/reduxfx/blob/master/samples/src/main/java/com/netopyr/reduxfx/todo/updater/Updater.java
[class todomvc]: https://github.com/netopyr/reduxfx/blob/master/samples/src/main/java/com/netopyr/reduxfx/todo/TodoMVC.java
[issue tracking]: https://github.com/netopyr/reduxfx/issues
[article driver]: doc/driver.md