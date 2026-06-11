package net.labymod.v1_21_11.mixins.client;

import java.util.function.BooleanSupplier;
import net.labymod.api.client.options.ToggleInputMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinToggleKeyMapping.class */
@Mixin({gfw.class})
public abstract class MixinToggleKeyMapping extends MixinKeyMapping implements ToggleInputMapping {

    @Shadow
    @Final
    private BooleanSupplier b;

    @Override // net.labymod.api.client.options.ToggleInputMapping
    public boolean needsToggle() {
        return this.b.getAsBoolean();
    }

    @Override // net.labymod.api.client.options.ToggleInputMapping
    public void forcePress() {
        updateDown(true);
    }

    @Override // net.labymod.api.client.options.ToggleInputMapping
    public void forceUnpress() {
        resetClickCount();
        updateDown(false);
    }

    @Override // net.labymod.api.client.options.ToggleInputMapping
    public boolean toggle() {
        if (!needsToggle()) {
            return false;
        }
        updateDown(!input$isDown());
        return true;
    }
}
