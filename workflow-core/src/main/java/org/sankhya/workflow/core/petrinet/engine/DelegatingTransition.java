/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine;

import java.util.function.Consumer;

import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;

/**
 * @author Pc
 *
 */
public class DelegatingTransition extends AbstractTransition {
	
	private Consumer<ExecutionContext> trigger;
	
	public DelegatingTransition(String name, Consumer<ExecutionContext> trigger) {
		super(name);
		this.trigger = trigger;
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.petrinet.Transition#trigger(org.sankhya.workflow.core.petrinet.execution.ExecutionContext)
	 */
	@Override
	public void trigger(ExecutionContext context) {
		trigger.accept(context);
		this.postTrigger(context);

	}

}
