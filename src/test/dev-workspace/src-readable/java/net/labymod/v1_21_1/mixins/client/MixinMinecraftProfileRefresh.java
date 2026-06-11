package net.labymod.v1_21_1.mixins.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.session.SessionRefresher;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({fgo.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Shadow
    @Final
    private static Logger F;

    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;

    @Shadow
    @Final
    public File p;

    @Mutable
    @Shadow
    @Final
    private UserApiService aw;

    @Mutable
    @Shadow
    @Final
    private fzr aL;

    @Shadow
    @Final
    private YggdrasilAuthenticationService au;

    @Shadow
    @Final
    private fhb V;

    @Mutable
    @Shadow
    @Final
    private fsu aH;

    @Shadow
    private gao bs;

    @Mutable
    @Shadow
    @Final
    private gvj aK;

    @Shadow
    @Final
    private MinecraftSessionService av;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.aw = labyMod$createUserApiService();
        this.aL = fzr.a(this.aw, this.V, this.p.toPath());
        this.aH = new fsu((fgo) this, this.aw);
        this.aK = new gvj((fgo) this, this.aw, this.V);
        this.bs = gao.a(gal.a(), this.aw);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.av.fetchProfile(this.V.b(), true);
        }, ad.i());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().getId();
        UUID currentId = this.V.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    private UserApiService labyMod$createUserApiService() {
        return this.au.createUserApiService(this.V.d());
    }
}
