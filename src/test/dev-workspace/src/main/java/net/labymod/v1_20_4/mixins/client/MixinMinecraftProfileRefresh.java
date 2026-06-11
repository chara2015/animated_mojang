package net.labymod.v1_20_4.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({evi.class})
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
    private UserApiService ay;

    @Mutable
    @Shadow
    @Final
    private foc aM;

    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;

    @Shadow
    @Final
    private evx W;

    @Mutable
    @Shadow
    @Final
    private fhj aI;

    @Shadow
    private fow bv;

    @Mutable
    @Shadow
    @Final
    private gji aL;

    @Shadow
    @Final
    private MinecraftSessionService ax;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.ay = labyMod$createUserApiService();
        this.aM = foc.a(this.ay, this.W, this.p.toPath());
        this.aI = new fhj((evi) this, this.ay);
        this.aL = new gji((evi) this, this.ay, this.W);
        this.bv = fow.a(fot.a(), this.ay);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.ax.fetchProfile(this.W.b(), true);
        }, ac.h());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().getId();
        UUID currentId = this.W.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    private UserApiService labyMod$createUserApiService() {
        return this.aw.createUserApiService(this.W.d());
    }
}
