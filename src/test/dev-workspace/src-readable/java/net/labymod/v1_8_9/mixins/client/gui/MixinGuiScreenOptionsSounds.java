package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.v1_8_9.client.gui.GuiScreenOptionsSoundsAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiScreenOptionsSounds.class */
@Mixin({axz.class})
public abstract class MixinGuiScreenOptionsSounds implements GuiScreenOptionsSoundsAccessor {
    @Shadow
    protected abstract String a(bpg bpgVar);

    @Override // net.labymod.v1_8_9.client.gui.GuiScreenOptionsSoundsAccessor
    public String labymod$getSoundVolume(bpg category) {
        return a(category);
    }
}
