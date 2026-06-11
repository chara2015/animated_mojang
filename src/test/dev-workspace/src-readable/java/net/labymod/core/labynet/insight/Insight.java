package net.labymod.core.labynet.insight;

import com.google.gson.JsonObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/Insight.class */
public interface Insight {
    JsonObject toJsonObject();

    void fromJsonObject(JsonObject jsonObject);
}
