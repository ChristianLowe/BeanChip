package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
public class Registers {
    public static final int NUMBER_OF_GENERAL_REGISTERS = 16;

    private byte[] general;
    private short index;

    public Registers() {
        general = new byte[NUMBER_OF_GENERAL_REGISTERS];
        index = 0;
    }
}
