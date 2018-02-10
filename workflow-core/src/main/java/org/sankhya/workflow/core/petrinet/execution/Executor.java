/**
 * 
 */
package org.sankhya.workflow.core.petrinet.execution;

import org.sankhya.workflow.core.petrinet.Network;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;
import org.sankhya.workflow.core.petrinet.engine.EndPlace;

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
			int val = ts.peek();
			if(val <0 )
				continue;
			Place place = network.getPlace(val);
			if(place instanceof EndPlace)
				break;
			for(Transition transition : place.getOutgoing()){
				if(transition.isTrigger(executionContext))
					transition.trigger(executionContext);
			}
		}
		
		
	}
	
}
