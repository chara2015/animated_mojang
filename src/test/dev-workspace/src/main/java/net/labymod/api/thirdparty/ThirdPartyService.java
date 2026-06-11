package net.labymod.api.thirdparty;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.thirdparty.discord.DiscordApp;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/thirdparty/ThirdPartyService.class */
@Referenceable
public interface ThirdPartyService {
    void initialize();

    DiscordApp discord();
}
