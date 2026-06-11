package net.labymod.v1_20_6.mixins.client.gui.components.bossbar;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/components/bossbar/VersionedBossBar.class */
@Mixin({fhu.class})
@Implements({@Interface(iface = BossBar.class, prefix = "bossBar$", remap = Interface.Remap.NONE)})
public abstract class VersionedBossBar extends bqm implements BossBar {
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;

    @Shadow
    public abstract void a(float f);

    private VersionedBossBar(UUID param0, xp param1, a param2, b param3) {
        super(param0, param1, param2, param3);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::a, this::j);
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$init(UUID $$0, xp $$1, float $$2, a $$3, b $$4, boolean $$5, boolean $$6, boolean $$7, CallbackInfo ci) {
        labyMod$init();
    }

    private void labyMod$init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(i());
        this.labyMod$bossBarColor = BossBarColor.getByName(k().b());
        this.labyMod$bossBarOverlay = BossBarOverlay.getByName(l().a());
    }

    public void a(xp $$0) {
        super.a($$0);
        labyMod$init();
    }

    public void a(a $$0) {
        super.a($$0);
        labyMod$init();
    }

    public void a(b $$0) {
        super.a($$0);
        labyMod$init();
    }

    @Override // net.labymod.api.client.world.BossBar
    @NotNull
    public UUID getIdentifier() {
        return h();
    }

    @Override // net.labymod.api.client.world.BossBar
    public Component displayName() {
        return this.labyMod$displayName;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarColor bossBarColor() {
        return this.labyMod$bossBarColor;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarOverlay bossBarOverlay() {
        return this.labyMod$bossBarOverlay;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarProgressHandler progressHandler() {
        return this.labyMod$progressHandler;
    }
}
