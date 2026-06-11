package com.mojang.realmsclient.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Objects;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsText.class */
public class RealmsText {
    private static final String TRANSLATION_KEY = "translationKey";
    private static final String ARGS = "args";
    private final String translationKey;
    private final String[] args;

    private RealmsText(String $$0, String[] $$1) {
        this.translationKey = $$0;
        this.args = $$1;
    }

    public Component createComponent(Component $$0) {
        return (Component) Objects.requireNonNullElse(createComponent(), $$0);
    }

    public Component createComponent() {
        if (!I18n.exists(this.translationKey)) {
            return null;
        }
        if (this.args == null) {
            return Component.translatable(this.translationKey);
        }
        return Component.translatable(this.translationKey, this.args);
    }

    public static RealmsText parse(JsonObject $$0) {
        String[] $$5;
        String $$1 = JsonUtils.getRequiredString(TRANSLATION_KEY, $$0);
        JsonElement $$2 = $$0.get(ARGS);
        if ($$2 == null || $$2.isJsonNull()) {
            $$5 = null;
        } else {
            JsonArray $$4 = $$2.getAsJsonArray();
            $$5 = new String[$$4.size()];
            for (int $$6 = 0; $$6 < $$4.size(); $$6++) {
                $$5[$$6] = $$4.get($$6).getAsString();
            }
        }
        return new RealmsText($$1, $$5);
    }

    public String toString() {
        return this.translationKey;
    }
}
