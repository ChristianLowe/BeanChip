package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
class Memory {
    public enum MemoryOffset {
        FONT    (0x050),
        PROGRAM (0x200);

        public final int address;

        MemoryOffset(int address) {
            this.address = address;
        }
    }

    private static final int[] symbols = {
            0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
            0x20, 0x60, 0x20, 0x20, 0x70, // 1
            0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
            0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
            0x90, 0x90, 0xF0, 0x10, 0x10, // 4
            0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
            0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
            0xF0, 0x10, 0x20, 0x40, 0x40, // 7
            0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
            0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
            0xF0, 0x90, 0xF0, 0x90, 0x90, // A
            0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
            0xF0, 0x80, 0x80, 0x80, 0xF0, // C
            0xE0, 0x90, 0x90, 0x90, 0xE0, // D
            0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
            0xF0, 0x80, 0xF0, 0x80, 0x80, // F
    };

    private static final int MEMORY_SIZE = 4096;

    private int[] memory;

    Memory(byte[] program) {
        memory = new int[MEMORY_SIZE];

        for (int i = 0; i < symbols.length; i++) {
            int offset = MemoryOffset.FONT.address + i;
            setByte(offset, symbols[i]);
        }

        for (int i = 0; i < program.length; i++) {
            int offset = MemoryOffset.PROGRAM.address + i;
            setByte(offset, program[i]);
        }
    }

    int getByte(int index) {
        return memory[index] & 0xFF;
    }

    void setByte(int index, int value) {
        memory[index] = value & 0xFF;
    }

    void setBCD(int index, int value) {
        setByte(index, value / 100);
        setByte(index + 1, (value / 10) % 10);
        setByte(index + 2, (value % 100) % 10);
    }
}
