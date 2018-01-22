/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Jun 13, 2017
 *
 */
public class LoggingTransition extends AbstractTransition {

	private final Logger logger = LoggerFactory.getLogger(LoggingTransition.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger() {
		for (Place in : getIncoming()) {
			if (in.getTokenCount() > 0) {
				Token token = in.getToken();
				if (token != null) {
					logger.debug("Logging token {} from Transition {}.", token,this);
					if (getOutgoing().length > 0)
						for (Place out : getOutgoing())
							out.addToken(token);
				}
			}
		}

	}

}
