package net.labymod.v1_21_11.mixins.client;

import java.util.function.BooleanSupplier;
import net.labymod.api.client.options.ToggleInputMapping;
import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinToggleKeyMapping.class */
@Mixin({ToggleKeyMapping.class})
public abstract class MixinToggleKeyMapping extends MixinKeyMapping implements ToggleInputMapping {

    @Shadow
    @Final
    private BooleanSupplier b;

    public boolean needsToggle() {
        return this.b.getAsBoolean();
    }

    public void forcePress() {
        updateDown(true);
    }

    public void forceUnpress() {
        resetClickCount();
        updateDown(false);
    }

    public boolean toggle() {
        if (!needsToggle()) {
            return false;
        }
        updateDown(!input$isDown());
        return true;
    }
}
