/**
 * 
 */
package org.sankhya.workflow.core.engine.freeChoice;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sankhya.workflow.core.definition.Place;
import org.sankhya.workflow.core.definition.Token;
import org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork;
import org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork.Builder;
import org.sankhya.workflow.core.engine.petrinet.BoundPlace;
import org.sankhya.workflow.core.engine.petrinet.LoggingTransition;
import org.sankhya.workflow.core.engine.petrinet.Network;
import org.sankhya.workflow.core.execution.petrinet.EmptyToken;
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
		this.network = (FreeChoiceNetwork) buildNetWorkOne();
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
		
		Place start = new BoundPlace(1);
		start.addToken(new EmptyToken());
		
		builder.addPlace(start, 0);
		builder.addPlace(new BoundPlace(1), 1);
		builder.addPlace(new BoundPlace(1), 2);
		builder.addPlace(new BoundPlace(1), 3);
		
		builder.addTransition(new LoggingTransition(), 0);
		builder.addTransition(new LoggingTransition(), 1);
		builder.addTransition(new LoggingTransition(), 2);
		builder.addTransition(new LoggingTransition(), 3);
		builder.addTransition(new LoggingTransition(), 4);
		
		builder.addConnection(0, 0, true);
		builder.addConnection(1, 0, true);
		builder.addConnection(2, 1, true);
		builder.addConnection(3, 1, true);
		builder.addConnection(4, 2, true);
		
		builder.addConnection(0, 1, false);
		builder.addConnection(1, 2, false);
		builder.addConnection(2, 3, false);
		builder.addConnection(3, 3, false);
		builder.addConnection(4, 3, false);

		return builder.build("Simple", "1.0");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Expected name \"Simple\" got " + this.network.getName() ,this.network.getName(),"Simple");
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getVersion()}.
	 */
	@Test
	public void testGetVersion() {
		assertEquals("Expected verion \"1.0\" got " + this.network.getVersion() ,this.network.getVersion(),"1.0");
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getPlace(int)}.
	 */
	@Test
	public void testGetPlace() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getTransition(int)}.
	 */
	@Test
	public void testGetTransition() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getTransitionMatrix()}.
	 */
	@Test
	public void testGetTransitionMatrix() {
		logger.debug("{}",Arrays.deepToString(network.getTransitionMatrix()));
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getOutgoingTransitions(int)}.
	 */
	@Test
	public void testGetOutgoingTransitions() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.sankhya.workflow.core.engine.freeChoice.FreeChoiceNetwork#getAllTransitions()}.
	 */
	@Test
	public void testGetAllTransitions() {
		fail("Not yet implemented"); // TODO
	}

}