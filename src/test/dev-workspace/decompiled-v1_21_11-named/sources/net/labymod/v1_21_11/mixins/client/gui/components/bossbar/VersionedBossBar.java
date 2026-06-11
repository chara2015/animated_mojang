package net.labymod.v1_21_11.mixins.client.gui.components.bossbar;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.world.BossEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/bossbar/VersionedBossBar.class */
@Mixin({LerpingBossEvent.class})
@Implements({@Interface(iface = BossBar.class, prefix = "bossBar$", remap = Interface.Remap.NONE)})
public abstract class VersionedBossBar extends BossEvent implements BossBar {
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;

    @Shadow
    public abstract void setProgress(float f);

    private VersionedBossBar(UUID param0, net.minecraft.network.chat.Component param1, BossEvent.BossBarColor param2, BossEvent.BossBarOverlay param3) {
        super(param0, param1, param2, param3);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::setProgress, this::getProgress);
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$init(UUID $$0, net.minecraft.network.chat.Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7, CallbackInfo ci) {
        labyMod$init();
    }

    private void labyMod$init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(getName());
        this.labyMod$bossBarColor = BossBarColor.getByName(getColor().getName());
        this.labyMod$bossBarOverlay = BossBarOverlay.getByName(getOverlay().getName());
    }

    public void setName(net.minecraft.network.chat.Component $$0) {
        super.setName($$0);
        labyMod$init();
    }

    public void setColor(BossEvent.BossBarColor $$0) {
        super.setColor($$0);
        labyMod$init();
    }

    public void setOverlay(BossEvent.BossBarOverlay $$0) {
        super.setOverlay($$0);
        labyMod$init();
    }

    @NotNull
    public UUID getIdentifier() {
        return getId();
    }

    public Component displayName() {
        return this.labyMod$displayName;
    }

    public BossBarColor bossBarColor() {
        return this.labyMod$bossBarColor;
    }

    public BossBarOverlay bossBarOverlay() {
        return this.labyMod$bossBarOverlay;
    }

    public BossBarProgressHandler progressHandler() {
        return this.labyMod$progressHandler;
    }
}
