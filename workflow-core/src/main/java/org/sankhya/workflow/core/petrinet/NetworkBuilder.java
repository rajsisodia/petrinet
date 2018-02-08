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
	
	NetworkBuilder add(String name, Node<?> node);
	NetworkBuilder connect(Node<?> from, Node<?> to);
	Network build(String name, String version);

}
