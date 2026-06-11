package net.labymod.v1_21_8.mixins.client.world.effect;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.v1_21_8.client.renderer.GameRendererAccessor;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({byq.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int h;

    @Shadow
    private int i;

    @Shadow
    @Final
    private jl<byo> g;
    private ame labyMod$iconSprite;
    private fxb labyMod$graphics;

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
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(((byo) this.g.a()).g());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        ame spriteLocation = labyMod$cacheIcon();
        if (spriteLocation == null) {
            return null;
        }
        fxc sprites = fue.R().aJ();
        hrs sprite = sprites.a(spriteLocation);
        return Icon.sprite(sprite.j(), sprite.a(), sprite.b(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        ame sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            fue minecraft = fue.R();
            this.labyMod$graphics = new fxb(minecraft, GameRendererAccessor.self(fue.R().j).labyMod$getGuiRenderState());
        }
        this.labyMod$graphics.setPose((Matrix3x2fStack) poseStackObject);
        this.labyMod$graphics.a(gxx.ar, sprite, x, y, width, height, -1);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.h == -1;
    }

    private ame labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        ame resourceLocation = mm.d.b((byo) this.g.a());
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = fxa.a(this.g);
        return this.labyMod$iconSprite;
    }
}
