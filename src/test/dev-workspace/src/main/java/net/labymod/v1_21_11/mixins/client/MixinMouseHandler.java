package net.labymod.v1_21_11.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.key.MacOSMouse;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinMouseHandler.class */
@Mixin({gfk.class})
public abstract class MixinMouseHandler implements MouseHandlerAccessor {

    @Shadow
    @Final
    private gfj d;

    @Shadow
    private gzd l;

    @Shadow
    private double o;

    @Shadow
    private double h;

    @Shadow
    private double i;

    @Shadow
    private double r;

    @Shadow
    private double s;

    @Shadow
    private boolean v;
    private final MouseBridge labyMod$mouseBridge = LabyMod.references().mouseBridge();

    @Inject(method = {"onButton"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$handleMouseEvent(long $$0, gzd $$1, int $$2, CallbackInfo ci) {
        DefaultKeyMapper.setGlfwMouseModifiers($$1.b());
        if (this.labyMod$mouseBridge.handleMouseEvent($$1.t(), $$2)) {
            ci.cancel();
        }
    }

    @Inject(method = {"onMove"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isWindowActive()Z", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$handleMouseMove(long windowHandle, double mouseX, double mouseY, CallbackInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.l == null ? -1 : this.l.t(), this.o, mouseX, mouseY, this.h, this.i, cancel -> {
            if (!cancel.booleanValue()) {
                return;
            }
            callback.cancel();
            if (this.d.aI()) {
                this.r += mouseX - this.h;
                this.s += mouseY - this.i;
            }
            this.h = mouseX;
            this.i = mouseY;
        });
    }

    @Insert(method = {"onScroll(JDD)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(long windowHandle, double xOffset, double yOffset, InsertInfo callback) {
        gfo options = gfj.V().k;
        double scrollOffset = ((Boolean) options.ag().b()).booleanValue() ? Math.signum(yOffset) : yOffset;
        double delta = scrollOffset * ((Double) options.Q().b()).doubleValue();
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }

    @Redirect(method = {"turnPlayer"}, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(hnh player, double x, double y) {
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
        fyk window = gfj.V().aR();
        double x = (this.h * ((double) window.o())) / ((double) window.m());
        double y = (this.i * ((double) window.p())) / ((double) window.n());
        MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.v);
        bridge.absoluteMouse().setGrabbed(this.v);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void grabMouseNative() {
        fyc.a(this.d.aR(), 212995, ((double) this.d.aR().m()) / 2.0d, ((double) this.d.aR().n()) / 2.0d);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void ungrabMouseNative() {
        fyc.a(this.d.aR(), 212993, ((double) this.d.aR().m()) / 2.0d, ((double) this.d.aR().n()) / 2.0d);
    }

    @WrapOperation(method = {"onScroll"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/ScrollWheelHandler;onMouseScroll(DD)Lorg/joml/Vector2i;")})
    private Vector2i labyMod$fireHotbarSlotChangeEvent(gft instance, double $$0, double $$1, Operation<Vector2i> original) {
        Vector2i mouseScroll = (Vector2i) original.call(new Object[]{instance, Double.valueOf($$0), Double.valueOf($$1)});
        if (mouseScroll.y == 0) {
            return mouseScroll;
        }
        hnh player = this.d.s;
        int slotDelta = MathHelper.clamp(mouseScroll.y, -1, 1);
        ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.gK().g(), slotDelta);
        int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            mouseScroll.y = 0;
            return mouseScroll;
        }
        if (event.toSlot() != originalToSlot && !player.au()) {
            player.gK().d(MathHelper.clamp(event.toSlot(), 0, 8));
            mouseScroll.y = 0;
            return mouseScroll;
        }
        return mouseScroll;
    }
}
