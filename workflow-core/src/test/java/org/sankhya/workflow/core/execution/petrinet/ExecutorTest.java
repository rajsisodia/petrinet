package org.sankhya.workflow.core.execution.petrinet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetworkTest;
import org.sankhya.workflow.core.petrinet.execution.CompressedRowTokenStore;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.sankhya.workflow.core.petrinet.execution.Executor;
import org.sankhya.workflow.core.petrinet.execution.StandardExecutionContext;
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
		for (int i = 0; i < 1000; i++)
			executionContext.pushToken(0);
		executor.execute(executionContext);
	}

}
