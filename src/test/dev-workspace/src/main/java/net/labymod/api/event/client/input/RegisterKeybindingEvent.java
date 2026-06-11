package net.labymod.api.event.client.input;

import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/input/RegisterKeybindingEvent.class */
public class RegisterKeybindingEvent extends DefaultCancellable implements Event {
    private final String name;
    private String category;
    private Supplier<Widget> replacement;

    public RegisterKeybindingEvent(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        Objects.requireNonNull(category, "category must not be null");
        this.category = category;
    }

    public Supplier<Widget> getReplacement() {
        return this.replacement;
    }

    public void setReplacement(Supplier<Widget> replacement) {
        this.replacement = replacement;
        setCancelled(false);
    }

    @Override // net.labymod.api.event.DefaultCancellable, net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        super.setCancelled(cancelled);
    }
}
