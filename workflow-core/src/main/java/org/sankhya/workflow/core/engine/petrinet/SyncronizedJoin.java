/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Token;

/**
 * @author Raj Singh Sisodia
 * @since Jan 23, 2018
 *
 */
public class SyncronizedJoin extends AbstractTransition {
	
	/*
	 * (non-Javadoc)
	 * @see org.sankhya.workflow.core.engine.petrinet.AbstractTransition#isTrigger()
	 */
	@Override
	public boolean isTrigger(){
		boolean shouldTrigger = true;
		for (Place in : getIncoming()) {
			shouldTrigger = shouldTrigger && (in.getTokenCount() > 0);
		}
		return shouldTrigger;
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger() {
		Token token = null;
		for (Place in : getIncoming()) {
			if (in.getTokenCount() == 0) return;
			token = in.getToken();
		}
		
		getOutgoing()[0].addToken(token);
	}

}
