package org.sankhya.workflow.core.execution.petrinet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetworkTest;
import org.sankhya.workflow.core.execution.petrinet.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorTest {
	
	private final Logger logger = LoggerFactory.getLogger(ExecutorTest.class);

	private Executor executor;

	@Before
	public void setUp() throws Exception {
		this.executor = new Executor(FreeChoiceNetworkTest.buildConcurrentNetWork());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		ExecutionContext executionContext = new StandardExecutionContext(new CompressedRowTokenStore(3));
		executionContext.pushToken(0);
		executor.execute(executionContext);
	}

}
