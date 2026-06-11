package net.labymod.v1_21_5.mixins.client.world.effect;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({bwi.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int h;

    @Shadow
    private int i;

    @Shadow
    @Final
    private jg<bwg> g;
    private hkq labyMod$iconSprite;
    private ftk labyMod$graphics;

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
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(((bwg) this.g.a()).g());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        hkq sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        hkq sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            fqq minecraft = fqq.Q();
            this.labyMod$graphics = new ftk(minecraft, minecraft.aR().d());
        }
        this.labyMod$graphics.setPose((fld) poseStackObject);
        this.labyMod$graphics.a(gry::H, sprite, x, y, width, height, -1);
        this.labyMod$graphics.d();
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.h == -1;
    }

    private hkq labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        alr resourceLocation = mh.d.b((bwg) this.g.a());
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = fqq.Q().aG().a(this.g);
        return this.labyMod$iconSprite;
    }
}
