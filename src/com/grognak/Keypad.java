package com.grognak;

/**
 * Created by Chris on 11/29/2016.
 */
public class Keypad {
    boolean key[];

    public Keypad() {
        // TODO: Magic number 16 until I make the enum for this
        key = new boolean[16];
    }
}
