package net.labymod.api.test;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/InputEmulator.class */
public interface InputEmulator {
    InputEmulator pressKey(Key key);

    InputEmulator releaseKey(Key key);

    InputEmulator typeCharacter(char c);

    InputEmulator pressMouse(MouseButton mouseButton);

    InputEmulator releaseMouse(MouseButton mouseButton);

    InputEmulator moveMouse(double d, double d2);

    InputEmulator moveMouseBy(double d, double d2);

    InputEmulator scroll(double d, double d2);

    double getMouseX();

    double getMouseY();

    InputEmulator delay(long j);

    InputEmulator waitTick();

    InputEmulator waitTicks(int i);

    default InputEmulator tapKey(Key key) {
        pressKey(key);
        delay(50L);
        releaseKey(key);
        return this;
    }

    default InputEmulator holdKey(Key key, Runnable action) {
        pressKey(key);
        try {
            action.run();
            return this;
        } finally {
            releaseKey(key);
        }
    }

    default InputEmulator typeText(String text) {
        for (char c : text.toCharArray()) {
            typeCharacter(c);
            delay(20L);
        }
        return this;
    }

    default InputEmulator typeText(String text, long delayMillis) {
        for (char c : text.toCharArray()) {
            typeCharacter(c);
            delay(delayMillis);
        }
        return this;
    }

    default InputEmulator click(MouseButton button) {
        pressMouse(button);
        delay(50L);
        releaseMouse(button);
        return this;
    }

    default InputEmulator leftClick() {
        return click((MouseButton) MouseButton.LEFT);
    }

    default InputEmulator rightClick() {
        return click((MouseButton) MouseButton.RIGHT);
    }

    default InputEmulator middleClick() {
        return click((MouseButton) MouseButton.MIDDLE);
    }

    default InputEmulator doubleClick() {
        leftClick();
        delay(100L);
        leftClick();
        return this;
    }

    default InputEmulator scrollVertical(double amount) {
        return scroll(0.0d, amount);
    }

    default InputEmulator scrollUp(int clicks) {
        return scrollVertical(clicks);
    }

    default InputEmulator scrollDown(int clicks) {
        return scrollVertical(-clicks);
    }

    default InputEmulator drag(MouseButton button, double targetX, double targetY) {
        pressMouse(button);
        delay(50L);
        moveMouse(targetX, targetY);
        delay(50L);
        releaseMouse(button);
        return this;
    }

    default InputEmulator ctrlKey(Key key) {
        return holdKey(Key.L_CONTROL, () -> {
            tapKey(key);
        });
    }

    default InputEmulator shiftKey(Key key) {
        return holdKey(Key.L_SHIFT, () -> {
            tapKey(key);
        });
    }

    default InputEmulator altKey(Key key) {
        return holdKey(Key.L_ALT, () -> {
            tapKey(key);
        });
    }

    default InputEmulator escape() {
        return tapKey(Key.ESCAPE);
    }

    default InputEmulator enter() {
        return tapKey(Key.ENTER);
    }

    default InputEmulator tab() {
        return tapKey(Key.TAB);
    }

    default InputEmulator backspace() {
        return tapKey(Key.BACK);
    }

    default InputEmulator space() {
        return tapKey(Key.SPACE);
    }
}
