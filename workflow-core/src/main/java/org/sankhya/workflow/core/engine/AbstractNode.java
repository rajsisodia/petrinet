/**
 * 
 */
package org.sankhya.workflow.core.engine;

import java.util.Arrays;

import org.sankhya.workflow.core.definition.Node;

/**
 * @author Raj Singh Sisodia
 * @since Feb 8, 2018
 *
 */
public class AbstractNode<N extends Node<?>> implements Node<N> {
	
	private int id;
	private String name;

	private N[] incoming;
	private N[] outgoing;
	
	public AbstractNode(String name){
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public N[] getOutgoing() {
		return outgoing;
	}

	@Override
	public void setOutgoing(N[] outgoing) {
		this.outgoing = outgoing;
	}

	/**
	 * Convenience method to add outgoing nodes one by one.
	 * @param outgoing The node to be added as a child to this node.
	 */
	public void setOutgoing(N outgoing){
		N[] newOutgoing = Arrays.copyOf(this.outgoing, this.outgoing.length+1);
		newOutgoing[newOutgoing.length-1] = outgoing;
		this.outgoing = newOutgoing;
	}
	
	@Override
	public N[] getIncoming() {
		return incoming;
	}

	@Override
	public void setIncoming(N[] incoming) {
		this.incoming = incoming;
	}
	
	/**
	 * Convenience method to add incoming nodes one by one.
	 * @param incoming The node to be added as a parent to this node.
	 */
	public void setIncoming(N incoming){
		N[] newIncoming = Arrays.copyOf(this.incoming, this.incoming.length+1);
		newIncoming[newIncoming.length-1] = incoming;
		this.incoming = newIncoming;
	}

}
