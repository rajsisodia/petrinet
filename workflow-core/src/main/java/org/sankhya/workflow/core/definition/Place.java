/**
 * 
 */
package org.sankhya.workflow.core.definition;

import java.io.Serializable;
import java.util.List;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.TimeoutException;
import org.springframework.messaging.Message;

/**
 * A place is part of the bi-parate graph responsible for holding the
 * {@link Message} object and instance variables collectively called tokens. It
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
public interface Place extends Serializable {
	
	/**
	 * @return the ID associated with this place.
	 */
	int getId();

	/**
	 * Returns weather the place already contains the maximum number of tokens.
	 * 
	 * @return <code>true</code> when the place is at maximum capacity,
	 *         otherwise <code>false</code>
	 */
	boolean isFullCapacity();

	/**
	 * 
	 * @return The number of tokens currently held at the Place. <code>0</code> when the place is empty;
	 */
	int getTokenCount();
	
	Transition[] getOutgoingTransitions();
	
	void addTransition(Transition transition);
	
}
