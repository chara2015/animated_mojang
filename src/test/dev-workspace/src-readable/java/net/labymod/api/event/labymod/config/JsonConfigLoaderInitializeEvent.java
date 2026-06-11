package net.labymod.api.event.labymod.config;

import com.google.gson.GsonBuilder;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/config/JsonConfigLoaderInitializeEvent.class */
@LabyEvent(background = true)
public class JsonConfigLoaderInitializeEvent implements Event {
    private final GsonBuilder gsonBuilder;

    public JsonConfigLoaderInitializeEvent(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }

    public GsonBuilder getGsonBuilder() {
        return this.gsonBuilder;
    }
}
