package net.labymod.v1_20_1.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({enn.class})
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
    private UserApiService ay;

    @Mutable
    @Shadow
    @Final
    private ffc aK;

    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;

    @Shadow
    @Final
    private eoc W;

    @Mutable
    @Shadow
    @Final
    private eys aG;

    @Mutable
    @Shadow
    @Final
    private fzg aJ;

    @Shadow
    private ffs bt;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.ay = labyMod$createUserApiService();
        this.aK = ffc.a(this.ay, this.W, this.p.toPath());
        this.aG = new eys((enn) this, this.ay);
        this.aJ = new fzg((enn) this, this.ay, this.W);
        this.bt = ffs.a(ffq.a(), this.ay);
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
