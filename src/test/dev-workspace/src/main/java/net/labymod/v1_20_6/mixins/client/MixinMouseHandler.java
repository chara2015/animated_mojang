package net.labymod.v1_20_6.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.MacOSMouse;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/MixinMouseHandler.class */
@Mixin({ffi.class})
public abstract class MixinMouseHandler implements MouseHandlerAccessor {

    @Shadow
    @Final
    private ffh a;

    @Shadow
    private int h;

    @Shadow
    private double k;

    @Shadow
    private double e;

    @Shadow
    private double f;

    @Shadow
    private double n;

    @Shadow
    private double o;

    @Shadow
    private boolean s;

    @Shadow
    private double q;
    private final MouseBridge labyMod$mouseBridge = LabyMod.references().mouseBridge();

    @Insert(method = {"onPress(JIII)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseEvent(long windowHandle, int button, int action, int mods, InsertInfo callback) {
        if (this.labyMod$mouseBridge.handleMouseEvent(button, action)) {
            callback.cancel();
        }
    }

    @Insert(method = {"onMove"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isWindowActive()Z", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseMove(long windowHandle, double mouseX, double mouseY, InsertInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.h, this.k, mouseX, mouseY, this.e, this.f, cancel -> {
            if (!cancel.booleanValue()) {
                return;
            }
            callback.cancel();
            if (this.a.aB()) {
                this.n += mouseX - this.e;
                this.o += mouseY - this.f;
            }
            this.e = mouseX;
            this.f = mouseY;
        });
    }

    @Insert(method = {"onScroll(JDD)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(long windowHandle, double xOffset, double yOffset, InsertInfo callback) {
        ffl options = ffh.Q().m;
        double scrollOffset = ((Boolean) options.S().c()).booleanValue() ? Math.signum(yOffset) : yOffset;
        double delta = scrollOffset * ((Double) options.F().c()).doubleValue();
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }

    @Redirect(method = {"turnPlayer"}, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(gcs player, double x, double y) {
        ClientPlayerTurnEvent event = (ClientPlayerTurnEvent) Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer) player, x / 8.0d, y / 8.0d));
        if (event.isCancelled()) {
            return;
        }
        player.b(event.getX() * 8.0d, event.getY() * 8.0d);
    }

    @Inject(method = {"releaseMouse"}, at = {@At("TAIL")})
    private void labyMod$releaseMouse(CallbackInfo ci) {
        labyMod$updateMouseGrabbed();
    }

    @Inject(method = {"grabMouse"}, at = {@At("TAIL")})
    private void labyMod$grabMouse(CallbackInfo ci) {
        labyMod$updateMouseGrabbed();
    }

    @ModifyVariable(method = {"onScroll"}, at = @At("HEAD"), index = 5, argsOnly = true)
    private double labyMod$fixMacOSScroll(double value, long windowHandle, double horizontal, double vertical) {
        return MacOSMouse.fixMouseScroll(value, windowHandle, horizontal, vertical);
    }

    private void labyMod$updateMouseGrabbed() {
        eze window = ffh.Q().aO();
        double x = (this.e * ((double) window.o())) / ((double) window.m());
        double y = (this.f * ((double) window.p())) / ((double) window.n());
        MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.s);
        bridge.absoluteMouse().setGrabbed(this.s);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void grabMouseNative() {
        eyv.a(this.a.aO().i(), 212995, ((double) this.a.aO().m()) / 2.0d, ((double) this.a.aO().n()) / 2.0d);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void ungrabMouseNative() {
        eyv.a(this.a.aO().i(), 212993, ((double) this.a.aO().m()) / 2.0d, ((double) this.a.aO().n()) / 2.0d);
    }

    @Redirect(method = {"onScroll"}, at = @At(value = "FIELD", ordinal = 5, target = "Lnet/minecraft/client/MouseHandler;accumulatedScrollY:D"))
    private double labyMod$fireHotbarSlotChangeEvent(ffi instance) {
        double delta = this.q;
        if (delta == 0.0d) {
            return 0.0d;
        }
        gcs player = this.a.s;
        int slotDelta = MathHelper.clamp((int) delta, -1, 1);
        ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.gc().k, slotDelta);
        int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return 0.0d;
        }
        if (event.toSlot() != originalToSlot && !player.N_()) {
            player.gc().k = MathHelper.clamp(event.toSlot(), 0, 8);
            return 0.0d;
        }
        return delta;
    }
}
