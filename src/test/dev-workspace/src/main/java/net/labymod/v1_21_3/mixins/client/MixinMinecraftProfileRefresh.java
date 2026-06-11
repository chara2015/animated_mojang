package net.labymod.v1_21_3.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({fmg.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Shadow
    @Final
    private static Logger G;

    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;

    @Shadow
    @Final
    public File q;

    @Mutable
    @Shadow
    @Final
    private UserApiService aA;

    @Mutable
    @Shadow
    @Final
    private gfx aR;

    @Shadow
    @Final
    private YggdrasilAuthenticationService ay;

    @Shadow
    @Final
    private fms Y;

    @Mutable
    @Shadow
    @Final
    private fyo aN;

    @Shadow
    private ggt bx;

    @Mutable
    @Shadow
    @Final
    private hgr aQ;

    @Shadow
    @Final
    private MinecraftSessionService az;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.aA = labyMod$createUserApiService();
        this.aR = gfx.a(this.aA, this.Y, this.q.toPath());
        this.aN = new fyo((fmg) this, this.aA);
        this.aQ = new hgr((fmg) this, this.aA, this.Y);
        this.bx = ggt.a(ggq.a(), this.aA);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.az.fetchProfile(this.Y.b(), true);
        }, ae.i());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().getId();
        UUID currentId = this.Y.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    private UserApiService labyMod$createUserApiService() {
        return this.ay.createUserApiService(this.Y.d());
    }
}
