package net.labymod.v1_16_5.mixins.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import net.labymod.api.client.session.SessionRefresher;
import net.labymod.v1_16_5.mojang.MojangServices;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({djz.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Mutable
    @Shadow
    @Final
    private SocialInteractionsService au;

    @Shadow
    @Final
    private dkm W;

    @Mutable
    @Shadow
    @Final
    private dsa aD;

    @Shadow
    @Final
    private MinecraftSessionService at;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.au = MojangServices.createSocialInteractions(this.at, this.W);
        this.aD = new dsa((djz) this, this.au);
    }
}
