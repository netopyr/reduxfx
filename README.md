ReduxFX
=======

[Redux]("Redux.js GitHub Page")-Architecture for JavaFX

ReduxFX is an experiment to apply patterns from funtional reactive UI programming to JavaFX. Modern frameworks like Redux for [React.js], [Elm], and [Cycle.js] introduced a new approach to building user interfaces. This approach has many [advantages](#Advantages) and it is worth exploring how it can be applied to JavaFX.

If you are familiar with functional reactive UI programming and already bought into it, you can jump straight to the [TodoMVC JavaFX example](#TodoMVC JavaFX Example).

Functional Reactive UI Programming
----------------------------------
On the face of it, frameworks like React.js with the Redux-architecture, Elm, and Cycle.js seem quite different. But under the hood, all of them share a simple idea, which is sketched in the following picture:

![alt text][fruip cycle]

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

### State

### View-Function

### Action Creators

### Updater

[redux]: https://github.com/reactjs/redux/
[react.js]: https://facebook.github.io/react/
[elm]: http://elm-lang.org
[cycle.js]: https://cycle.js.org
[fruip cycle]: doc/frp_cycle.jpg