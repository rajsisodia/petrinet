/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

/**
 * Provides methods and strategies to store tokens.
 * 
 * @author Raj Singh Sisodia
 * @since Jan 27, 2018
 *
 */
public interface TokenStore {
	
	/**
	 * Add a token to the store.
	 * @param placeId Place at which the token needs to be added.
	 */
	void push(int placeId);
	
	int pop();
	
	int pop(int placeId);
	
	int peek();
	
	int peek(int placeId);
	
	int exists(int placeId);
	
	boolean hasNext();

}
