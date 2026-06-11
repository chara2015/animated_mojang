package net.labymod.v1_21_11.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_21_11.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/screens/controls/MixinKeyBindsList.class */
@Mixin({gwi.class})
public abstract class MixinKeyBindsList extends gjk<b> {
    public /* bridge */ /* synthetic */ gmm aO_() {
        return super.r();
    }

    public MixinKeyBindsList(gfj $$0, int $$1, int $$2, int $$3, int $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private gfh[] labyMod$addCustomKeybindings(gfo instance) {
        List<gfh> keyMappings = new ArrayList<>(Arrays.asList(instance.as));
        keyMappings.removeIf(mapping -> {
            return ((MinecraftInputMapping) mapping).isHidden();
        });
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (gfh[]) keyMappings.toArray(new gfh[0]);
    }
}
