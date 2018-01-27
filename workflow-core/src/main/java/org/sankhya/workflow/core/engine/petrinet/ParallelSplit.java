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
 * @since Jan 22, 2018
 *
 */
public class ParallelSplit extends AbstractTransition {
	
	private final static Logger logger = LoggerFactory.getLogger(ParallelSplit.class);
	
	private final int id;
	
	public ParallelSplit(int id) {
		this.id =id;
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Transition#trigger()
	 */
	@Override
	public void trigger(ExecutionContext context) {
		for (Place in : getIncoming()) {
			
			if(context.exists(in.getId()) != -1){
				logger.trace("Splitting tokens from Transition {}.", this.id);
				context.popToken(in.getId());
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
