package net.labymod.v1_21.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_21.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({fro.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fgm[] labyMod$checkKeys(fgs instance) {
        List<fgm> mappings = new ArrayList<>(Arrays.asList(instance.W));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (fgm[]) mappings.toArray(new fgm[0]);
    }

    @Redirect(method = {"lambda$addFooter$0"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fgm[] labyMod$resetKeys(fgs instance) {
        List<fgm> mappings = new ArrayList<>(Arrays.asList(instance.W));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (fgm[]) mappings.toArray(new fgm[0]);
    }
}
