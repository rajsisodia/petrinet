/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

import org.sankhya.workflow.core.engine.petrinet.EndPlace;
import org.sankhya.workflow.core.petrinet.Network;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;

/**
 * @author Raj Singh Sisodia
 * @since Apr 20, 2017
 *
 */
public class Executor {
	
	private Network network;
	
//	TokenStore ts = new CompressedRowTokenStore(3);
	
	public Executor(Network network) {
		this.network = network;
	}

	public void execute(ExecutionContext executionContext){
		
		TokenStore ts = executionContext.getTokenStore();
		
		while(ts.hasNext()){
			Place place = network.getPlace(ts.peek());
			if(place instanceof EndPlace)
				break;
			for(Transition transition : place.getOutgoing()){
				if(transition.isTrigger(executionContext))
					transition.trigger(executionContext);
			}
		}
		
		
	}

	
}
