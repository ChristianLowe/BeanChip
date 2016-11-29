package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
public class Memory {
    public static final int MEMORY_SIZE = 4096;

    private byte[] memory;

    public Memory() {
        memory = new byte[MEMORY_SIZE];
    }
}
