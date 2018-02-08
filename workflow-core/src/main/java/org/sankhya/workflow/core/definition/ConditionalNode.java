/**
 * 
 */
package org.sankhya.workflow.core.definition;

import java.util.function.Function;

/**
 * Lacking any special construct to model arcs, ConditionalNode provides the
 * capability to provide arc conditions between current node and the incoming
 * node.
 * <p>
 * A node can accept a Token only when the arc condition is full-filled. Arc
 * conditions are in format of Function<T, Boolean>, where they expect an input
 * and provide a boolean output.
 * 
 * @since Feb 3, 2018
 *
 */
public interface ConditionalNode<N extends Node<?>> extends Node<N> {

	/**
	 * A node can accept a Token only when certain conditions are full-filled.
	 * 
	 * @param node
	 * @return the Function{@link Function} defining the condition for this
	 *         node.
	 */
	<T> Function<T, Boolean> getCondition(N node);

	
	/**
	 * 
	 * @param function
	 */
	<T> void setCondition(Function<T, Boolean> function, N incomingNode);

}
