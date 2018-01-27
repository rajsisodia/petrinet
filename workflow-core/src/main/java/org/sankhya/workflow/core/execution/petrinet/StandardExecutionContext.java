package org.sankhya.workflow.core.execution.petrinet;

public class StandardExecutionContext implements ExecutionContext {

	private TokenStore tokenStore;
	
	public StandardExecutionContext(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}
	
	@Override
	public void pushToken(int placeId) {
		tokenStore.push(placeId);

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

}
