package net.labymod.v1_16_5.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinMouseHandler.class */
@Mixin({dka.class})
public abstract class MixinMouseHandler implements MouseHandlerAccessor {
    private static final int CURSOR_DISABLED = 212995;
    private static final int CURSOR_NORMAL = 212993;

    @Shadow
    private int h;

    @Shadow
    private double k;

    @Shadow
    private double e;

    @Shadow
    private double f;

    @Shadow
    @Final
    private djz a;

    @Shadow
    private double o;

    @Shadow
    private double n;

    @Shadow
    private boolean r;

    @Shadow
    private double p;
    private final MouseBridge labyMod$mouseBridge = LabyMod.references().mouseBridge();

    @Shadow
    public abstract void a();

    @Shadow
    public abstract boolean h();

    @Insert(method = {"onPress(JIII)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;overlay:Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseEvent(long windowHandle, int button, int action, int mods, InsertInfo callback) {
        if (this.labyMod$mouseBridge.handleMouseEvent(button, action)) {
            callback.cancel();
        }
    }

    @Insert(method = {"onMove"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;overlay:Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseMove(long windowHandle, double mouseX, double mouseY, InsertInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.h, this.k, mouseX, mouseY, this.e, this.f, cancel -> {
            if (!cancel.booleanValue()) {
                return;
            }
            callback.cancel();
            this.a.au().a("mouse");
            if (h() && this.a.ap()) {
                this.n += mouseX - this.e;
                this.o += mouseY - this.f;
            }
            a();
            this.e = mouseX;
            this.f = mouseY;
            this.a.au().c();
        });
    }

    @ModifyVariable(method = {"onScroll"}, at = @At("HEAD"), index = 5, argsOnly = true)
    private double labyMod$fixMacOSScroll(double value, long windowHandle, double horizontal, double vertical) {
        return MacOSMouse.fixMouseScroll(value, windowHandle, horizontal, vertical);
    }

    @Insert(method = {"onScroll(JDD)V"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;overlay:Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(long windowHandle, double xOffset, double yOffset, InsertInfo callback) {
        dkd options = djz.C().k;
        double scrollOffset = options.S ? Math.signum(yOffset) : yOffset;
        double delta = scrollOffset * options.G;
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }

    @Redirect(method = {"turnPlayer"}, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(dzm player, double x, double y) {
        ClientPlayerTurnEvent event = (ClientPlayerTurnEvent) Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer) player, x / 8.0d, y / 8.0d));
        if (event.isCancelled()) {
            return;
        }
        player.a(event.getX() * 8.0d, event.getY() * 8.0d);
    }

    @Inject(method = {"releaseMouse"}, at = {@At("TAIL")})
    private void labyMod$releaseMouse(CallbackInfo ci) {
        labyMod$updateMouseGrabbed();
    }

    @Inject(method = {"grabMouse"}, at = {@At("TAIL")})
    private void labyMod$grabMouse(CallbackInfo ci) {
        labyMod$updateMouseGrabbed();
    }

    private void labyMod$updateMouseGrabbed() {
        dez window = djz.C().aD();
        double x = (this.e * ((double) window.o())) / ((double) window.m());
        double y = (this.f * ((double) window.p())) / ((double) window.n());
        MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.r);
        bridge.absoluteMouse().setGrabbed(this.r);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void grabMouseNative() {
        deo.a(this.a.aD().i(), CURSOR_DISABLED, ((double) this.a.aD().m()) / 2.0d, ((double) this.a.aD().n()) / 2.0d);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void ungrabMouseNative() {
        deo.a(this.a.aD().i(), CURSOR_NORMAL, ((double) this.a.aD().m()) / 2.0d, ((double) this.a.aD().n()) / 2.0d);
    }

    @Redirect(method = {"onScroll"}, at = @At(value = "FIELD", ordinal = 5, target = "Lnet/minecraft/client/MouseHandler;accumulatedScroll:D"))
    private double labyMod$fireHotbarSlotChangeEvent(dka instance) {
        double delta = this.p;
        if (delta == 0.0d) {
            return 0.0d;
        }
        dzm player = this.a.s;
        int slotDelta = MathHelper.clamp((int) delta, -1, 1);
        ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.bm.d, slotDelta);
        int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return 0.0d;
        }
        if (event.toSlot() != originalToSlot && !player.a_()) {
            player.bm.d = MathHelper.clamp(event.toSlot(), 0, 8);
            return 0.0d;
        }
        return delta;
    }
}
