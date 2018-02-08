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
	 * The {@link Transition} can trigger when any of the incoming {@link Place}
	 * holds a token.
	 */
	@Override
	public boolean isTrigger(ExecutionContext context) {
		for (Place in : getIncoming()) {
			if (context.exists(in.getId()) != -1)
				return true;
		}
		return false;
	}

}
