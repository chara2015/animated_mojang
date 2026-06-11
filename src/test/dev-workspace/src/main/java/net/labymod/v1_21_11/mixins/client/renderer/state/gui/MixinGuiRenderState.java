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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/state/gui/MixinGuiRenderState.class */
@Mixin({gqg.class})
public abstract class MixinGuiRenderState implements LabyGuiRenderState {

    @Shadow
    private int c;

    @Shadow
    public abstract void a(gqe gqeVar);

    @Inject(method = {"submitGuiElement", "submitItem", "submitText", "submitPicturesInPictureState"}, at = {@At("HEAD")})
    private void labyMod$flushPending(CallbackInfo ci) {
        labyMod$flushPending();
    }

    @Inject(method = {"blurBeforeThisStratum"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$dontThrowException(CallbackInfo ci) {
        if (this.c != Integer.MAX_VALUE) {
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
        gpm gpmVar;
        Rectangle componentBounds;
        List<GuiComponent> components = snapshot.capturedComponents();
        MutableRectangle bounds = Rectangle.extendable();
        boolean hasBounds = false;
        for (GuiComponent component : components) {
            if (!(component instanceof GuiCommandComponent) && (componentBounds = component.bounds()) != null) {
                if (component instanceof GuiTransform) {
                    GuiTransform guiTransform = (GuiTransform) component;
                    bounds.extendTransformed(componentBounds, guiTransform.pose());
                } else {
                    bounds.extend(componentBounds);
                }
                hasBounds = true;
            }
        }
        if (hasBounds) {
            gpmVar = new gpm(MathHelper.floor(bounds.getLeft()), MathHelper.floor(bounds.getTop()), MathHelper.floor(bounds.getWidth()), MathHelper.floor(bounds.getHeight()));
        } else {
            gpmVar = new gpm(0, 0, 0, 0);
        }
        gpm rectangle = gpmVar;
        a(new SentinelRenderState(rectangle, snapshot));
    }
}
