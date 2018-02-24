/**
 * 
 */
package org.sankhya.workflow.core.petrinet.execution;

/**
 * @author Raj Singh Sisodia
 * @since Jan 27, 2018
 *
 */
public interface ExecutionContext {
	
	void pushToken(int placeId);
	
	int popToken(int placeId);
	
	int exists(int placeId);
	
	TokenStore getTokenStore();
	
	void syncronize();

}
