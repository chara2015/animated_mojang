package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/CharacterTypedEvent.class */
public class CharacterTypedEvent extends DefaultCancellable implements Event {
    private final Key key;
    private final char character;

    public CharacterTypedEvent(Key key, char character) {
        this.key = key;
        this.character = character;
    }

    @NotNull
    public Key key() {
        return this.key;
    }

    public char getCharacter() {
        return this.character;
    }
}
