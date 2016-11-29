package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
public class FlowStack {
    public static final int MAX_STACK_SIZE = 16;

    private short stack[];
    private short pointer;

    public FlowStack() {
        stack = new short[MAX_STACK_SIZE];
        pointer = 0;
    }

    public void push(short programCounter) {
        try {
            stack[pointer] = programCounter;
            pointer++;
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalStateException("Attempt to store more than " + MAX_STACK_SIZE + " addresses on the stack");
        }
    }

    public short pop() {
        try {
            pointer--;
            return stack[pointer];
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalStateException("Attempt to return with no addresses in the stack");
        }
    }
}
