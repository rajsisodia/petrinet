/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

import org.sankhya.workflow.core.petrinet.engine.AbstractTransition;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Jun 13, 2017
 *
 */
public class LoggingTransition extends AbstractTransition {

	private final static Logger logger = LoggerFactory.getLogger(LoggingTransition.class);

	public LoggingTransition(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger(ExecutionContext context) {
		logger.debug("Logging from Transition {}.", getId());
		this.postTrigger(context);
	}

}
