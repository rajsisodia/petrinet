/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine;

import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Jan 23, 2018
 *
 */
public class SyncronizedJoin extends AbstractTransition {

	private final static Logger logger = LoggerFactory.getLogger(SyncronizedJoin.class);

	public SyncronizedJoin(String name) {
		super(name);
	}

	/**
	 * In a Join all incoming {@link Place Places} are expected to have a token
	 * before the {@link Transaction} can be triggered. This method is non thread
	 * safe and makes changes to the Places holding tokens so it is expected to be
	 * executed in the same thread as the
	 * {@link org.sankhya.workflow.core.petrinet.execution.Executor Executor}.
	 */
	@Override
	public boolean preTrigger(ExecutionContext context) {
		boolean shouldTrigger = true;

		for (Place in : getIncoming()) {
			shouldTrigger = shouldTrigger && (context.exists(in.getId()) != -1);
		}

		if (!shouldTrigger)
			return false;

		for (Place in : getIncoming()) {
			context.popToken(in.getId());
		}

		return true;
	}

	@Override
	public void trigger(ExecutionContext context) {
		logger.trace("Joining tokens at Transition {}.", getId());
		this.postTrigger(context);
	}
	

}
