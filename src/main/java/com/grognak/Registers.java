package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
class Registers {
    private static final int GENERAL_REGISTER_COUNT = 16;
    private static final int FLAG_REGISTER = 15;

    private int[] general;
    private int index;
    private int programCounter;

    Registers() {
        general = new int[GENERAL_REGISTER_COUNT];
        index = 0;
        programCounter = Memory.MemoryOffset.PROGRAM.address;
    }

    int getGeneral(int registerName) {
        return general[registerName];
    }

    void setGeneral(int registerName, int value) {
        this.general[registerName] = Math.floorMod(value, 256);
    }

    void setFlag(boolean enable) {
        setGeneral(FLAG_REGISTER, enable? 1 : 0);
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = Math.floorMod(index, 65535);
    }

    void bumpIndex() {
        setIndex(getIndex() + 1);
    }

    int getProgramCounter() {
        return programCounter;
    }

    void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }
}
