package net.labymod.v1_21.mixins.client.world.effect;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.effect.PotionEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({brz.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int h;

    @Shadow
    private int i;

    @Shadow
    @Final
    private jm<brx> g;
    private gql labyMod$iconSprite;
    private fhz labyMod$graphics;

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.h;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.i;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(((brx) this.g.a()).e());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        gql sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        gql sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            fgo minecraft = fgo.Q();
            this.labyMod$graphics = new fhz(minecraft, minecraft.aO().d());
        }
        this.labyMod$graphics.setPose((fbi) poseStackObject);
        this.labyMod$graphics.a(x, y, 0, width, height, sprite);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.h == -1;
    }

    private gql labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        akr resourceLocation = lt.d.b((brx) this.g.a());
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = fgo.Q().aE().a(this.g);
        return this.labyMod$iconSprite;
    }
}
