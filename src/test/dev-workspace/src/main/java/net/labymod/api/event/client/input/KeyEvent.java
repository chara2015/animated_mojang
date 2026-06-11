package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/KeyEvent.class */
public class KeyEvent extends DefaultCancellable implements Event {
    private final State state;
    private final Key key;
    private final InputType inputType;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/KeyEvent$State.class */
    public enum State {
        UNPRESSED,
        PRESS,
        HOLDING
    }

    public KeyEvent(State state, Key key) {
        this.state = state;
        this.key = key;
        this.inputType = KeyMapper.getInputType(key);
    }

    public State state() {
        return this.state;
    }

    public Key key() {
        return this.key;
    }

    public InputType inputType() {
        return this.inputType;
    }
}
