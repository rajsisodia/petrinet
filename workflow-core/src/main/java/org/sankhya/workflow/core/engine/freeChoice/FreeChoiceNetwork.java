/**
 * 
 */
package org.sankhya.workflow.core.engine.freeChoice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Transition;
import org.sankhya.workflow.core.engine.petrinet.Network;

/**
 * A free choice network is modeled as a bipartite graph, consisting of
 * {@link Place Places} and {@link Transition Transitions}. Being such, edges or
 * arcs are allowed only to connect between a Place and a Transition. Two Places
 * or Transitions cannot be connected directly.
 * <p>
 * Another restriction on a free choice network is, if there is an arc from
 * {@link Place place} <b><i>P</i></b> to a {@link Transition transition}
 * <b><i>T</i></b>, then either <b><i>T</i></b> is the only output Transition of
 * <b><i>P</i></b> or <b><i>P</i></b> is the only input Place for
 * <b><i>T</i></b>.
 * 
 * @author Raj Singh Sisodia
 * @since Apr 17, 2017
 *
 */
public class FreeChoiceNetwork implements Network {

	/*
	 * The name of the network. name along with ver may be used to fetch a flow
	 * definition from the repository, so the combination must be unique.
	 */
	private String name;

	/*
	 * The version of the network. name along with ver may be used to fetch a
	 * flow definition from the repository, so the combination must be unique.
	 */
	private String version;

	/*
	 * An array of all the places in this network. The position of the Place in
	 * the array would denote its UID.
	 */
	private Place[] places;

	/*
	 * An array of all the transitions in this network. The position of the
	 * Transition in the array would denote its UID.
	 */
	private Transition[] transitions;

	/*
	 * This is an m x n (m rows, n columns) matrix, where m is the number of
	 * transitions and n is the number of places in the network.
	 * 
	 * For each position [i,j] in the matrix, a value of 1 denotes an output
	 * from transition i to position j. -1 denotes innput to the place j from i
	 * and 0 denotesno connection between the two.
	 */
	private int[][] transitionMatrix;

	private FreeChoiceNetwork() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public Place getPlace(int placeId) throws IllegalStateException {
		if (placeId > places.length)
			throw new IllegalStateException(
					"Place with id[" + placeId + "] does not exist.");

		return places[placeId];
	}

	@Override
	public Transition getTransition(int transitionId)
			throws IllegalStateException {
		if (transitionId > transitions.length)
			throw new IllegalStateException(
					"Transition with id[" + transitionId + "] does not exist.");

		return transitions[transitionId];
	}

	@Override
	public int[][] getTransitionMatrix() {
		return transitionMatrix;
	}

	@Override
	public int[] getOutgoingTransitions(int placeId) {

		List<Integer> out = new ArrayList<>();
		for (int transitionCount = 0; transitionCount < transitions.length; transitionCount++)
			if (transitionMatrix[transitionCount][placeId] == -1)
				out.add(transitionCount);

		return out.stream().mapToInt(i -> i).toArray();
	}

	@Override
	public Transition[] getAllTransitions() {
		return transitions;
	}

	/**
	 * Privileged Builder class to create an instance of the Network and set the
	 * necessary parameters.
	 * 
	 * @author Raj Singh Sisodia
	 * @since Apr 21, 2017
	 *
	 */
	public static class Builder {
		/*
		 * This is an m x n (m rows, n columns) matrix, where m is the number of
		 * transitions and n is the number of places in the network.
		 * 
		 * For each position [i,j] in the matrix, a value of 1 denotes an input
		 * place from position j, on transition i. 0 denotes no input from the
		 * position on the transition.
		 */
		private int[][] transitionInputs;

		/*
		 * This is an m x n (m rows, n columns) matrix, where m is the number of
		 * transitions and n is the number of places in the network.
		 * 
		 * For each position [i,j] in the matrix, a value of 1 denotes an output
		 * from transition i to position j. O denotes no output to the place j
		 * from i.
		 */
		private int[][] transitionOutputs;

		/*
		 * An array of all the places in the network. The position of the Place
		 * in the array would denote its UID. To ensure the UID does not change
		 * on reloads, it needs to be provided in the definition file itself.
		 */
		private Place[] places = new Place[]{};

		/*
		 * An array of all the transitions in the network. The position of the
		 * Transition in the array would denote its UID. To ensure the UID does
		 * not change on reloads, it needs to be provided in the definition file
		 * itself.
		 */
		private Transition[] transitions= new Transition[]{};
		
		private boolean arcBuildingStarted = false;

		public Network build(String name, String version) {

			FreeChoiceNetwork network = new FreeChoiceNetwork();

			network.name = name;
			network.version = version;
			network.places = places;
			network.transitions = transitions;
			
			network.transitionMatrix = subtract(transitionOutputs, transitionInputs);
			
			for(int i=0;i<transitionInputs.length;i++){
				List<Place> incomingPlaces = new ArrayList<>();
				for(int j=0;j<transitionInputs[0].length;j++){
					if(transitionInputs[i][j] == 1)
						incomingPlaces.add(places[j]);
				}
				transitions[i].setIncomingPlaces(incomingPlaces.toArray(new Place[] {}));
			}

			for(int i=0;i<transitionOutputs.length;i++){
				List<Place> ougoingPlaces = new ArrayList<>();
				for(int j=0;j<transitionOutputs[0].length;j++){
					if(transitionOutputs[i][j] == 1)
						ougoingPlaces.add(places[j]);
				}
				transitions[i].setOutgoingPlaces(ougoingPlaces.toArray(new Place[] {}));
			}
			
			return network;
		}

		public void addPlace(Place place, int id) throws IllegalStateException {

			if (arcBuildingStarted)
				throw new IllegalStateException(
						"Cannot add more places, connection building started.");

			if (id < places.length) {
				if (places[id] != null)
					throw new IllegalStateException(
							"Place with id[" + id + "] already set.");
				else
					places[id] = place;
			} else {
				places = expandArraySize(Place.class, places, id+1);
				places[id] = place;
			}
		}

		public void addTransition(Transition transition, int id)
				throws IllegalStateException {
			if (arcBuildingStarted)
				throw new IllegalStateException(
						"Cannot add more transitions, connection building started.");

			if (id < transitions.length) {
				if (transitions[id] != null)
					throw new IllegalStateException(
							"Transition with id[" + id + "] already set.");
				else
					transitions[id] = transition;
			} else {
				transitions = expandArraySize(Transition.class, transitions, id +1);
				transitions[id] = transition;
			}
		}

		/**
		 * Adds an arc to connect a place and transition. This method must be
		 * called after all the Places and Transitions are provided.
		 * 
		 * @param transitionId
		 *            UID of the transition.
		 * @param placeId
		 *            UID of the place.
		 * @param incoming
		 *            <code>true</code> when its incoming for the transition,
		 *            i.e. flowing out of the place and coming in the
		 *            transition.
		 */
		public void addConnection(int transitionId, int placeId, boolean incoming) {
			arcBuildingStarted = true;
			
			if(transitionInputs == null)
				transitionInputs = new int[transitions.length][places.length];
			if(transitionOutputs == null)
				transitionOutputs = new int[transitions.length][places.length];
			
			if(incoming)
				transitionInputs[transitionId][placeId] = 1;
			else
				transitionOutputs[transitionId][placeId] = 1;
			
		}

		@SuppressWarnings("unchecked")
		private <T> T[] expandArraySize(Class<?>clazz, T[] orignal, int newSize) {
			T[] newArray = (T[]) Array.newInstance(clazz, newSize);

			for (int i = 0; i < orignal.length; i++) {
				newArray[i] = orignal[i];
			}

			return (T[]) newArray;

		}

		private int[][] subtract(int[][] a, int[][] b) {
			int rows = a.length;
			int columns = a[0].length;
			int[][] result = new int[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					result[i][j] = a[i][j] - b[i][j];
				}
			}
			return result;
		}

	}
}
