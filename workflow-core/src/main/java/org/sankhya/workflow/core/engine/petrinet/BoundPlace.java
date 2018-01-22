/**
 * 
 */
package org.sankhya.workflow.core.engine.petrinet;

import java.util.ArrayList;
import java.util.List;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.TimeoutException;
import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Token;

/**
 * @author Raj Singh Sisodia
 * @since Apr 26, 2017
 *
 */
public class BoundPlace implements Place {

	private static final long serialVersionUID = 2856657906324136545L;

	private static final int INFINITE_CAPACITY = 0;

	private int capacity;

	private List<Token> tokens;

	public BoundPlace() {
		this(INFINITE_CAPACITY);
	}

	public BoundPlace(int capacity) {
		this.capacity = capacity;

		if (this.capacity == INFINITE_CAPACITY)
			this.tokens = new ArrayList<Token>();
		else
			this.tokens = new ArrayList<Token>(this.capacity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#isFullCapacity()
	 */
	@Override
	public boolean isFullCapacity() {
		if (this.capacity == INFINITE_CAPACITY || this.tokens.size() < this.capacity)
			return false;

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#getTokens()
	 */
	@Override
	public List<Token> getTokens() {
		return this.tokens;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#getTokens(int)
	 */
	@Override
	public List<Token> getTokens(int count) {
		if(count > tokens.size())
			count =tokens.size();
		
		if(count == 0)
			return new ArrayList<Token>();
		
		List<Token> tokensSubList = new ArrayList<>(count);
		for(int i=0;i<count;i++)
			tokensSubList.add(tokens.get(i));
		
//		List<Token> tokensSubList = tokens.subList(0,count);
		tokens.subList(0,count).clear();
		return tokensSubList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#getToken()
	 */
	@Override
	public Token getToken() {
		List<Token> internalList = getTokens(1);
		return internalList.size() > 0 ? internalList.get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sankhya.workflow.core.definition.Place#addToken(org.sankhya.workflow.
	 * core.definition.Token)
	 */
	@Override
	public void addToken(Token token) {
		tokens.add(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sankhya.workflow.core.definition.Place#addToken(org.sankhya.workflow.
	 * core.definition.Token, long)
	 */
	@Override
	public void addToken(Token token, long timeout) throws TimeoutException {
		addToken(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sankhya.workflow.core.definition.Place#removeToken(org.sankhya.
	 * workflow.core.definition.Token)
	 */
	@Override
	public List<Token> removeToken(Token token) throws IllegalStateException {
		tokens.remove(token);
		return tokens;
	}

	@Override
	public int getTokenCount() {
		return this.tokens.size();
	}

}
