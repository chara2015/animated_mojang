package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.IntList;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/RolesMessageListener.class */
public class RolesMessageListener implements MessageListener {
    private final IntList roles;
    private final User self;

    public RolesMessageListener(IntList roles, User self) {
        this.roles = roles;
        this.self = self;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        this.roles.clear();
        JsonArray<JsonElement> roles = (JsonArray) GSON.fromJson(message, JsonArray.class);
        for (JsonElement role : roles) {
            this.roles.add(role.getAsInt());
        }
        DefaultUser user = (DefaultUser) this.self;
        user.setLabyPlusOverride(this.roles.contains(10));
    }
}
