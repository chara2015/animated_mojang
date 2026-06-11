package net.labymod.v1_20_4.mixins.client.world.effect;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({bli.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int l;

    @Shadow
    private int m;

    @Shadow
    @Final
    private blg k;
    private gen labyMod$iconSprite;
    private ewu labyMod$graphics;

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.l;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.m;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.k.e());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        gen sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        gen sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            evi minecraft = evi.O();
            this.labyMod$graphics = new ewu(minecraft, minecraft.aO().d());
        }
        this.labyMod$graphics.setPose((eqb) poseStackObject);
        this.labyMod$graphics.a(x, y, 0, width, height, sprite);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.l == -1;
    }

    private gen labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        ahg resourceLocation = kd.d.b(this.k);
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = evi.O().aE().a(this.k);
        return this.labyMod$iconSprite;
    }
}
