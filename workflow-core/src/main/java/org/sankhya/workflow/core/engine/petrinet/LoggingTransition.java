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
 * @since Jun 13, 2017
 *
 */
public class LoggingTransition extends AbstractTransition {

	private final static Logger logger = LoggerFactory.getLogger(LoggingTransition.class);
	
	private int id = 0;
	
	public LoggingTransition(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger(ExecutionContext context) {
		for (Place in : getIncoming()) {
			if(context.exists(in.getId()) != -1){
				context.popToken(in.getId());
				logger.debug("Logging from Transition {}.", this.id);
				if (getOutgoing().length > 0)
					for (Place out : getOutgoing())
						context.pushToken(out.getId());
			}
			
		}

	}

	@Override
	public int getId() {
		return id;
	}

}
