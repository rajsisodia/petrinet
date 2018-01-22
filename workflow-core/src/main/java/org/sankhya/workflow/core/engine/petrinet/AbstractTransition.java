/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Transition;

/**
 * @author Raj Singh Sisodia
 * @since Jun 13, 2017
 *
 */
public abstract class AbstractTransition implements Transition {

	private Place[] incoming;
	private Place[] outgoing;

	/**
	 * The {@link Transition} can trigger when any of the incoming {@link Place} holds a token.
	 */
	@Override
	public boolean isTrigger() {
		for(Place in : incoming){
			if(in.getTokenCount() > 0) return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Transition#setIncomingPlaces(org.sankhya.workflow.core.definition.Place[])
	 */
	@Override
	public void setIncomingPlaces(Place[] places) {
		incoming = places;
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Transition#setOutgoingPlaces(org.sankhya.workflow.core.definition.Place[])
	 */
	@Override
	public void setOutgoingPlaces(Place[] places) {
		outgoing = places;
	}

	protected Place[] getIncoming() {
		return incoming;
	}

	protected Place[] getOutgoing() {
		return outgoing;
	}
	
	

}
