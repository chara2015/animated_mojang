package net.labymod.v1_17_1.mixins.client.world.effect;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({ata.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int c;

    @Shadow
    private int d;

    @Shadow
    @Final
    private asy b;
    private exm labyMod$iconSprite;

    @Shadow
    public abstract boolean h();

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.c;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.d;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.b.d());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        exm sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.m().g(), sprite.d(), sprite.e(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        exm sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        RenderSystem.setShaderTexture(0, sprite.m().g());
        dwn.a((dql) poseStackObject, x, y, 0, width, height, sprite);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return h();
    }

    private exm labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        ww resourceLocation = gw.V.b(this.b);
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = dvp.C().av().a(this.b);
        return this.labyMod$iconSprite;
    }
}
