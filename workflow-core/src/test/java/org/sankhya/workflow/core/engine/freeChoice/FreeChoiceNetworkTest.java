/**
 * 
 */
package org.sankhya.workflow.core.engine.freeChoice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.execution.petrinet.LoggingTransition;
import org.sankhya.workflow.core.petrinet.Network;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.engine.BoundPlace;
import org.sankhya.workflow.core.petrinet.engine.EndPlace;
import org.sankhya.workflow.core.petrinet.engine.ParallelSplit;
import org.sankhya.workflow.core.petrinet.engine.SyncronizedJoin;
import org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork;
import org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Raj Singh Sisodia
 * @since Apr 26, 2017
 *
 */
public class FreeChoiceNetworkTest {
	
	private final Logger logger = LoggerFactory.getLogger(FreeChoiceNetworkTest.class);

	private FreeChoiceNetwork network;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.network = (FreeChoiceNetwork) buildConcurrentNetWork();
	}

	/* 
	 * build following network
	 * @formatter:off
	 *				P0
	 *				|
	 *			---------
	 *			|		|
	 *			T0		T1
	 *			|		|
	 *			P1		P2
	 *			|		|
	 *		 -------	|
	 *		 |	   |	|
	 *		 T2    T3	|
	 *		 |	   |	|
	 *		 ----------T4
	 *			   |
	 *			   P3
	 * 
	 *  @formatter:on
	 */
	public static Network buildNetWorkOne() {
		Builder builder = new Builder();
		
		Place start = new BoundPlace("P0");
		
		builder.add(start);
		builder.add(new BoundPlace("P1"));
		builder.add(new BoundPlace("P2"));
		builder.add(new EndPlace("P3"));
		
		builder.add(new LoggingTransition("T0"));
		builder.add(new LoggingTransition("T1"));
		builder.add(new LoggingTransition("T2"));
		builder.add(new LoggingTransition("T3"));
		builder.add(new LoggingTransition("T4"));
		
		builder.connect("P0", "T0");
		builder.connect("P0", "T1");
		builder.connect("T0", "P1");
		builder.connect("P1", "T2");
		builder.connect("P1", "T3");
		builder.connect("T1", "P2");
		builder.connect("P2", "T4");
		builder.connect("T2", "P3");
		builder.connect("T3", "P3");
		builder.connect("T4", "P3");
		
		return builder.build("Simple", "1.0");
	}
	
	/* 
	 * build following network
	 * @formatter:off
	 *				P0
	 *				|
	 *				T0 (concurreny Transition)
	 *				|
	 *			---------
	 *			P1      P2
	 *			|		|
	 *			T1		T2
	 *			|		|
	 *			P3		P4
	 *			|		|
	 *			T3 (CT)	|
	 *			|		|
	 *		 -------	|
	 *		 |	   |	|
	 *		 P5	   P6	|
	 *		 |	   |	|
	 *		 T4    T5	|
	 *		 |	   |	|
	 *		 P8    P9   |
	 *		 |	   |	|
	 *		 |	   T8	|
	 *		 |	   |	|
	 *		 |	   P10	|
	 *		 |	   |	|
	 *		 -------	|
	 *			|		|
	 *	(Sync) T7		|
	 *			|		|
	 *		 ----------T6
	 *			   |
	 *			   P7
	 *			   |
	 *			   T9
	 *			   |
	 *			   P11
	 * 
	 *  @formatter:on
	 */
	public static Network buildConcurrentNetWork() {
		Builder builder = new Builder();
		
		Place start = new BoundPlace("P0");
		
		builder.add(start);
		builder.add(new BoundPlace("P1"));
		builder.add(new BoundPlace("P2"));
		builder.add(new BoundPlace("P3"));
		builder.add(new BoundPlace("P4"));
		builder.add(new BoundPlace("P5"));
		builder.add(new BoundPlace("P6"));
		builder.add(new BoundPlace("P7"));
		builder.add(new BoundPlace("P8"));
		builder.add(new BoundPlace("P9"));
		builder.add(new BoundPlace("P10"));
		builder.add(new EndPlace("P11"));
		
		builder.add(new ParallelSplit("T0"));
		builder.add(new LoggingTransition("T1"));
		builder.add(new LoggingTransition("T2"));
		builder.add(new ParallelSplit("T3"));
		builder.add(new LoggingTransition("T4"));
		builder.add(new LoggingTransition("T5"));
		builder.add(new LoggingTransition("T6"));
		builder.add(new SyncronizedJoin("T7"));
		builder.add(new LoggingTransition("T8"));
		builder.add(new LoggingTransition("T9"));
		
		builder.connect("P0", "T0");
		builder.connect("T0", "P1");
		builder.connect("T0", "P2");
		builder.connect("P1", "T1");
		builder.connect("P2", "T2");
		builder.connect("T1", "P3");
		builder.connect("T2", "P4");
		builder.connect("P3", "T3");
		builder.connect("P4", "T6");
		builder.connect("T3", "P5");
		builder.connect("T3", "P6");
		builder.connect("P5", "T4");
		builder.connect("P6", "T5");
		builder.connect("T4", "P8");
		builder.connect("T5", "P9");
		builder.connect("P8", "T7");
		builder.connect("P9", "T8");
		builder.connect("T8", "P10");
		builder.connect("P10", "T7");
		builder.connect("T7", "P7");
		builder.connect("T6", "P7");
		builder.connect("P7", "T9");
		builder.connect("T9", "P11");
		

		return builder.build("Concurrent", "1.0");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Expected name \"Simple\" got " + this.network.getName() ,this.network.getName(),"Simple");
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getVersion()}.
	 */
	@Test
	public void testGetVersion() {
		assertEquals("Expected verion \"1.0\" got " + this.network.getVersion() ,this.network.getVersion(),"1.0");
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getPlace(int)}.
	 */
	@Test
	public void testGetPlace() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getTransition(int)}.
	 */
	@Test
	public void testGetTransition() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getTransitionMatrix()}.
	 */
	@Test
	public void testGetTransitionMatrix() {
		logger.debug("{}",Arrays.deepToString(network.getTransitionMatrix()));
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getOutgoingTransitions(int)}.
	 */
	@Test
	public void testGetOutgoingTransitions() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.petrinet.engine.freechoice.FreeChoiceNetwork#getAllTransitions()}.
	 */
	@Test
	public void testGetAllTransitions() {
		fail("Not yet implemented"); // TODO
	}

}
