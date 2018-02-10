package org.sankhya.workflow.core.execution.petrinet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.petrinet.Network;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.engine.AsyncTransition;
import org.sankhya.workflow.core.petrinet.engine.BoundPlace;
import org.sankhya.workflow.core.petrinet.engine.EndPlace;
import org.sankhya.workflow.core.petrinet.engine.ParallelSplit;
import org.sankhya.workflow.core.petrinet.engine.SyncronizedJoin;
import org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork;
import org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork.Builder;
import org.sankhya.workflow.core.petrinet.execution.CompressedRowTokenStore;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.sankhya.workflow.core.petrinet.execution.Executor;
import org.sankhya.workflow.core.petrinet.execution.StandardExecutionContext;

public class AsyncTransitionTest {
	
	private Executor executor;
	
	/*
	 * 		P0
	 * 		|
	 * 		ParallelSplit
	 * 		|			|
	 * 		P1			P2
	 * 		|			|
	 * 		WaitAsync	Logging
	 * 		|			|
	 * 		P3			P4
	 * 		|___________|
	 * 			|
	 * 		SyncJoin
	 * 			|
	 * 			P5
	 * 
	 */
	public static Network buildNetWorkWithAsync() {
		Builder builder = new Builder();
		
		builder.add(new BoundPlace("P0"))
				.add(new BoundPlace("P1"))
				.add(new BoundPlace("P2"))
				.add(new BoundPlace("P3"))
				.add(new BoundPlace("P4"))
				.add(new EndPlace("P5"))
				.add(new ParallelSplit("ParallelSplit"))
				.add(new AsyncTransition("WaitAsync", new WaitTransition("WaitTransition", 3000)))
//				.add(new WaitTransition("WaitAsync", 3000))
				.add(new LoggingTransition("Logging"))
				.add(new SyncronizedJoin("SyncJoin"))
				.connect("P0", "ParallelSplit")
				.connect("ParallelSplit", "P1")
				.connect("ParallelSplit", "P2")
				.connect("P1", "WaitAsync")
				.connect("P2", "Logging")
				.connect("WaitAsync", "P3")
				.connect("Logging", "P4")
				.connect("P3", "SyncJoin")
				.connect("P4", "SyncJoin")
				.connect("SyncJoin", "P5");
		
		return builder.build("AsyncNetwork", "1.0");
	}

	@Before
	public void setUp() throws Exception {
		executor = new Executor( buildNetWorkWithAsync());
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ExecutionContext executionContext = new StandardExecutionContext(new CompressedRowTokenStore(3));
		executionContext.pushToken(0);
		executor.execute(executionContext);
	}

}
