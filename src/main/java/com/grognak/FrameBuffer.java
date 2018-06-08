package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
class FrameBuffer {
    private static final int BUFFER_WIDTH = 64;
    private static final int BUFFER_HEIGHT = 32;
    private static final int BUFFER_SIZE = BUFFER_WIDTH * BUFFER_HEIGHT;

    private boolean[] pixels;

    FrameBuffer() {
        reset();
    }

    void reset() {
        pixels = new boolean[BUFFER_SIZE];
    }

    void setPixel(int x, int y, boolean value) {
        pixels[getIndex(x, y)] = value;
    }

    boolean getPixel(int x, int y) {
        return pixels[getIndex(x, y)];
    }

    private int getIndex(int x, int y) {
        return Math.floorMod((BUFFER_WIDTH * y) + x, BUFFER_SIZE);
    }
}
