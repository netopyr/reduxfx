Creating UI-components with ReduxFX
===================================

Most (if not all) real-world applications require one or more custom components and ReduxFX-applications are no exception.
Using a custom component is straightforward and it is also possible to create custom components with ReduxFX.

The Basic Concept
-----------------
A custom component implemented with ReduxFX is somewhat like a small ReduxFX application with its own event cycle.
It has a view()-method, ActionCreators, and an Updater.
In addition a custom component offers a regular JavaFX interface, which is used to communicate to the outside world.
The following picture shows the architecture of a custom component implemented with ReduxFX.
This may sound like a lot of work, but most of the boiler-plate is already conveniently provided in the class [ComponentBase][class componentbase].

![alt Overview ReduxFX component][reduxfx component]

As you can see the heart of a ReduxFX component is a regular event cycle.
But in addition we have a class that implements the actual JavaFX component.
The JavaFX Node of the component is set and updated by ReduxFX.
The interaction with the actual interface, i.e. the properties and events of the component, is done via a component driver.

Setting up a Custom Component
-----------------------------
The first step required to create a custom component with ReduxFX is usually to define its interface.
In the example ColorChooserApp, a simple component is defined that can be used to choose a color.
The JavaFX component is implemented in the class [ColorChooserComponent][class colorchoosercomponent].
In the constructor an instance of the class [ComponentBase][class componentbase] is created.
This class is similar to the class [ReduxFX][class reduxfx].
One has to pass in the initial state, the view()-function, and the Updater and it will set up the event cycle.
In addition it contains factory-methods to create JavaFX properties and events.

Using JavaFX properties and events
----------------------------------
The JavaFX properties of a UI-component implemented with ReduxFX have to be created using the appropriate methods in [ComponentBase][class componentbase].
This will ensure, that the properties interact with the ReduxFX event cycle of the component automatically.
For instance one can provide a [VChangeListener][class vchangelistener] while creating a property and everytime the value of that property changes, the VChangeListener is called.
The VChangeListener creates an Action, which is sent to the Updater of the component.
This way it is possible to react to property value changes in the ReduxFX style.

To change the value of a property, a Command has to be sent.
For each type, a specific change-Command is defined, e.g. for ints there is an [IntegerChangedCommand][class integerchangedcommand].

To fire an event, a [FireEventCommand][class fireeventcommand] needs to be created within the Updater.
The [ColorChooserUpdater][class colorchooserupdater] shows an example of how events and property changes are initiated within the Updater.
 
There is a separate article about how Commands can be used within ReduxFX to communicate with the outside world.
 
> In theory it would also be possible to create methods in the JavaFX class and dispatch Actions to the ComponentBase.
> But methods contradicts the whole idea of functional reactive programming and it would not be possible to use these methods from a ReduxFX application.
> Therefore we advise against it.

Further Reading
---------------
[ReduxFX and the outside world][article driver]


[class componentbase]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/component/ComponentBase.java
[reduxfx component]: component.jpg
[class colorchoosercomponent]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/colorchooser/component/ColorChooserComponent.java
[class reduxfx]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/ReduxFX.java
[class vchangelistener]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/vscenegraph/property/VChangeListener.java
[class integerchangedcommand]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/component/command/IntegerChangedCommand.java
[class fireeventcommand]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/component/command/FireEventCommand.java
[class colorchooserupdater]: https://github.com/netopyr/reduxfx/tree/master/samples/src/main/java/com/netopyr/reduxfx/colorchooser/component/updater/ColorChooserUpdater.java
[article driver]: driver.md