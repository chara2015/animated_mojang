package net.labymod.v1_19_4.mixins.client;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import net.labymod.api.client.session.SessionRefresher;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({emh.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Shadow
    @Final
    private static Logger G;

    @Shadow
    @Final
    public File p;

    @Mutable
    @Shadow
    @Final
    private UserApiService az;

    @Mutable
    @Shadow
    @Final
    private fdp aL;

    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;

    @Shadow
    @Final
    private emw W;

    @Mutable
    @Shadow
    @Final
    private exf aH;

    @Mutable
    @Shadow
    @Final
    private fxn aK;

    @Shadow
    private fef bt;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.az = labyMod$createUserApiService();
        this.aL = fdp.a(this.az, this.W, this.p.toPath());
        this.aH = new exf((emh) this, this.az);
        this.aK = new fxn((emh) this, this.az, this.W);
        this.bt = fef.a(fed.a(), this.az);
    }

    private UserApiService labyMod$createUserApiService() {
        try {
            return this.aw.createUserApiService(this.W.d());
        } catch (AuthenticationException exception) {
            G.error("Failed to verify authentication", exception);
            return UserApiService.OFFLINE;
        }
    }
}
