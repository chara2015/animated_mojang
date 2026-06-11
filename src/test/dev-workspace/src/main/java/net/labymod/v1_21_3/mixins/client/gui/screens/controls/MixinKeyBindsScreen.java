package net.labymod.v1_21_3.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_21_3.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({fxj.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fme[] labyMod$checkKeys(fmk instance) {
        List<fme> mappings = new ArrayList<>(Arrays.asList(instance.V));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (fme[]) mappings.toArray(new fme[0]);
    }

    @Redirect(method = {"lambda$addFooter$0"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fme[] labyMod$resetKeys(fmk instance) {
        List<fme> mappings = new ArrayList<>(Arrays.asList(instance.V));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (fme[]) mappings.toArray(new fme[0]);
    }
}
