package net.labymod.v1_21_11.mixins.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.v1_21_11.client.gui.screen.key.mapper.VersionedKeyMapper;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinKeyMapping.class */
@Mixin({KeyMapping.class})
@Implements({@Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE)})
public abstract class MixinKeyMapping implements MinecraftInputMapping {

    @Shadow
    private InputConstants.Key setDown;

    @Shadow
    @Final
    private static Map<String, KeyMapping> ALL;

    @Shadow
    private boolean isDown;

    @Shadow
    private int clickCount;
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;

    @Shadow
    public abstract boolean isDown();

    @Shadow
    public abstract String getName();

    @Shadow
    protected abstract void release();

    @Shadow
    public abstract void setDown(boolean z);

    @Inject(method = {"<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILnet/minecraft/client/KeyMapping$Category;)V"}, at = {@At("RETURN")})
    private void labyMod$fireRegisterKeybindingEvent(String $$0, InputConstants.Type $$1, int $$2, KeyMapping.Category $$3, CallbackInfo ci) {
        RegisterKeybindingEvent event = Laby.fireEvent(new RegisterKeybindingEvent($$0, $$3.id().toString()));
        if (event.isCancelled()) {
            this.labyMod$hidden = true;
        } else if (event.getReplacement() != null) {
            this.labyMod$replacement = event.getReplacement();
        }
    }

    @Inject(method = {"isDown"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$disableKeybind(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced(this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = {"same"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$same(KeyMapping mapping, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) mapping)) {
            cir.setReturnValue(false);
        }
    }

    @Intrinsic
    public boolean input$isDown() {
        return isDown();
    }

    @Intrinsic
    public String input$getName() {
        return getName();
    }

    @Intrinsic
    public String input$getCategory() {
        return getCategory();
    }

    public int getKeyCode() {
        return this.setDown.getValue();
    }

    @NotNull
    public Key key() {
        MouseButton key;
        InputConstants.Type type = this.setDown.getType();
        if (type == InputConstants.Type.MOUSE) {
            key = KeyMapper.getMouseButton(getKeyCode());
        } else {
            key = KeyMapper.getKey(getKeyCode());
        }
        return key == null ? Key.NONE : key;
    }

    public boolean isMouse() {
        return this.setDown.getType() == InputConstants.Type.MOUSE;
    }

    public void unpress() {
        release();
    }

    public void press() {
        setDown(true);
    }

    public boolean isActuallyDown() {
        InputConstants.Type type = this.setDown.getType();
        if (type == InputConstants.Type.MOUSE) {
            return VersionedKeyMapper.isMousePressed(getKeyCode());
        }
        return VersionedKeyMapper.isKeyPressed(getKeyCode());
    }

    public boolean isHidden() {
        return this.labyMod$hidden;
    }

    public Widget getReplacement() {
        if (this.labyMod$replacement != null) {
            return this.labyMod$replacement.get();
        }
        return null;
    }

    public boolean hasReplacement() {
        return this.labyMod$replacement != null;
    }

    public void addCategory(String category) {
    }

    public void updateDown(boolean down) {
        this.isDown = down;
    }

    public void resetClickCount() {
        this.clickCount = 0;
    }
}

