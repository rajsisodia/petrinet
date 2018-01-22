/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

import java.util.HashSet;
import java.util.Set;

import org.sankhya.workflow.core.definition.Transition;
import org.sankhya.workflow.core.engine.petrinet.Network;

/**
 * @author Raj Singh Sisodia
 * @since Apr 20, 2017
 *
 */
public class ExecutionContext {
	
	private Network network;
	
	public ExecutionContext(Network network) {
		this.network = network;
	}

	public void execute(){
		Set<Transition> triggerTransitions = nextTransitionMatrix();
		while(triggerTransitions.size() >0){
			for(Transition transition : triggerTransitions){
				if(transition.isTrigger())
					transition.trigger();
			}
			triggerTransitions = nextTransitionMatrix();
		}
		
		
	}

	private Set<Transition> nextTransitionMatrix(){

		Set<Transition> triggerTransitions = new HashSet<>();
		for(Transition transition : network.getAllTransitions()){
			if(transition.isTrigger()){
				triggerTransitions.add(transition);
			}
		}
		
		return triggerTransitions;
	}
	
	
	
	
}
