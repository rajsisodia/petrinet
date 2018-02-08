/**
 * 
 */
package org.sankhya.workflow.core.petrinet;

import org.sankhya.workflow.core.definition.Node;

/**
 * A place is part of the bi-parate graph where a {@link Token} is stored. It
 * can connect only with a Transition using a connection arc. After the token is
 * consumed by the transition it would be removed from the Place buffer, which
 * can be of any size depending upon the capacity. The Capacity of a place
 * denotes the maximum number of tokens it can hold at a time. Generally
 * <b>0</b> denotes infinite capacity.
 * <p>
 * A set of all places at any given point of time would represent the execution
 * state of the flow at that instance.
 * 
 * @author Raj Singh Sisodia
 * @since Mar 22, 2017
 *
 */
public interface Place extends Node<Transition> {

	/**
	 * Capacity is the maximum number of tokens a Place can hold.
	 * 
	 * @return the capacity of this place.
	 */
	int getCapcity();

}
