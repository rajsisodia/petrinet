package org.sankhya.workflow.core.petrinet.execution;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class StandardExecutionContext implements ExecutionContext {

	private TokenStore tokenStore;
	private Queue<Integer> pushData = new LinkedBlockingQueue();
	
	
	public StandardExecutionContext(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}
	
	@Override
	public void pushToken(int placeId) {
		pushData.add(placeId);

	}

	@Override
	public int popToken(int placeId) {
		return tokenStore.pop(placeId);
	}

	@Override
	public int exists(int placeId) {
		return tokenStore.exists(placeId);
	}

	@Override
	public TokenStore getTokenStore() {
		return tokenStore;
	}

	@Override
	public void syncronize() {
		while(!pushData.isEmpty()) {
			tokenStore.push(pushData.poll());	
		}
	}

}
