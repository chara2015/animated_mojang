package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.v1_12_2.client.gui.GuiScreenOptionsSoundsAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiScreenOptionsSounds.class */
@Mixin({blo.class})
public abstract class MixinGuiScreenOptionsSounds implements GuiScreenOptionsSoundsAccessor {
    @Shadow
    protected abstract String a(qg qgVar);

    @Override // net.labymod.v1_12_2.client.gui.GuiScreenOptionsSoundsAccessor
    public String labymod$getSoundVolume(qg category) {
        return a(category);
    }
}
