package com.grognak;

/**
 * Created by Chris on 11/29/2016.
 */
public class Timers {
    private byte delayTimer;
    private byte soundTimer;

    public Timers() {
        delayTimer = 0;
        soundTimer = 0;
    }

    public void setDelayTimer(byte ticks) {
        delayTimer = ticks;
    }

    public boolean isDelayTimerZero() {
        return (delayTimer == 0);
    }

    public void setSoundTimer(byte ticks) {
        soundTimer = ticks;
    }

    public boolean isSoundTimerZero() {
        return (soundTimer == 0);
    }

    public void tick() {
        if (delayTimer > 0) {
            delayTimer--;
        }

        if (soundTimer > 0) {
            soundTimer--;
        }
    }
}
