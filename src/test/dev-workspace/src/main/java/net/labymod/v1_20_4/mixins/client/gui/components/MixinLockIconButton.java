package net.labymod.v1_20_4.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/gui/components/MixinLockIconButton.class */
@Mixin({exw.class})
public abstract class MixinLockIconButton extends MixinAbstractWidget {
    @Insert(method = {"renderWidget"}, at = @At("HEAD"), cancellable = true)
    public void render(ewu graphics, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        getWatcher().update(this, ((exe) this).x());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(graphics.c().stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
