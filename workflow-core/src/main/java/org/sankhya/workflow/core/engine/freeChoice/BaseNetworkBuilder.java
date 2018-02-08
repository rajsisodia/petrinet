package org.sankhya.workflow.core.engine.freeChoice;

import java.util.HashMap;
import java.util.Map;

import org.sankhya.workflow.core.IllegalStateException;
import org.sankhya.workflow.core.definition.Node;
import org.sankhya.workflow.core.engine.AbstractNode;
import org.sankhya.workflow.core.petrinet.NetworkBuilder;
import org.sankhya.workflow.core.petrinet.Place;
import org.sankhya.workflow.core.petrinet.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseNetworkBuilder implements NetworkBuilder {

	private final static Logger logger = LoggerFactory.getLogger(BaseNetworkBuilder.class);

	private Map<String, Transition> transitions = new HashMap<>();
	private Map<String, Place> places = new HashMap<>();
	private int transitionCounter = 0;
	private int placeCounter = 0;

	/*
	 * This is an m x n (m rows, n columns) matrix, where m is the number of
	 * transitions and n is the number of places in the network.
	 * 
	 * For each position [i,j] in the matrix, a value of 1 denotes an input
	 * place from position j, on transition i. 0 denotes no input from the
	 * position on the transition.
	 */
	private int[][] transitionInputs;

	/*
	 * This is an m x n (m rows, n columns) matrix, where m is the number of
	 * transitions and n is the number of places in the network.
	 * 
	 * For each position [i,j] in the matrix, a value of 1 denotes an output
	 * from transition i to position j. O denotes no output to the place j from
	 * i.
	 */
	private int[][] transitionOutputs;

	@Override
	public NetworkBuilder add(String name, Node<?> node) {

		if (transitions.containsKey(name) || places.containsKey(name))
			throw new IllegalStateException("Node with name \"" + name + "\" already added");

		if (node instanceof Transition) {
			if (node.getId() < 0)
				node.setId(transitionCounter);
			transitions.put(name, (Transition) node);
			logger.trace("Added Transition [{}] with id [{}] to network", node.getName(), node.getId());

			transitionCounter++;

		} else if (node instanceof Place) {
			if (node.getId() < 0)
				node.setId(placeCounter);
			places.put(name, (Place) node);
			logger.trace("Added Place [{}] with id [{}] to network", node.getName(), node.getId());

			placeCounter++;
		} else {
			throw new IllegalStateException("Can't recognize node of type " + node.getClass().getName());
		}

		return this;
	}

	@Override
	public NetworkBuilder connect(Node<?> from, Node<?> to) {

		if (from instanceof Place) {
			if (!places.containsValue(from))
				throw new IllegalStateException(
						"Place " + from.getName() + " not added to network, cannot create connection.");
			if (!transitions.containsValue(to))
				throw new IllegalStateException(
						"Transition " + to.getName() + " not added to network, cannot create connection.");

			transitionInputs = resizeMatrix(transitionInputs, to.getId(), from.getId());
			transitionInputs[to.getId()][from.getId()] = 1;
			
			if(from.getOutgoing() == null)
				((Place)from).setOutgoing(new Transition[0]);
			if(to.getIncoming() == null)
				((Transition)to).setIncoming(new Place[0]);
			
			((AbstractNode<Node<?>>) from).setOutgoing((Transition)to);
			((AbstractNode<Node<?>>) to).setIncoming((Place)from);

			logger.trace("Connecting Place [{}]:[{}] with Transition [{}]:[{}]", from.getName(), from.getId(),
					to.getName(), to.getId());

		} else {
			if (!transitions.containsValue(from))
				throw new IllegalStateException(
						"Transition " + from.getName() + " not added to network, cannot create connection.");
			if (!places.containsValue(to))
				throw new IllegalStateException(
						"Place " + to.getName() + " not added to network, cannot create connection.");

			transitionOutputs = resizeMatrix(transitionOutputs, to.getId(), from.getId());
			transitionOutputs[to.getId()][from.getId()] = 1;

			if(from.getOutgoing() == null)
				((Transition)from).setOutgoing(new Place[0]);
			if(to.getIncoming() == null)
				((Place)to).setIncoming(new Transition[0]);
			
			((AbstractNode<Node<?>>) from).setOutgoing((Place)to);
			((AbstractNode<Node<?>>) to).setIncoming((Transition)from);
			
			logger.trace("Connecting Transition [{}]:[{}] with place [{}]:[{}]", from.getName(), from.getId(),
					to.getName(), to.getId());
		}

		return this;
	}

	public int[][] getTransitionMatrix() {
		return subtract(transitionOutputs, transitionInputs);
	}

	public Map<String, Place> getPlaces() {
		return places;
	}

	public Map<String, Transition> getTransitions() {
		return transitions;
	}

	protected int[][] resizeMatrix(int[][] matrix, int i, int j) {
		if (matrix == null)
			matrix = new int[0][0];

		int iLen = matrix.length;
		int jLen = matrix[0].length;

		if (i < iLen && j < jLen)
			return matrix;

		int iNew = (iLen > i) ? iLen : i;
		int jNew = (jLen > j) ? jLen : j;

		int[][] newMatrix = new int[iNew][jNew];

		for (int iCount = 0; iCount < iLen; iCount++)
			for (int jCount = 0; jCount < jLen; jCount++)
				newMatrix[iCount][jCount] = matrix[iCount][jCount];

		return newMatrix;

	}

	protected int[][] subtract(int[][] a, int[][] b) {
		int rows = a.length;
		int columns = a[0].length;
		int[][] result = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				result[i][j] = a[i][j] - b[i][j];
			}
		}
		return result;
	}
}
