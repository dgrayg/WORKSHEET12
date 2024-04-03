This project asks you to use the priority queue you wrote in a *discrete event simulation*. The simulation models a 911 dispatch in a small police department. During the simulation, `Incident`s will be reported to 911 and police units will be dispatched to deal with those events. What `Incident`s to deal with depends on their priority, which in turn depends on their type: murders take priority over robberies, which take priority over traffic collisions, which take priority over wellness checks.

Your min-heap will be used in two different ways for this simulation:

* One priority queue will be used to keep track of what events will occur next in the simulation, such as the next incident to be reported, or the next incident to be resolved. If a robbery is reported at time 3, and nothing else happens until time 1,000,000,003, there is no point looping through the timesteps inbetween. Instead, a priority queue can be used to store the times of events, so the simulation can "jump" to the next timestep when something happens.

* A second priority queue will be used for the police to determine what incident to respond to next. Since there are only a limited number of police units, they are unable to respond to all incidents immediately; instead, the incidents must be *triaged* and dealt with based on type.

For example, if a two-unit police department has to deal with the following reports, with the estimated duration it will take to resolve them:

* Time 0: wellness check reported (duration: 10)
* Time 3: wellness check reported (duration: 2)
* Time 4: wellness check reported (duration: 3)
* Time 5: robbery reported (duration: 5)

The following will happen in the simulation:

```
Time 0: wellness check reported (duration 10)
Time 0: unit 0 dispatched to the wellness check from time 0; will be resolved at time 10
Time 3: wellness check reported (duration 2)
Time 3: unit 1 dispatched to the wellness check from time 3; will be resolved at time 5
Time 4: wellness check reported (duration 3)
Time 5: robbery reported (duration 5)
Time 5: unit 1 resolved the wellness check from time 3
Time 5: unit 1 dispatched to the robbery from time 5; will be resolved at time 10
Time 10: unit 1 resolved the robbery from time 5
Time 10: unit 0 resolved the wellness check from time 0
Time 10: unit 0 dispatched to the wellness check from time 4; will be resolved at time 13
Time 13: unit 0 resolved the wellness check from time 4
```

Note that when the wellness check and the robbery were reported at time 4 and time 5 respectively, both police units are busy, so the incidents are left in the triage queue. When unit 1 resolves the wellness check at time 5, the waiting robbery takes priority over the wellness check, and so unit 1 is dispatched to the robbery. Both unit 0 and unit 1 resolve their incidents at time 10, when a wellness check remains in the triage queue. The unit with the lowest ID - in this case unit 0 - is then dispatched, and finally resolving all incidents at time 13.

__Make sure you understand the logic behind the sequence of events in this example before starting on code.__ A more complicated example can be found in the `main()` of `PoliceDispatchSimulation.java`.

<div style="page-break-after:always;"></div>

You will only need to edit the `run()` method of `PoliceDispatchSimulation.java`, which runs the simulation. Using the event queue, the simulation runs until no more events are remaining. At each step:

1. All events with the same timestep are polled and dealt with.
    
    * If an incident is reported, it is added to the triage queue.

    * If a unit has resolved an incident, the unit is marked as available.

2. Incidents in the triage queue are dispatched as long as there are units available. The unit with the lowest number will always be dispatched first. To indicate to the simulation that a report will be resolved in the future, you can use the `dispatch` method of `Incident` add additional information on which unit was dispatched and when it was dispatched. This `Incident` can then be added to the event queue.

To help you print the exact output as necessary, three private logging functions `logReport`,  `logDispatch`, and `logResolution` have been provided for you. They should be called when an incident is reported, dispatched, and resolved, respectively. The print order within the same time step does not matter.

-----

You have three additional files which you do not have to modify. `IncidentType.java` represents different types of incidents - wellness check, traffic collision, robbery, and murder. It is not actually a class, but an `enum`, which gives unique symbols to each possibility. (They're just `int`s underneath.) `Incident.java` contains the `Incident` class, which represents the details of an incident. This class is used to represent both reports to 911, as well as the dispatch of a police unit. You may find it useful to familiarize yourself with the methods of `Incident.java` by readings its [javadoc](/javadoc)

Finally, there is the `HeapElement` class which serves as a wrapper around the `Incident` for your min-heap.

-----

Hint: It is important for you to mentally separate an "event" from an "incident". An incident is something that happens in the simulated world, such as a burglary, and the police must then deal with them. Events, on the other hand, only exists for the simulation. For example, we might know that a burglary will be reported at time 10, even though the simulated world is only at time 2. Importantly, both of these are represented by instances of `Incident`, but should be treated differently.
