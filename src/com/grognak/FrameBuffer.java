package com.grognak;

/**
 * Created by Chris on 11/28/2016.
 */
public class FrameBuffer {
    public static final int BUFFER_WIDTH = 64;
    public static final int BUFFER_HEIGHT = 32;

    private boolean[][] pixels;

    public FrameBuffer() {
        pixels = new boolean[BUFFER_WIDTH][BUFFER_HEIGHT];
    }

    public void flipPixel(int x, int y) {
        pixels[x][y] = !pixels[x][y];
    }

    public boolean getPixel(int x, int y) {
        return pixels[x][y];
    }
}
