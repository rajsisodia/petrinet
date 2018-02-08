/**
 * 
 */
package org.sankhya.workflow.core.definition;

import org.sankhya.workflow.core.petrinet.Network;

/**
 * Represents a position in graph. Multiple nodes are connected in directional
 * graph, representing parent child relationship. Children of the node are
 * represented as <b>outgoing</b> nodes and parents as <b>incoming</b> nodes.
 * 
 * @author Raj Singh Sisodia
 * @since Feb 3, 2018
 *
 */
public interface Node<N extends Node<?>> {

	/**
	 * An ID is a unique identifier of the Node. It's expected to be used
	 * internally for easy access of nodes in {@link Network}. Implementors can
	 * decide if this needs to be auto generated at Network creation of
	 * statically provided in Network design.
	 * 
	 * @return the ID associated with this node.
	 */
	int getId();

	/**
	 * Sets the ID for this node. This is deliberately lazy to allow the
	 * programmatic generation of IDs when they get added to a container.
	 * 
	 * @param id the ID to associate with this node.
	 */
	void setId(int id);

	/**
	 * A Name is an identifier which would be used for logging and debug
	 * processes. It is not expected to used for internal identification within
	 * the framework, though this may be subject to the specific
	 * implementations.
	 * 
	 * @return the name associated with this node.
	 */
	String getName();

	/**
	 * Outgoing node is a child node of the current node in a directed graph.
	 * 
	 * @return Array of all the child nodes of this node.
	 */
	N[] getOutgoing();
	
	/**
	 * Sets all the child nodes for this node.  
	 * @param outgoing Array of all child nodes for this node.
	 */
	void setOutgoing(N[] outgoing);

	/**
	 * Incoming node is a parent node of the current node in a directed graph.
	 * 
	 * @return Array of all the parent nodes of this node.
	 */
	N[] getIncoming();

	/**
	 * Sets all the parent nodes for this node.
	 * @param incoming Array of all the parent nodes of this node.
	 */
	void setIncoming(N[] incoming);

	

}
