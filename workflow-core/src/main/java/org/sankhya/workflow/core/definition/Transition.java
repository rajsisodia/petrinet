/**
 * 
 */
package org.sankhya.workflow.core.definition;

/**
 * A transition changes the state of execution of the graph, in other words,
 * actions happen at the Transition. When the preconditions for the Transition,
 * viz. {@link Place} having a {@link Token}, are fulfilled, the transition is
 * triggered. It would then consume the relevant tokens from the Place, do some
 * action, and put the Token to the next Place after appropriate modification.
 * <p>
 * A transition may consume one or more tokens at a time depending on its
 * implementation. It is the responsibility of the Transition to remove the
 * Token from the Place.
 * 
 * @author Raj Singh Sisodia
 * @since Apr 10, 2017
 *
 */
public interface Transition {

	/**
	 * Triggers the Transition and consumes tokens from the in-bound
	 * {@link Place places} and modify the state of execution. The resulting
	 * {@link Token tokens}, if any, would be places into out-bound places.
	 * 
	 */
	void trigger();

	/**
	 * Checks if the precondition to trigger the Transition are satisfied or not.
	 * @return <code>true</code> if the Transition can be triggered, otherwise <code>false</code>.
	 */
	boolean isTrigger();
	
	/**
	 * Sets the {@link Place places} which are connected to this transition through in-bound arc.
	 * @param places Places to set.
	 */
	void setIncomingPlaces(Place[] places);
	
	/**
	 * Sets the {@link Place places} which are connected to this transition through out-bound arc.
	 * @param places Places to set.
	 */
	void setOutgoingPlaces(Place[] places);
}
