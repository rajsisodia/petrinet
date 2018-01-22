package org.sankhya.workflow.core.execution.petrinet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetworkTest;
import org.sankhya.workflow.core.execution.petrinet.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionContextTest {
	
	private final Logger logger = LoggerFactory.getLogger(ExecutionContextTest.class);

	private ExecutionContext executionContext;

	@Before
	public void setUp() throws Exception {
		this.executionContext = new ExecutionContext(FreeChoiceNetworkTest.buildNetWorkOne());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		executionContext.execute();
	}

}
