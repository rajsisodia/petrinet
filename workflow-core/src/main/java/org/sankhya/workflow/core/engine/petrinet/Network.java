/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Transition;
import org.sankhya.workflow.core.IllegalStateException;

/**
 * A network represents a bipartite graph, consisting of {@link Place Places}
 * and {@link Transition Transitions}. Being such, edges or arcs are allowed
 * only to connect between a Place and a Transition. Two Places or Transitions
 * cannot be connected directly.
 * 
 * @author Raj Singh Sisodia
 * @since Apr 20, 2017
 *
 */
public interface Network {

	/**
	 * Name of the network for easy identification. Name along with Version may
	 * also be used to fetch the flow from repository, so the combination must
	 * be unique.
	 * 
	 * @return Name of the Network as defined in the definition file.
	 */
	String getName();

	/**
	 * Version of the network to support multi-version network. Name along with
	 * Version may also be used to fetch the flow from repository, so the
	 * combination must be unique.
	 * 
	 * @return Version of the Network as defined in the definition file.
	 */
	String getVersion();

	/**
	 * Provide the Place based on the identifier. This is a local identifier and
	 * must be unique in a single definition.
	 * 
	 * @param placeId
	 *            The UID of the place, expected to be unique in a single
	 *            definition.
	 * @return Place object corresponding the provided UID;
	 * @throws IllegalStateException
	 *             When there is no place corresponding to the provided UID.
	 */
	Place getPlace(int placeId) throws IllegalStateException;

	/**
	 * Provide the Transition based on the identifier. This is a local
	 * identifier and must be unique in a single definition.
	 * 
	 * @param transitionId
	 *            The UID of the place, expected to be unique in a single
	 *            definition.
	 * @return Transition object corresponding the provided UID;
	 * @throws IllegalStateException
	 *             When there is no transition corresponding to the provided
	 *             UID.
	 */
	Transition getTransition(int transitionId) throws IllegalStateException;

	/**
	 * Provides an m x n (m rows, n columns) matrix, where m is the number of
	 * transitions and n is the number of places in the network. The values
	 * denote define the arcs between the Transitions and Places. The position
	 * of the Transition and places in the matrix is decided by their
	 * corresponding integer UID.
	 * 
	 * @return The network connections in form of a matrix.
	 */
	int[][] getTransitionMatrix();

	/**
	 * Provides the UIDs of all out going Transaction from the provided Place UID.
	 * @param placeId The UID of the place.
	 * @return Array of UIDs of outgoing Transactions. 
	 */
	int[] getOutgoingTransitions(int placeId);

	/**
	 * Provides all the transactions in the network.
	 * @return Array if all the transactions in the network.
	 */
	Transition[] getAllTransitions();

}
