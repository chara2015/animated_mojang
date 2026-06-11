package net.labymod.v1_19_4.mixins.client.world.effect;

import com.mojang.blaze3d.systems.RenderSystem;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({bey.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int d;

    @Shadow
    private int e;

    @Shadow
    @Final
    private bew c;
    private ftc labyMod$iconSprite;

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.d;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.e;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.c.e());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        ftc sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.i(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        ftc sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        RenderSystem.setShaderTexture(0, sprite.i());
        enr.a((ehe) poseStackObject, x, y, 0, width, height, sprite);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.d == -1;
    }

    private ftc labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        add resourceLocation = ja.e.b(this.c);
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = emh.N().aF().a(this.c);
        return this.labyMod$iconSprite;
    }
}
