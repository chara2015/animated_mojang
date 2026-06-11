package net.labymod.v1_12_2.client;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/LabyModKeyMapping.class */
public class LabyModKeyMapping extends bhy {
    private static final Function<Hotkey, bhy> KEY_MAPPINGS = Laby.references().functionMemoizeStorage().memoize(hotkey -> {
        String category = hotkey.category();
        bhy labyModKeyMapping = new LabyModKeyMapping(I18n.translate(hotkey.translationKey(), new Object[0]), hotkey.accessor(), category);
        ((MinecraftInputMapping) labyModKeyMapping).addCategory(category);
        return labyModKeyMapping;
    });
    private final KeyAccessor keyAccessor;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return super.a((bhy) obj);
    }

    private LabyModKeyMapping(String name, KeyAccessor accessor, String category) {
        super(name, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }

    public static bhy create(Hotkey hotkey) {
        return KEY_MAPPINGS.apply(hotkey);
    }

    public int i() {
        Key defaultKey = this.keyAccessor.getDefault();
        if (defaultKey instanceof MouseButton) {
            return defaultKey.getId() - 100;
        }
        return defaultKey.getId();
    }

    public int j() {
        Key key = this.keyAccessor.get();
        if (key instanceof MouseButton) {
            return key.getId() - 100;
        }
        return key.getId();
    }

    public void b(int key) {
        super.b(key);
        if (key < 0) {
            this.keyAccessor.set(KeyMapper.getMouseButton(key + 100));
        } else {
            this.keyAccessor.set(KeyMapper.getKey(key));
        }
    }
}
