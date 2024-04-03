import java.lang.IllegalArgumentException;

public class PoliceDispatchSimulation {

	private boolean[] availableUnits;
	private MinHeap triageQueue;
	private MinHeap eventQueue;

	/**
	 * Create a new simulation.
	 *
	 * @param numUnits The number of units available in this simulation.
	 */
	public PoliceDispatchSimulation(int numUnits) {
		if (numUnits < 1) {
			throw new IllegalArgumentException("There must be at least one unit to dispatch.");
		}
		this.availableUnits = new boolean[numUnits];
		this.triageQueue = new MinHeap();
		this.eventQueue = new MinHeap();
		for (int i = 0; i < numUnits; i++) {
			this.markUnitAvailable(i);
		}
	}

	/**
	 * Add an incident to the incident queue.
	 *
	 * @param incident The incident to add.
	 */
	public void addToIncidentQueue(Incident incident) {
		this.eventQueue.add(
			new HeapElement(incident), 
			incident.getTimePriority()
		);
	}

	/**
	 * Get the next available police unit.
	 *
	 * @return The ID (int) of the next available police unit.
	 */
	private int nextAvailableUnit() {
		for (int i = 0; i < this.availableUnits.length; i++) {
			if (this.availableUnits[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Mark a unit as unavailable (ie. after it has been dispatched)
	 *
	 * @param unit The int ID of the unit.
	 */
	private void markUnitUnavailable(int unit) {
		this.availableUnits[unit] = false;
	}

	/**
	 * Mark a unit as available (ie. after it has resolved an incident)
	 *
	 * @param unit The int ID of the unit.
	 */
	private void markUnitAvailable(int unit) {
		this.availableUnits[unit] = true;
	}

	/**
	 * Print out that an incident has been reported.
	 *
	 * @param reportIncident The Incident describing the report.
	 */
	private void logReport(int time, Incident reportIncident) {
		System.out.println(
			"Time " + time + ": " + reportIncident.getIncidentTypeString() + " reported"
			+ " (duration: " + reportIncident.getDuration() + ")"
		);
	}

	/**
	 * Print out that a unit has been dispatched to an incident.
	 *
	 * @param time The current time.
	 * @param dispatchedIncident The Incident describing the dispatch.
	 */
	private void logDispatch(int time, Incident dispatchIncident) {
		System.out.println(
			"Time " + time + ": unit " + dispatchIncident.getDispatchedUnit() + " dispatched to the " 
			+ dispatchIncident.getIncidentTypeString() + " reported at time " + dispatchIncident.getReportTime() + ";"
			+ " will be resolved at time " + (dispatchIncident.getDispatchTime() + dispatchIncident.getDuration())
		);
	}

	/**
	 * Print out that an incident has been resolved.
	 *
	 * @param time The current time.
	 * @param dispatchedIncident The Incident describing the dispatch.
	 */
	private void logResolution(int time, Incident dispatchIncident) {
		System.out.println(
			"Time " + time + ": unit " + dispatchIncident.getDispatchedUnit() + " resolved the " 
			+ dispatchIncident.getIncidentTypeString() + " reported at time " + dispatchIncident.getReportTime()
		);
	}

	/**
	 * Run the simulation
	 */
	public void run() {
		// FIXME
	}

	public static void main(String[] args) {
		// A test case for the simulation. The correct output is after the code.

		PoliceDispatchSimulation sim = new PoliceDispatchSimulation(4);

		sim.addToIncidentQueue(new Incident(0, IncidentType.WELLNESS_CHECK, 10));
		sim.addToIncidentQueue(new Incident(1, IncidentType.WELLNESS_CHECK, 9));
		sim.addToIncidentQueue(new Incident(2, IncidentType.WELLNESS_CHECK, 8));
		sim.addToIncidentQueue(new Incident(3, IncidentType.WELLNESS_CHECK, 7));

		sim.addToIncidentQueue(new Incident(5, IncidentType.WELLNESS_CHECK, 1));
		sim.addToIncidentQueue(new Incident(5, IncidentType.TRAFFIC_COLLISION, 1));
		sim.addToIncidentQueue(new Incident(5, IncidentType.ROBBERY, 1));
		sim.addToIncidentQueue(new Incident(5, IncidentType.MURDER, 1));

		sim.addToIncidentQueue(new Incident(6, IncidentType.WELLNESS_CHECK, 1));
		sim.addToIncidentQueue(new Incident(6, IncidentType.TRAFFIC_COLLISION, 1));
		sim.addToIncidentQueue(new Incident(6, IncidentType.ROBBERY, 1));
		sim.addToIncidentQueue(new Incident(6, IncidentType.MURDER, 1));

		sim.addToIncidentQueue(new Incident(7, IncidentType.WELLNESS_CHECK, 1));
		sim.addToIncidentQueue(new Incident(7, IncidentType.TRAFFIC_COLLISION, 1));
		sim.addToIncidentQueue(new Incident(7, IncidentType.ROBBERY, 1));
		sim.addToIncidentQueue(new Incident(7, IncidentType.MURDER, 1));

		sim.addToIncidentQueue(new Incident(8, IncidentType.WELLNESS_CHECK, 1));
		sim.addToIncidentQueue(new Incident(8, IncidentType.TRAFFIC_COLLISION, 1));
		sim.addToIncidentQueue(new Incident(8, IncidentType.ROBBERY, 1));
		sim.addToIncidentQueue(new Incident(8, IncidentType.MURDER, 1));

		sim.run();

		/* Correct output (note: order within the same time step does not matter):
		 *
		 * Time 0: wellness check reported (duration: 10)
		 * Time 0: unit 0 dispatched to the wellness check reported at time 0; will be resolved at time 10
		 * Time 1: wellness check reported (duration: 9)
		 * Time 1: unit 1 dispatched to the wellness check reported at time 1; will be resolved at time 10
		 * Time 2: wellness check reported (duration: 8)
		 * Time 2: unit 2 dispatched to the wellness check reported at time 2; will be resolved at time 10
		 * Time 3: wellness check reported (duration: 7)
		 * Time 3: unit 3 dispatched to the wellness check reported at time 3; will be resolved at time 10
		 * Time 5: murder reported (duration: 1)
		 * Time 5: wellness check reported (duration: 1)
		 * Time 5: traffic collision reported (duration: 1)
		 * Time 5: robbery reported (duration: 1)
		 * Time 6: wellness check reported (duration: 1)
		 * Time 6: traffic collision reported (duration: 1)
		 * Time 6: robbery reported (duration: 1)
		 * Time 6: murder reported (duration: 1)
		 * Time 7: robbery reported (duration: 1)
		 * Time 7: murder reported (duration: 1)
		 * Time 7: wellness check reported (duration: 1)
		 * Time 7: traffic collision reported (duration: 1)
		 * Time 8: murder reported (duration: 1)
		 * Time 8: traffic collision reported (duration: 1)
		 * Time 8: wellness check reported (duration: 1)
		 * Time 8: robbery reported (duration: 1)
		 * Time 10: unit 1 resolved the wellness check reported at time 1
		 * Time 10: unit 0 resolved the wellness check reported at time 0
		 * Time 10: unit 2 resolved the wellness check reported at time 2
		 * Time 10: unit 3 resolved the wellness check reported at time 3
		 * Time 10: unit 0 dispatched to the murder reported at time 5; will be resolved at time 11
		 * Time 10: unit 1 dispatched to the murder reported at time 6; will be resolved at time 11
		 * Time 10: unit 2 dispatched to the murder reported at time 7; will be resolved at time 11
		 * Time 10: unit 3 dispatched to the murder reported at time 8; will be resolved at time 11
		 * Time 11: unit 0 resolved the murder reported at time 5
		 * Time 11: unit 3 resolved the murder reported at time 8
		 * Time 11: unit 2 resolved the murder reported at time 7
		 * Time 11: unit 1 resolved the murder reported at time 6
		 * Time 11: unit 0 dispatched to the robbery reported at time 5; will be resolved at time 12
		 * Time 11: unit 1 dispatched to the robbery reported at time 6; will be resolved at time 12
		 * Time 11: unit 2 dispatched to the robbery reported at time 7; will be resolved at time 12
		 * Time 11: unit 3 dispatched to the robbery reported at time 8; will be resolved at time 12
		 * Time 12: unit 0 resolved the robbery reported at time 5
		 * Time 12: unit 3 resolved the robbery reported at time 8
		 * Time 12: unit 2 resolved the robbery reported at time 7
		 * Time 12: unit 1 resolved the robbery reported at time 6
		 * Time 12: unit 0 dispatched to the traffic collision reported at time 5; will be resolved at time 13
		 * Time 12: unit 1 dispatched to the traffic collision reported at time 6; will be resolved at time 13
		 * Time 12: unit 2 dispatched to the traffic collision reported at time 7; will be resolved at time 13
		 * Time 12: unit 3 dispatched to the traffic collision reported at time 8; will be resolved at time 13
		 * Time 13: unit 0 resolved the traffic collision reported at time 5
		 * Time 13: unit 3 resolved the traffic collision reported at time 8
		 * Time 13: unit 2 resolved the traffic collision reported at time 7
		 * Time 13: unit 1 resolved the traffic collision reported at time 6
		 * Time 13: unit 0 dispatched to the wellness check reported at time 5; will be resolved at time 14
		 * Time 13: unit 1 dispatched to the wellness check reported at time 6; will be resolved at time 14
		 * Time 13: unit 2 dispatched to the wellness check reported at time 7; will be resolved at time 14
		 * Time 13: unit 3 dispatched to the wellness check reported at time 8; will be resolved at time 14
		 * Time 14: unit 0 resolved the wellness check reported at time 5
		 * Time 14: unit 3 resolved the wellness check reported at time 8
		 * Time 14: unit 2 resolved the wellness check reported at time 7
		 * Time 14: unit 1 resolved the wellness check reported at time 6
		 */
	}

}
