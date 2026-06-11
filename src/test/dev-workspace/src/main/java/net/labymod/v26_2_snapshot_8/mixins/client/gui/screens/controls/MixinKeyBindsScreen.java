package net.labymod.v26_2_snapshot_8.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v26_2_snapshot_8.client.LabyModKeyMapping;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({KeyBindsScreen.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"extractRenderState"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private KeyMapping[] labyMod$checkKeys(Options instance) {
        List<KeyMapping> mappings = new ArrayList<>(Arrays.asList(instance.keyMappings));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (KeyMapping[]) mappings.toArray(new KeyMapping[0]);
    }

    @Redirect(method = {"lambda$addFooter$0"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private KeyMapping[] labyMod$resetKeys(Options instance) {
        List<KeyMapping> mappings = new ArrayList<>(Arrays.asList(instance.keyMappings));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (KeyMapping[]) mappings.toArray(new KeyMapping[0]);
    }
}
