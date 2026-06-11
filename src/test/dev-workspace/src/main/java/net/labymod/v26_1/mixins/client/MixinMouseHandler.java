package net.labymod.v26_1.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.player.LocalPlayer;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/MixinMouseHandler.class */
@Mixin({MouseHandler.class})
public abstract class MixinMouseHandler implements MouseHandlerAccessor {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private MouseButtonInfo activeButton;

    @Shadow
    private double mousePressedTime;

    @Shadow
    private double xpos;

    @Shadow
    private double ypos;

    @Shadow
    private double accumulatedDX;

    @Shadow
    private double accumulatedDY;

    @Shadow
    private boolean mouseGrabbed;
    private final MouseBridge labyMod$mouseBridge = LabyMod.references().mouseBridge();

    @Inject(method = {"onButton"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$handleMouseEvent(long $$0, MouseButtonInfo $$1, int $$2, CallbackInfo ci) {
        DefaultKeyMapper.setGlfwMouseModifiers($$1.modifiers());
        if (this.labyMod$mouseBridge.handleMouseEvent($$1.button(), $$2)) {
            ci.cancel();
        }
    }

    @Inject(method = {"onMove"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isWindowActive()Z", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$handleMouseMove(long windowHandle, double mouseX, double mouseY, CallbackInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.activeButton == null ? -1 : this.activeButton.button(), this.mousePressedTime, mouseX, mouseY, this.xpos, this.ypos, cancel -> {
            if (!cancel.booleanValue()) {
                return;
            }
            callback.cancel();
            if (this.minecraft.isWindowActive()) {
                this.accumulatedDX += mouseX - this.xpos;
                this.accumulatedDY += mouseY - this.ypos;
            }
            this.xpos = mouseX;
            this.ypos = mouseY;
        });
    }

    @Insert(method = {"onScroll(JDD)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(long windowHandle, double xOffset, double yOffset, InsertInfo callback) {
        Options options = Minecraft.getInstance().options;
        double scrollOffset = ((Boolean) options.discreteMouseScroll().get()).booleanValue() ? Math.signum(yOffset) : yOffset;
        double delta = scrollOffset * ((Double) options.mouseWheelSensitivity().get()).doubleValue();
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }

    @Redirect(method = {"turnPlayer"}, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(LocalPlayer player, double x, double y) {
        ClientPlayerTurnEvent event = (ClientPlayerTurnEvent) Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer) player, x / 8.0d, y / 8.0d));
        if (event.isCancelled()) {
            return;
        }
        player.turn(event.getX() * 8.0d, event.getY() * 8.0d);
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
        Window window = Minecraft.getInstance().getWindow();
        double x = (this.xpos * ((double) window.getGuiScaledWidth())) / ((double) window.getScreenWidth());
        double y = (this.ypos * ((double) window.getGuiScaledHeight())) / ((double) window.getScreenHeight());
        MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.mouseGrabbed);
        bridge.absoluteMouse().setGrabbed(this.mouseGrabbed);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void grabMouseNative() {
        InputConstants.grabOrReleaseMouse(this.minecraft.getWindow(), 212995, ((double) this.minecraft.getWindow().getScreenWidth()) / 2.0d, ((double) this.minecraft.getWindow().getScreenHeight()) / 2.0d);
    }

    @Override // net.labymod.api.client.gui.mouse.MouseHandlerAccessor
    public void ungrabMouseNative() {
        InputConstants.grabOrReleaseMouse(this.minecraft.getWindow(), 212993, ((double) this.minecraft.getWindow().getScreenWidth()) / 2.0d, ((double) this.minecraft.getWindow().getScreenHeight()) / 2.0d);
    }

    @WrapOperation(method = {"onScroll"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/ScrollWheelHandler;onMouseScroll(DD)Lorg/joml/Vector2i;")})
    private Vector2i labyMod$fireHotbarSlotChangeEvent(ScrollWheelHandler instance, double $$0, double $$1, Operation<Vector2i> original) {
        Vector2i mouseScroll = (Vector2i) original.call(new Object[]{instance, Double.valueOf($$0), Double.valueOf($$1)});
        if (mouseScroll.y == 0) {
            return mouseScroll;
        }
        LocalPlayer player = this.minecraft.player;
        int slotDelta = MathHelper.clamp(mouseScroll.y, -1, 1);
        ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.getInventory().getSelectedSlot(), slotDelta);
        int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            mouseScroll.y = 0;
            return mouseScroll;
        }
        if (event.toSlot() != originalToSlot && !player.isSpectator()) {
            player.getInventory().setSelectedSlot(MathHelper.clamp(event.toSlot(), 0, 8));
            mouseScroll.y = 0;
            return mouseScroll;
        }
        return mouseScroll;
    }
}
