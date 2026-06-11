package net.labymod.v1_21_8.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_21_8.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/screens/controls/MixinKeyBindsList.class */
@Mixin({giw.class})
public abstract class MixinKeyBindsList extends fxu<b> {
    @Nullable
    public /* bridge */ /* synthetic */ fzn aL_() {
        return super.r();
    }

    public MixinKeyBindsList(fue $$0, int $$1, int $$2, int $$3, int $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fuc[] labyMod$addCustomKeybindings(fui instance) {
        List<fuc> keyMappings = new ArrayList<>(Arrays.asList(instance.W));
        keyMappings.removeIf(mapping -> {
            return ((MinecraftInputMapping) mapping).isHidden();
        });
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (fuc[]) keyMappings.toArray(new fuc[0]);
    }
}
