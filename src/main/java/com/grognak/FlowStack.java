package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
class FlowStack {
    private static final int MAX_STACK_SIZE = 16;

    private int stack[];
    private int pointer;

    FlowStack() {
        stack = new int[MAX_STACK_SIZE];
        pointer = 0;
    }

    void push(int programCounter) {
        try {
            stack[pointer] = programCounter;
            pointer++;
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalStateException("Attempt to store more than " + MAX_STACK_SIZE + " addresses on the stack");
        }
    }

    int pop() {
        try {
            pointer--;
            return stack[pointer];
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalStateException("Attempt to return with no addresses in the stack");
        }
    }
}
