package com.grognak;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 11/29/2016.
 */
class Keypad {
    private boolean keys[];

    private static final Map<Integer, Integer> keyMap;
    static {
        Map<Integer, Integer> buildKeyMap = new HashMap<>();
        buildKeyMap.put(KeyEvent.VK_X, 0x0);
        buildKeyMap.put(KeyEvent.VK_1, 0x1);
        buildKeyMap.put(KeyEvent.VK_2, 0x2);
        buildKeyMap.put(KeyEvent.VK_3, 0x3);
        buildKeyMap.put(KeyEvent.VK_Q, 0x4);
        buildKeyMap.put(KeyEvent.VK_W, 0x5);
        buildKeyMap.put(KeyEvent.VK_E, 0x6);
        buildKeyMap.put(KeyEvent.VK_A, 0x7);
        buildKeyMap.put(KeyEvent.VK_S, 0x8);
        buildKeyMap.put(KeyEvent.VK_D, 0x9);
        buildKeyMap.put(KeyEvent.VK_Z, 0xA);
        buildKeyMap.put(KeyEvent.VK_C, 0xB);
        buildKeyMap.put(KeyEvent.VK_4, 0xC);
        buildKeyMap.put(KeyEvent.VK_R, 0xD);
        buildKeyMap.put(KeyEvent.VK_F, 0xE);
        buildKeyMap.put(KeyEvent.VK_V, 0xF);
        keyMap = Collections.unmodifiableMap(buildKeyMap);
    }

    Keypad() {
        keys = new boolean[16];

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((keyEvent) -> {
            synchronized (this) {
                int keyCode = keyEvent.getKeyCode();

                switch (keyEvent.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (keyMap.containsKey(keyCode)) {
                            keys[keyMap.get(keyCode)] = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (keyMap.containsKey(keyCode)) {
                            keys[keyMap.get(keyCode)] = false;
                        }
                        break;
                }

                return false;
            }
        });
    }

    synchronized boolean isKeyPressed(int key) {
        return keys[key];
    }

    synchronized Integer firstKeyPressed() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i]) return i;
        }

        return null;
    }
}
