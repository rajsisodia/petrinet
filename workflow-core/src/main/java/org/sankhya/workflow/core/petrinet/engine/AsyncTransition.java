/**
 * 
 */
package org.sankhya.workflow.core.petrinet.engine;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;

/**
 * @author Raj Singh Sisodia
 * @since Feb 8, 2018
 *
 */
public class AsyncTransition extends AbstractTransition {
	
	private final Transition transition;
	
	public AsyncTransition(String name, Transition transition) {
		super(name);
		this.transition = transition;
	}

	@Override
	public void trigger(ExecutionContext context) {
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		commonPool.execute(() ->{
				transition.trigger(context);
		});
	}

	@Override
	public boolean isTrigger(ExecutionContext context) {
		return transition.isTrigger(context);
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		transition.setId(id);
	}


	@Override
	public void setOutgoing(Place[] outgoing) {
		super.setOutgoing(outgoing);
		transition.setOutgoing(outgoing);
	}


	@Override
	public void setIncoming(Place[] incoming) {
		super.setIncoming(incoming);
		transition.setIncoming(incoming);
	}
	
	@Override
	public void setOutgoing(Place outgoing){
		super.setOutgoing(outgoing);
		((AbstractTransition)transition).setOutgoing(outgoing);
	}
	
	@Override
	public void setIncoming(Place incoming){
		super.setIncoming(incoming);
		((AbstractTransition)transition).setIncoming(incoming);
	}
	

}
