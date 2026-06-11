package net.labymod.api.labyconnect.protocol.model;

import java.util.UUID;
import net.labymod.api.user.GameUser;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/User.class */
public interface User {
    UUID getUniqueId();

    String getName();

    boolean isSelf();

    @NotNull
    UserStatus userStatus();

    void updateStatus(@NotNull UserStatus userStatus);

    GameUser gameUser();

    boolean isLabyPlus();
}
