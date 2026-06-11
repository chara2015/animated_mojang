package net.labymod.v1_20_1.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/gui/components/MixinAbstractButton.class */
@Mixin({eoz.class})
public class MixinAbstractButton extends MixinAbstractWidget {
    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(eox graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((epf) this).l());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
