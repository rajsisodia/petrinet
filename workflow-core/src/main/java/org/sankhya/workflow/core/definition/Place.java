/**
 * 
 */
package org.sankhya.workflow.core.definition;

import java.io.Serializable;
import java.util.List;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.TimeoutException;
import org.springframework.messaging.Message;

/**
 * A place is part of the bi-parate graph responsible for holding the
 * {@link Message} object and instance variables collectively called tokens. It
 * can connect only with a Transition using a connection arc. After the token is
 * consumed by the transition it would be removed from the Place buffer, which
 * can be of any size depending upon the capacity. The Capacity of a place
 * denotes the maximum number of tokens it can hold at a time. Generally
 * <b>0</b> denotes infinite capacity.
 * <p>
 * A set of all places at any given point of time would represent the execution
 * state of the flow at that instance.
 * 
 * @author Raj Singh Sisodia
 * @since Mar 22, 2017
 *
 */
public interface Place extends Serializable {

	/**
	 * Returns weather the place already contains the maximum number of tokens.
	 * 
	 * @return <code>true</code> when the place is at maximum capacity,
	 *         otherwise <code>false</code>
	 */
	boolean isFullCapacity();

	/**
	 * Provides the list of all {@link Token Tokens} available at the Place at
	 * the time of request.
	 * 
	 * @return List of tokens at the place, an empty List if the Place has no
	 *         tokens. Never <code>null</code>.
	 */
	List<Token> getTokens();

	/**
	 * Provides the list of {@link Token Tokens} available at the Place at the
	 * time of request. The number of tokens returned will either be requested
	 * count or all tokens, whichever is lower. The order of tokens is
	 * non-determinant.
	 * 
	 * @param count
	 *            number of tokens which needs to be provided.
	 * @return List of requested number tokens at the place, an empty List if
	 *         the Place has no tokens. Never <code>null</code>.
	 */
	List<Token> getTokens(int count);

	/**
	 * Convenience method for {@link #getTokens(int)} with <b>count</b> equals
	 * to <b>1</b>.
	 * 
	 * @return One token at the place, <code>null</code> if
	 *         the Place has no tokens.
	 */
	Token getToken();

	/**
	 * Adds a {@link Token} to the Place. This is a blocking call, whenever
	 * there is an overflow of the capacity the call will be blocked until the
	 * buffer is available.
	 * 
	 * @param token
	 *            the {@link Token} to be added to the Place.
	 */
	void addToken(Token token);

	/**
	 * Adds a {@link Token} to the Place. This call will be blocked whenever
	 * there is an overflow of capacity, until the Place buffer is available
	 * again, or timeout has occurred, whichever is earlier.
	 * 
	 * @param token
	 *            the {@link Token} to be added to the Place.
	 * @param timeout
	 *            time in milliseconds to wait for the Place buffer to be
	 *            available.
	 * @throws TimeoutException
	 *             if the timeout occurs before the buffer is available.
	 */
	void addToken(Token token, long timeout) throws TimeoutException;

	/**
	 * Removes the specified {@link Token} from the Place buffer.
	 * 
	 * @param token
	 *            token to be removed.
	 * @return List of available tokens at the place after the removal, an empty
	 *         List if the Place has no tokens. Never <code>null</code>.
	 * @throws IllegalStateException
	 *             if the token is not available.
	 */
	List<Token> removeToken(Token token) throws IllegalStateException;
	
	/**
	 * 
	 * @return The number of tokens currently held at the Place. <code>0</code> when the place is empty;
	 */
	int getTokenCount();
	
}
