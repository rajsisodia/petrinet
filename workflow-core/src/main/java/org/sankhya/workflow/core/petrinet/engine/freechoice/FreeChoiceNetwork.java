/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine.freechoice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.definition.Node;
import org.sankhya.workflow.core.petrinet.Network;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * from transition i to position j. -1 denotes input to the place j from i
	 * and 0 denotes no connection between the two.
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
			throw new IllegalStateException("Place with id[" + placeId + "] does not exist.");

		return places[placeId];
	}

	@Override
	public Transition getTransition(int transitionId) throws IllegalStateException {
		if (transitionId > transitions.length)
			throw new IllegalStateException("Transition with id[" + transitionId + "] does not exist.");

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

	@Override
	public int getPlaceCount() {
		return places.length;
	}

	public static class Builder extends BaseNetworkBuilder {

		private final Logger logger = LoggerFactory.getLogger(Builder.class);
		@Override
		public Network build(String name, String version) {

			FreeChoiceNetwork network = new FreeChoiceNetwork();

			network.name = name;
			network.version = version;
			network.places = getPlaces().values().stream()
						.sorted(Comparator.comparing(Node::getId))
						.toArray(Place[]::new);
			
			network.transitions = getTransitions().values().stream()
					.sorted(Comparator.comparing(Node::getId))
					.toArray(Transition[]::new);
			
			network.transitionMatrix = getTransitionMatrix();

			return network;
		}
		@Override
		protected Logger getLogger() {
			return logger;
		}

	}

}
