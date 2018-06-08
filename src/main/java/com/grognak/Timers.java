package com.grognak;

/**
 * Created by Chris on 11/29/2016.
 */
class Timers {
    private static final long TIME_BETWEEN_TICKS = 1_000 / 60;

    private long lastTick;

    private int delayTimer;
    private int soundTimer;

    Timers() {
        lastTick = System.currentTimeMillis();

        delayTimer = 0;
        soundTimer = 0;
    }

    int getDelayTimer() {
        return delayTimer;
    }

    void setDelayTimer(int ticks) {
        delayTimer = ticks;
    }

    void setSoundTimer(int ticks) {
        soundTimer = ticks;
    }

    void tick() {
        long currentTime = System.currentTimeMillis();

        if (currentTime > lastTick + TIME_BETWEEN_TICKS) {
            if (delayTimer > 0) {
                delayTimer--;
            }

            if (soundTimer > 0) {
                soundTimer--;
            }

            lastTick = currentTime;
        }
    }
}
