package net.labymod.v1_8_9.client.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import net.labymod.api.client.resources.CompletableResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/player/PlayerSkinAccessor.class */
public interface PlayerSkinAccessor {
    Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables();
}
