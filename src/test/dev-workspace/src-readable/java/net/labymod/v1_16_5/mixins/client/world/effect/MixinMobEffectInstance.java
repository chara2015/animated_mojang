package net.labymod.v1_16_5.mixins.client.world.effect;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({apu.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int c;

    @Shadow
    private int d;

    @Shadow
    @Final
    private aps b;
    private ekc labyMod$iconSprite;

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
        ekc sprite = labyMod$cacheIcon();
        if (sprite == null) {
            return null;
        }
        return Icon.sprite(sprite.m().g(), MathHelper.ceil(sprite.h()), MathHelper.ceil(sprite.j()), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        ekc sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        djz.C().M().a(sprite.m().g());
        dkw.a((dfm) poseStackObject, x, y, 0, width, height, sprite);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return h();
    }

    private ekc labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        vk resourceLocation = gm.P.b(this.b);
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = djz.C().at().a(this.b);
        return this.labyMod$iconSprite;
    }
}
