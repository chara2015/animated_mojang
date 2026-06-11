package net.labymod.v1_21_11.mixins.client.renderer.state.gui;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiTransform;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_21_11.client.renderer.state.gui.LabyGuiRenderState;
import net.labymod.v1_21_11.client.renderer.state.gui.SentinelRenderState;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/gui/MixinGuiRenderState.class */
@Mixin({GuiRenderState.class})
public abstract class MixinGuiRenderState implements LabyGuiRenderState {

    @Shadow
    private int firstStratumAfterBlur;

    @Shadow
    public abstract void submitGuiElement(GuiElementRenderState guiElementRenderState);

    @Inject(method = {"submitGuiElement", "submitItem", "submitText", "submitPicturesInPictureState"}, at = {@At("HEAD")})
    private void labyMod$flushPending(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"blurBeforeThisStratum"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$dontThrowException(CallbackInfo ci) {
        if (this.firstStratumAfterBlur != Integer.MAX_VALUE) {
            ci.cancel();
        }
    }

    @Override // net.labymod.v1_21_11.client.renderer.state.gui.LabyGuiRenderState
    public void labyMod$flushPending() {
        ScreenCanvas canvas = Laby.references().renderEnvironmentContext().screenContext().canvas();
        List<CanvasSnapshot> snapshots = canvas.captureSnapshot();
        if (snapshots.isEmpty()) {
            return;
        }
        for (CanvasSnapshot snapshot : snapshots) {
            labyMod$submitSentinel(snapshot);
        }
    }

    @Unique
    private void labyMod$submitSentinel(CanvasSnapshot snapshot) {
        ScreenRectangle screenRectangle;
        Rectangle componentBounds;
        List<GuiComponent> components = snapshot.capturedComponents();
        MutableRectangle bounds = Rectangle.extendable();
        boolean hasBounds = false;
        for (GuiComponent guiComponent : components) {
            if (!(guiComponent instanceof GuiCommandComponent) && (componentBounds = guiComponent.bounds()) != null) {
                if (guiComponent instanceof GuiTransform) {
                    GuiTransform guiTransform = (GuiTransform) guiComponent;
                    bounds.extendTransformed(componentBounds, guiTransform.pose());
                } else {
                    bounds.extend(componentBounds);
                }
                hasBounds = true;
            }
        }
        if (hasBounds) {
            screenRectangle = new ScreenRectangle(MathHelper.floor(bounds.getLeft()), MathHelper.floor(bounds.getTop()), MathHelper.floor(bounds.getWidth()), MathHelper.floor(bounds.getHeight()));
        } else {
            screenRectangle = new ScreenRectangle(0, 0, 0, 0);
        }
        ScreenRectangle rectangle = screenRectangle;
        submitGuiElement(new SentinelRenderState(rectangle, snapshot));
    }
}

