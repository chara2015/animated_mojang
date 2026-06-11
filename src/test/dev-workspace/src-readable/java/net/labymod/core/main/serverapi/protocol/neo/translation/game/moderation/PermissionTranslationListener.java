package net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.moderation.Permission;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/moderation/PermissionTranslationListener.class */
public class PermissionTranslationListener extends KeyedTranslationListener {
    public PermissionTranslationListener() {
        super("PERMISSIONS");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        JsonObject permissionObject = messageContent.getAsJsonObject();
        List<Permission.StatedPermission> permissions = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : permissionObject.entrySet()) {
            String key = entry.getKey();
            if (key != null && !key.isEmpty()) {
                Permission permission = Permission.of(key.toLowerCase(Locale.ENGLISH));
                permissions.add(entry.getValue().getAsBoolean() ? permission.allow() : permission.deny());
            }
        }
        return new PermissionPacket(permissions);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
