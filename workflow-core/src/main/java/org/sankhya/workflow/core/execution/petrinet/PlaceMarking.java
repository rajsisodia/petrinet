/**
 * 
 */
package org.sankhya.workflow.core.execution.petrinet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.sankhya.workflow.core.definition.Token;

/**
 * @author Raj Singh Sisodia
 * @since Apr 20, 2017
 *
 */
public class PlaceMarking implements Serializable {

	private static final long serialVersionUID = 6732205605169614733L;
	private final List<Token> markings = new ArrayList<>();

	public void addToken(Token token, int placeId) {
		// TODO : Handle concurrency.

		markings.add(token);

	}

	public Token getToken() {
		// TODO : Handle concurrency.
		if(markings == null || markings.size() == 0)
			return null;
		
		Token token = markings.get(0);
		markings.remove(0);
		
		return token;
	}

}
