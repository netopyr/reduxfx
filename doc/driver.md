ReduxFX and the outside world
=============================

The event cycle at the core of functional reactive UI programming makes handling UI-events strikingly simple. But it has one major drawback: the event cycle by itself is insufficient for most applications. It takes care of everything related to the UI, but almost always a client also needs to communicate with the outside world. It has to store data locally, talk to the server, allow collaboration with other users etc. We need to be able to send and receive events from the outside world.

Several solutions are being tried out, the one implemented in ReduxFX is largely inspired by (older versions of) Elm and redux-loops.

