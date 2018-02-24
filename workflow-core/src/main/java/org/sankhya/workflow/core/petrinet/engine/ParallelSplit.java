/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine;

import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Jan 22, 2018
 *
 */
public class ParallelSplit extends AbstractTransition {

	private final static Logger logger = LoggerFactory.getLogger(ParallelSplit.class);

	public ParallelSplit(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger(ExecutionContext context) {
		logger.trace("Splitting tokens from Transition {}.", getId());
		this.postTrigger(context);
	}

}
