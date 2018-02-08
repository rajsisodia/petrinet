/**
 * 
 */
package org.sankhya.workflow.core.petrinet;

import org.sankhya.workflow.core.definition.Node;

/**
 * @author Raj Singh Sisodia
 * @since Feb 7, 2018
 *
 */
public interface NetworkBuilder {
	
	NetworkBuilder add(Node<?> node);
	NetworkBuilder connect(String name, String to);
	NetworkBuilder connect(Node<?> from, Node<?> to);
	Network build(String name, String version);

}
