/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import java.util.Arrays;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Transition;

/**
 * @author Raj Singh Sisodia
 * @since Apr 26, 2017
 *
 */
public class BoundPlace implements Place {

	private static final long serialVersionUID = 2856657906324136545L;

	private static final int INFINITE_CAPACITY = 0;

	private int capacity;
	
	private final int id;
	
	private Transition[] outgoing = new Transition[0];


	public BoundPlace(int id) {
		this(id, INFINITE_CAPACITY);
	}

	public BoundPlace(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#isFullCapacity()
	 */
	@Override
	public boolean isFullCapacity() {
		if (this.capacity == INFINITE_CAPACITY || this.getTokenCount() < this.capacity)
			return false;

		return true;

	}

	@Override
	public int getTokenCount() {
		return 0;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Transition[] getOutgoingTransitions() {
		return outgoing;
	}
	
	public void addTransition(Transition transition){
		Transition[] newOutgoing = Arrays.copyOf(outgoing, outgoing.length +1);
		newOutgoing[newOutgoing.length-1] = transition;
		outgoing = newOutgoing;
	}

}
