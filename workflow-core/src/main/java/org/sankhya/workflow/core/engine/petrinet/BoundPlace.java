/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.engine.AbstractNode;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;

/**
 * @author Raj Singh Sisodia
 * @since Apr 26, 2017
 *
 */
public class BoundPlace extends AbstractNode<Transition> implements Place {

	private static final int INFINITE_CAPACITY = 0;

	private int capacity;

	public BoundPlace(String name) {
		this(name, INFINITE_CAPACITY);
	}

	public BoundPlace(String name, int capacity) {
		super(name);
		this.capacity = capacity;

	}

	@Override
	public int getCapcity() {
		return capacity;
	}
	
	/*
	public void addOutgoingTransition(Transition transition) {
		Transition[]outgoing = getOutgoing();
		Transition[] newOutgoing = Arrays.copyOf(outgoing, outgoing.length + 1);
		newOutgoing[newOutgoing.length - 1] = transition;
		setOutgoing(newOutgoing);
	}
	*/

}
