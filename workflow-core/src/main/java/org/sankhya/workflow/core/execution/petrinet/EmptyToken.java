/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

import java.util.UUID;

import org.sankhya.workflow.core.definition.Token;

/**
 * @author Raj Singh Sisodia
 * @since Jun 14, 2017
 *
 */
public class EmptyToken implements Token {
	
	private static final long serialVersionUID = -8776289021490082646L;
	private final UUID uuid;
	
	public EmptyToken(){
		this.uuid = UUID.randomUUID();
	}

	/* (non-Javadoc)
	 * @see org.sankhya.workflow.core.definition.Token#getId()
	 */
	@Override
	public UUID getId() {
		return this.uuid;
	}

}
