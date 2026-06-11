package net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.moderation.RecommendedAddon;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonRecommendationResponsePacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/moderation/AddonRecommendationTranslationListener.class */
public class AddonRecommendationTranslationListener extends KeyedTranslationListener {
    public AddonRecommendationTranslationListener() {
        super("addon_recommendation");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has("addons")) {
            return null;
        }
        JsonElement addonsElement = jsonObject.get("addons");
        if (!addonsElement.isJsonArray()) {
            return null;
        }
        List<RecommendedAddon> recommendedAddons = new ArrayList<>();
        JsonArray<JsonElement> addonsArray = addonsElement.getAsJsonArray();
        for (JsonElement addonElement : addonsArray) {
            if (addonElement.isJsonObject()) {
                JsonObject addonObject = addonElement.getAsJsonObject();
                if (addonObject.has("namespace")) {
                    String namespace = addonObject.get("namespace").getAsString();
                    recommendedAddons.add(RecommendedAddon.of(namespace, addonObject.has("required") && addonObject.get("required").getAsBoolean()));
                }
            }
        }
        if (recommendedAddons.isEmpty()) {
            return null;
        }
        return new AddonRecommendationPacket(recommendedAddons);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        if (!(packet instanceof AddonRecommendationResponsePacket)) {
            return null;
        }
        AddonRecommendationResponsePacket responsePacket = (AddonRecommendationResponsePacket) packet;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gui_closed", Boolean.valueOf(!responsePacket.isInitial()));
        jsonObject.addProperty("all_installed", Boolean.valueOf(responsePacket.isAllInstalled()));
        JsonArray missingArray = new JsonArray();
        for (String namespace : responsePacket.getMissingAddons()) {
            JsonObject missingObject = new JsonObject();
            missingObject.addProperty("namespace", namespace);
            missingArray.add(missingObject);
        }
        jsonObject.add("missing", missingArray);
        return jsonObject;
    }
}
