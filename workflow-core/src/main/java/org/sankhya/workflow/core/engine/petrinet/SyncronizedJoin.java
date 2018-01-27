/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.execution.petrinet.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Jan 23, 2018
 *
 */
public class SyncronizedJoin extends AbstractTransition {
	
	private final static Logger logger = LoggerFactory.getLogger(SyncronizedJoin.class);

	private int id;

	public SyncronizedJoin(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sankhya.workflow.core.engine.petrinet.AbstractTransition#isTrigger()
	 */
	@Override
	public boolean isTrigger(ExecutionContext context) {
		boolean shouldTrigger = true;
		for (Place in : getIncoming()) {
			shouldTrigger = shouldTrigger && (context.exists(in.getId()) != -1);
		}
		return shouldTrigger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger(ExecutionContext context) {
		for (Place in : getIncoming()) {
			
			if (context.exists(in.getId()) == -1)
				return;
			context.popToken(in.getId());
		}
		logger.trace("Joining tokens at Transition {}.", this.id);
		context.pushToken(getOutgoing()[0].getId());
	}

	@Override
	public int getId() {
		return id;
	}

}
