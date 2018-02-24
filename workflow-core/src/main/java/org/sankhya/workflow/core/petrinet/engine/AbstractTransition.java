/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine;

import org.sankhya.workflow.core.engine.AbstractNode;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;

/**
 * @author Raj Singh Sisodia
 * @since Jun 13, 2017
 *
 */
public abstract class AbstractTransition extends AbstractNode<Place> implements Transition {

	public AbstractTransition(String name) {
		super(name);
	}

	/**
	 * Only one token from any one of the incoming {@link Place Places} is needed to configure this
	 * {@link Transaction}. 
	 */
	@Override
	public boolean preTrigger(ExecutionContext context) {
		for (Place in : getIncoming()) {
			if (context.exists(in.getId()) != -1) {
				context.popToken(in.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * A token is pushed to all the outgoing {@link Place Places}.
	 */
	@Override
	public void postTrigger(ExecutionContext context) {
		for (Place out : getOutgoing())
			context.pushToken(out.getId());

	}

}
