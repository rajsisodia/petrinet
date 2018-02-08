/**
 * 
 */
package org.sankhya.workflow.core.definition;

import org.sankhya.workflow.core.execution.petrinet.ExecutionContext;

/**
 * @author Raj Singh Sisodia
 * @since Feb 1, 2018
 *
 */
public interface Listener<N extends Node<?>> {

	/*
	 * TODO: provide either a generic event which can be sub-classed or create
	 * lifecycle enum.
	 */
	void listen(ExecutionContext context, String event);

}
