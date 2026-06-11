package net.labymod.v1_18_2.mixins.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import net.labymod.api.client.session.SessionRefresher;
import net.labymod.v1_18_2.mojang.MojangServices;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({dyr.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Mutable
    @Shadow
    @Final
    private UserApiService av;

    @Shadow
    @Final
    private dzh W;

    @Mutable
    @Shadow
    @Final
    private ehk aE;

    @Shadow
    @Final
    private MinecraftSessionService au;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.av = MojangServices.createSocialInteractions(this.au, this.W);
        this.aE = new ehk((dyr) this, this.av);
    }
}
