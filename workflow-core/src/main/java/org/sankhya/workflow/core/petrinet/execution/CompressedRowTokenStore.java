/**
 * 
 */
package org.sankhya.workflow.core.petrinet.execution;

import java.util.Arrays;

/**
 * @author Raj Singh Sisodia
 * @since Jan 27, 2018
 *
 */
public class CompressedRowTokenStore implements TokenStore {
	
	/**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 3;
	
	private int[] placeIDs;
	private int[] tokens;
	private int size = 0;
	private int peekCounter = 0;
	
	public CompressedRowTokenStore(int initialCapacity) {
		this.placeIDs = new int[initialCapacity];
		this.tokens = new int[initialCapacity];
	}

	@Override
	public void push(int placeId) {
		int location = exists(placeId);
		if(exists(location) == -1) {
			this.ensureCapacity();
			location = size;
			size++;
			
		}
		
		this.placeIDs[location] = placeId;
		this.tokens[location] += 1;
		
		peekCounter = size-1;

	}

	@Override
	public int pop() {
		if(!this.hasNext())
			throw new IllegalStateException("Cannot provide token from an empty place.");
		
		int value = this.tokens[size-1];
		if (value < 1)
			throw new IllegalStateException("Cannot provide token from an empty place.");
		
		this.placeIDs[size-1] -= 1;
		this.tokens[size-1] -= 1;
		
		if(value == 1){
			size--;
		}
			
		peekCounter = size-1;
		return value;
	}

	@Override
	public int pop(int placeId) {
		int location = exists(placeId);
		if (location == -1)
			throw new IllegalStateException("Cannot provide token from an empty place.");
		
		
		int value = this.tokens[location];
		if (value < 1)
			throw new IllegalStateException("Cannot provide token from an empty place.");
		
		this.tokens[location] -= 1;
		
		if(value == 1)
			compressArray(location);
		
		peekCounter = size-1;
		
		return value;
	}

	@Override
	public int peek() {
		if(!this.hasNext())
			throw new IllegalStateException("Cannot provide token from an empty place.");
		int location = this.placeIDs[peekCounter];
		int value = this.tokens[peekCounter];
		if (value < 1)
			throw new IllegalStateException("Cannot provide token from an empty place.");
		
		peekCounter -= 1;
		return location;
	}

	@Override
	public int peek(int placeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int exists(int placeId) {
		for(int i=0; i< this.size;i++){
			if(this.placeIDs[i] == placeId)
				return i;
		}
		
		return -1;
	}

	@Override
	public boolean hasNext() {
		return this.size > 0 ;
	}

	private void ensureCapacity(){
		if(placeIDs.length == size)
			grow(size+1);
	}
	
	private void compressArray(int placeId) {
		int[] newPlaceIDs = new int[size - 1];
		int[] newTokens = new int[size - 1];

		for (int i = 0, j = 0; i < size; i++) {
			if (i != placeId) {
				newPlaceIDs[j] = placeIDs[i];
				newTokens[j] = tokens[i];
				j++;
			}
		}
		
		size--;
		placeIDs = newPlaceIDs;
		tokens = newTokens;
		

	}
	
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    
    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = placeIDs.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        placeIDs = Arrays.copyOf(placeIDs, newCapacity);
        tokens = Arrays.copyOf(tokens, newCapacity);
    }
}
