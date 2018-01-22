/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Token;

/**
 * @author Raj Singh Sisodia
 * @since Jan 22, 2018
 *
 */
public class ParallelSplit extends AbstractTransition {

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger() {
		for (Place in : getIncoming()) {
			if (in.getTokenCount() > 0) {
				Token token = in.getToken();
				if (token != null) {
					if (getOutgoing().length > 0)
						for (Place out : getOutgoing())
							out.addToken(token);
				}
			}
		}

	}

}
