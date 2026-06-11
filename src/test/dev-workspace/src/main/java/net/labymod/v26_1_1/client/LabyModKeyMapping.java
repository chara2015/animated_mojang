package net.labymod.v26_1_1.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.util.I18n;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/LabyModKeyMapping.class */
public class LabyModKeyMapping extends KeyMapping {
    private static final Map<Identifier, KeyMapping.Category> CUSTOM_CATEGORIES = new HashMap();
    private static final Function<Hotkey, KeyMapping> KEY_MAPPINGS = Laby.references().functionMemoizeStorage().memoize(hotkey -> {
        Identifier categoryId = Identifier.tryBuild("labymod", hotkey.namespace());
        return new LabyModKeyMapping(I18n.translate(hotkey.translationKey(), new Object[0]), hotkey.accessor(), CUSTOM_CATEGORIES.computeIfAbsent(categoryId, KeyMapping.Category::register));
    });
    private final KeyAccessor keyAccessor;

    private LabyModKeyMapping(String name, KeyAccessor accessor, KeyMapping.Category category) {
        super(name, accessor.get() instanceof MouseButton ? InputConstants.Type.MOUSE : InputConstants.Type.KEYSYM, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }

    public static KeyMapping create(Hotkey hotkey) {
        return KEY_MAPPINGS.apply(hotkey);
    }

    @NotNull
    public InputConstants.Key getDefaultKey() {
        Key key = this.keyAccessor.getDefault();
        InputConstants.Type type = key instanceof MouseButton ? InputConstants.Type.MOUSE : InputConstants.Type.KEYSYM;
        return type.getOrCreate(key.getId());
    }

    public void setKey(@NotNull InputConstants.Key key) {
        Key key2;
        super.setKey(key);
        if (key.getType() == InputConstants.Type.KEYSYM || key.getType() == InputConstants.Type.SCANCODE) {
            key2 = KeyMapper.getKey(key.getValue());
        } else {
            key2 = KeyMapper.getMouseButton(key.getValue());
        }
        Key mappedKey = key2;
        this.keyAccessor.set(mappedKey);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDefault() {
        return ((MinecraftInputMapping) this).getKeyCode() == getDefaultKey().getValue();
    }
}
