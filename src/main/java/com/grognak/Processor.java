package com.grognak;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Chris on 11/28/2016.
 */
class Processor {
    private final Memory memory;
    private final Registers registers;
    private final Timers timers;
    private final FrameBuffer frameBuffer;
    private final Keypad keypad;
    private final FlowStack flowStack;

    Processor(byte[] program) {
        memory = new Memory(program);
        registers = new Registers();
        timers = new Timers();
        frameBuffer = new FrameBuffer();
        keypad = new Keypad();
        flowStack = new FlowStack();
    }

    void tick() {
        int programCounter = registers.getProgramCounter();
        int opcode = (memory.getByte(programCounter) << 8) | memory.getByte(programCounter + 1);

        int x = (opcode & 0x0F00) >> 8;
        int y = (opcode & 0x00F0) >> 4;
        int n = opcode & 0x000F;
        int nn = opcode & 0x00FF;
        int nnn = opcode & 0x0FFF;

        int vx = registers.getGeneral(x);
        int vy = registers.getGeneral(y);

        switch (opcode & 0xF000) {
            case 0x0000:
                if (opcode == 0x00E0) {
                    frameBuffer.reset();
                } else if (opcode == 0x00EE) {
                    programCounter = flowStack.pop();
                } else {
                    throw new UnsupportedOperationException("Calls RCA 1802 program. Not necessary for most ROMs.");
                }

                programCounter += 2;
                break;

            case 0x1000:
                programCounter = nnn;
                break;

            case 0x2000:
                flowStack.push(programCounter);
                programCounter = nnn;
                break;

            case 0x3000:
                if (vx == nn) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
                break;


            case 0x4000:
                if (vy != nn) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
                break;

            case 0x5000:
                if (vx == vy) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
                break;

            case 0x6000:
                registers.setGeneral(x, nn);
                programCounter += 2;
                break;

            case 0x7000:
                registers.setGeneral(x, vx + nn);
                programCounter += 2;
                break;

            case 0x8000:
                int newValue;

                switch (opcode & 0x000F) {
                    case 0x0: newValue = vy; break;
                    case 0x1: newValue = vx | vy; break;
                    case 0x2: newValue = vx & vy; break;
                    case 0x3: newValue = vx ^ vy; break;

                    case 0x4:
                        newValue = vx + vy;
                        registers.setFlag((newValue & 0xFF) != newValue);
                        break;

                    case 0x5:
                        newValue = vx - vy;
                        registers.setFlag(newValue >= 0);
                        break;

                    case 0x6:
                        registers.setFlag((vy & 1) == 1);
                        newValue = vy >> 1;
                        break;

                    case 0x7:
                        newValue = vy - vx;
                        registers.setFlag(newValue >= 0);
                        break;

                    case 0xE:
                        newValue = vy << 1;
                        registers.setFlag((newValue & 0x100) != 0);
                        newValue &= 0xFF;
                        registers.setGeneral(y, newValue);
                        break;

                    default:
                        throw new UnsupportedOperationException("Undocumented math instruction.");
                }

                registers.setGeneral(x, newValue);
                programCounter += 2;
                break;

            case 0x9000:
                if (vx != vy) {
                    programCounter += 4;
                } else {
                    programCounter += 2;
                }
                break;

            case 0xA000:
                registers.setIndex(nnn);
                programCounter += 2;
                break;

            case 0xB000:
                programCounter = registers.getGeneral(0) + nnn;
                break;

            case 0xC000:
                registers.setGeneral(x, ThreadLocalRandom.current().nextInt() & nn);
                programCounter += 2;
                break;

            case 0xD000:
                registers.setFlag(false);

                for (int spriteY = 0; spriteY < n; spriteY++) {
                    int pointer = registers.getIndex() + spriteY;
                    int spriteLine = memory.getByte(pointer);

                    for (int spriteX = 7; spriteX >= 0; spriteX--) {
                        boolean currentBit = frameBuffer.getPixel(vx + spriteX, vy + spriteY);
                        boolean spriteBit = (spriteLine & 1) == 1;
                        spriteLine >>= 1;

                        if (spriteBit && currentBit) {
                            registers.setFlag(true);
                        }

                        frameBuffer.setPixel(vx + spriteX, vy + spriteY, spriteBit ^ currentBit);
                    }
                }
                programCounter += 2;
                break;

            case 0xE000:
                switch (opcode & 0x00FF) {
                    case 0x9E: if (keypad.isKeyPressed(vx)) programCounter += 2; break;
                    case 0xA1: if (!keypad.isKeyPressed(vx)) programCounter += 2; break;
                    default: throw new UnsupportedOperationException("Unknown opcode.");
                }
                programCounter += 2;
                break;

            case 0xF000:
                switch (opcode & 0x00FF) {
                    case 0x07:
                        registers.setGeneral(x, timers.getDelayTimer());
                        break;
                    case 0x0A:
                        Integer key = keypad.firstKeyPressed();
                        if (key != null) {
                            registers.setGeneral(x, key);
                        } else {
                            programCounter -= 2; // Wait for key to be pressed.
                        }
                        break;
                    case 0x15:
                        timers.setDelayTimer(vx);
                        break;
                    case 0x18:
                        timers.setSoundTimer(vx);
                        break;
                    case 0x1E:
                        registers.setIndex(registers.getIndex() + vx);
                        break;
                    case 0x29:
                        registers.setIndex(Memory.MemoryOffset.FONT.address + (vx * 5));
                        break;
                    case 0x33:
                        memory.setBCD(registers.getIndex(), vx);
                        break;
                    case 0x55:
                        for (int i = 0; i <= x; i++) {
                            memory.setByte(registers.getIndex(), registers.getGeneral(i));
                            registers.bumpIndex();
                        }
                        break;
                    case 0x65:
                        for (int i = 0; i <= x; i++) {
                            registers.setGeneral(i, memory.getByte(registers.getIndex()));
                            registers.bumpIndex();
                        }
                        break;
                }
                programCounter += 2;
                break;

            default:
                throw new IllegalStateException("Unreachable code");
        }

        registers.setProgramCounter(programCounter);
        timers.tick();
    }

    FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }
}
