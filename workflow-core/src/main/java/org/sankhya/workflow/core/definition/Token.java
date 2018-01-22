/**
 * 
 */
package org.sankhya.workflow.core.definition;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.messaging.Message;

/**
 * A token represents part of the state of execution of the graph. It stores a
 * {@link Message} object and instance variables.
 * 
 * @author Raj Singh Sisodia
 * @since Apr 10, 2017
 *
 */
public interface Token extends Serializable{
	
	/**
	 * A token can be identified using its unique id. 
	 * @return UUID of the token.
	 */
	UUID getId();

}
