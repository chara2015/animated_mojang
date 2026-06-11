package net.labymod.v1_20_6.mixins.client.gui;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.laby3d.util.DirtyFramebufferClearer;
import net.labymod.v1_20_6.client.gui.GuiGraphicsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/MixinGuiGraphics.class */
@Mixin({fgt.class})
public class MixinGuiGraphics implements GuiGraphicsAccessor {

    @Mutable
    @Shadow
    @Final
    private faa e;

    @Unique
    private boolean labyMod$firstBlit = true;

    @Override // net.labymod.v1_20_6.client.gui.GuiGraphicsAccessor
    public void setPose(faa pose) {
        this.e = pose;
    }

    @Inject(method = {"fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeFill(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeFillGradient(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFF)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeBlit(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawString(CallbackInfoReturnable<Integer> ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeDrawFormattedString(CallbackInfoReturnable<Integer> ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V"}, at = {@At("HEAD")})
    private void labyMod$flushBeforeRenderItem(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Unique
    private void labyMod$flushPending() {
        RenderEnvironmentContext context = Laby.references().renderEnvironmentContext();
        if (!context.isScreenContext()) {
            return;
        }
        ScreenCanvas canvas = context.screenContext().canvas();
        List<CanvasSnapshot> snapshots = canvas.captureSnapshot();
        if (snapshots.isEmpty()) {
            return;
        }
        ((fgt) this).e();
        DirtyFramebufferClearer clearer = Laby.references().getDirtyFramebufferClearer();
        if (clearer != null) {
            clearer.clear();
        }
        for (CanvasSnapshot snapshot : snapshots) {
            boolean firstBlit = this.labyMod$firstBlit;
            this.labyMod$firstBlit = false;
            context.screenContext().renderSnapshot(snapshot, firstBlit);
        }
    }
}
