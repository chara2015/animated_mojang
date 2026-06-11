package net.labymod.v26_1_2.mixins.client.world.effect;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.v26_1_2.client.renderer.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/world/effect/MixinMobEffectInstance.class */
@Mixin({MobEffectInstance.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinMobEffectInstance implements PotionEffect {

    @Shadow
    private int duration;

    @Shadow
    private int amplifier;

    @Shadow
    @Final
    private Holder<MobEffect> effect;
    private Identifier labyMod$iconSprite;
    private GuiGraphicsExtractor labyMod$graphics;

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.duration;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.amplifier;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        Component component = Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(((MobEffect) this.effect.value()).getDisplayName());
        if (component instanceof TranslatableComponent) {
            return ((TranslatableComponent) component).key();
        }
        return "labymod.unknown";
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        Identifier spriteLocation = labyMod$cacheIcon();
        if (spriteLocation == null) {
            return null;
        }
        TextureAtlas sprites = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.GUI);
        TextureAtlasSprite sprite = sprites.getSprite(spriteLocation);
        return Icon.sprite(sprite.atlasLocation(), sprite.getX(), sprite.getY(), 18, 18, 256, 128);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        Object poseStackObject;
        Identifier sprite = labyMod$cacheIcon();
        if (sprite == null || (poseStackObject = context.stack().getProvider().getPoseStack()) == null) {
            return;
        }
        if (this.labyMod$graphics == null) {
            Minecraft minecraft = Minecraft.getInstance();
            this.labyMod$graphics = new GuiGraphicsExtractor(minecraft, GameRendererAccessor.self(Minecraft.getInstance().gameRenderer).labyMod$getGuiRenderState(), 0, 0);
        }
        this.labyMod$graphics.setPose((Matrix3x2fStack) poseStackObject);
        this.labyMod$graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, width, height, -1);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return false;
    }

    @Intrinsic
    public boolean mobEffect$isInfiniteDuration() {
        return this.duration == -1;
    }

    private Identifier labyMod$cacheIcon() {
        if (this.labyMod$iconSprite != null) {
            return this.labyMod$iconSprite;
        }
        Identifier resourceLocation = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect) this.effect.value());
        if (resourceLocation == null) {
            return null;
        }
        this.labyMod$iconSprite = Gui.getMobEffectSprite(this.effect);
        return this.labyMod$iconSprite;
    }
}
