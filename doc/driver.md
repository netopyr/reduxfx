ReduxFX and the outside world
=============================

The event cycle at the core of functional reactive UI programming makes handling UI-events strikingly simple. 
But it has one major drawback: the event cycle by itself is insufficient for most applications. 
It takes care of everything related to the UI, but almost always a client also needs to communicate with the outside world. 
It has to store data locally, talk to the server, allow collaboration with other users etc. 
We need to be able to send and receive events from the outside world.

Several solutions are currently being tried out in the community.
The one implemented in ReduxFX is largely inspired by (older versions of) Elm and redux-loops.
It has the advantage, that the benefits of the Redux-architecture (e.g. the simple flow of events and testability) are maintained, while the logic is still maintained in one place.

Basic Concept
-------------
An overview of the concept can be seen in the following picture.

![alt Overview driver communication][driver]


The basic idea is, that the updater not only calculates the new state of the application upon receiving an action, but it can also produce one or more commands, which are sent to drivers.
For every outside component, that we need to communicate with, exists a driver that takes the command and calls the required action on the component.
If the outside component needs to communicate with the ReduxFX application, it goes through the driver, too.
The driver generates regular Actions, which are dispatched to the Updater.

For example, if we want to load data from a server, the Updater creates a load-command, which contains the required parameters and is sent to the driver.
Upon receival of the load-command, the driver sends a HTTP-GET to the server.
Once the response comes back, the driver generates an Action with the new data, which is dispatched to the Updater.
The Updater integrates the new data into the state of the application.

Testing
-------
Testing an Updater that can communicate with drivers is not that much different from testing a simple Updater.
It is still a pure function, i.e. its output depends only on the input and there are no side-effects.
The only difference is, that it produces not only a new state upon each call, but optionally also one or more commands.
These commands are just data, which can easily be checked.

Flow of events
--------------
The flow of events is still simple, though we now have two (or more) sources for actions.
Also the output of the Updater goes into several directions.
But after leaving the Updater, the event flow forms another cycle again, going to the driver and then coming back to the Updater.

> Arguably the whole ReduxFX architecture can be viewed as just one of many cycles, where the view()-method, the DOM-Driver and the ActionCreator together form a driver that reacts on state-change commands.
> Event though this is an intriguing idea, we do not follow it any further, because the view is the central part of the application and deserves to be treated special.

[driver]: driver.jpg