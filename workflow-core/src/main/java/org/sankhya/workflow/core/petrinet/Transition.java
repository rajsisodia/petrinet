/**
 * 
 */
package org.sankhya.workflow.core.petrinet;

import org.sankhya.workflow.core.definition.Node;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;

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
public interface Transition extends Node<Place> {

	/**
	 * Responsible to perform actions needed to successful transition trigger, like
	 * fetching the tokens from relevant places before triggering the Transition.
	 * 
	 * @param context
	 *            ExecutionContext under which the Transition needs to be triggered.
	 *           
	 * @return boolean <b>true</b> if successfully configured the trigger environment, otherwise <b>false</b>.
	 */
	boolean preTrigger(ExecutionContext context);

	/**
	 * Triggers the Transition and consumes tokens from the in-bound {@link Place
	 * places} and modify the state of execution. The resulting {@link Token
	 * tokens}, if any, would be places into out-bound places.
	 * 
	 * @param context
	 *            ExecutionContext under which the Transition needs to be triggered.
	 * 
	 */
	void trigger(ExecutionContext context);

	/**
	 * Responsible to perform actions needed after successful triggering of the
	 * transition, like pushing tokens to relevant places after the trigger is
	 * complete.
	 * 
	 * @param context
	 *            ExecutionContext under which the Transition needs to be triggered.
	 */
	void postTrigger(ExecutionContext context);

}
