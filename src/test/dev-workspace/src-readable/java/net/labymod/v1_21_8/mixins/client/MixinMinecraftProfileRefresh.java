package net.labymod.v1_21_8.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({fue.class})
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
    private UserApiService aB;

    @Mutable
    @Shadow
    @Final
    private grx aQ;

    @Shadow
    @Final
    private YggdrasilAuthenticationService az;

    @Shadow
    @Final
    private fuq aa;

    @Mutable
    @Shadow
    @Final
    private gkc aN;

    @Shadow
    private gst bw;

    @Mutable
    @Shadow
    @Final
    private hxb aP;

    @Shadow
    @Final
    private MinecraftSessionService aA;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.aB = labyMod$createUserApiService();
        this.aQ = grx.a(this.aB, this.aa, this.q.toPath());
        this.aN = new gkc((fue) this, this.aB);
        this.aP = new hxb((fue) this, this.aB, this.aa);
        this.bw = gst.a(gsq.a(), this.aB);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.aA.fetchProfile(this.aa.b(), true);
        }, ag.j());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().getId();
        UUID currentId = this.aa.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    private UserApiService labyMod$createUserApiService() {
        return this.az.createUserApiService(this.aa.d());
    }
}
