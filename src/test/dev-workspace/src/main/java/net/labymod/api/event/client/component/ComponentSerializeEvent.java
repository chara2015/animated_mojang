package net.labymod.api.event.client.component;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/component/ComponentSerializeEvent.class */
@LabyEvent(allowExceptions = {IllegalArgumentException.class})
public class ComponentSerializeEvent implements Event {
    private final Component component;

    @Nullable
    @ApiStatus.Experimental
    private Component unwrappedComponent;
    private JsonObject json;

    public ComponentSerializeEvent(@NotNull Component component) {
        this.component = component;
    }

    @NotNull
    public Component component() {
        return this.component;
    }

    @Nullable
    public JsonObject getJson() {
        return this.json;
    }

    public boolean wasSerialized() {
        return this.json != null;
    }

    public void serialize(@NotNull Consumer<JsonObject> consumer) {
        if (this.json != null) {
            throw new IllegalStateException("This component has already been serialized");
        }
        this.json = new JsonObject();
        consumer.accept(this.json);
    }

    @Nullable
    @ApiStatus.Experimental
    public Component getUnwrappedComponent() {
        return this.unwrappedComponent;
    }

    @ApiStatus.Experimental
    public void setUnwrappedComponent(@Nullable Component unwrappedComponent) {
        this.unwrappedComponent = unwrappedComponent;
    }
}
