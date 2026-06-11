package net.labymod.v1_21_8.client;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/LabyModKeyMapping.class */
public class LabyModKeyMapping extends fuc {
    private static final Function<Hotkey, fuc> KEY_MAPPINGS = Laby.references().functionMemoizeStorage().memoize(hotkey -> {
        String category = hotkey.category();
        fuc labyModKeyMapping = new LabyModKeyMapping(I18n.translate(hotkey.translationKey(), new Object[0]), hotkey.accessor(), category);
        ((MinecraftInputMapping) labyModKeyMapping).addCategory(category);
        return labyModKeyMapping;
    });
    private final KeyAccessor keyAccessor;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return super.a((fuc) obj);
    }

    private LabyModKeyMapping(String name, KeyAccessor accessor, String category) {
        super(name, accessor.get() instanceof MouseButton ? b.c : b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }

    public static fuc create(Hotkey hotkey) {
        return KEY_MAPPINGS.apply(hotkey);
    }

    @NotNull
    public a i() {
        Key key = this.keyAccessor.getDefault();
        b type = key instanceof MouseButton ? b.c : b.a;
        return type.a(key.getId());
    }

    public void b(@NotNull a key) {
        Key key2;
        super.b(key);
        if (key.a() == b.a || key.a() == b.b) {
            key2 = KeyMapper.getKey(key.b());
        } else {
            key2 = KeyMapper.getMouseButton(key.b());
        }
        Key mappedKey = key2;
        this.keyAccessor.set(mappedKey);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean l() {
        return ((MinecraftInputMapping) this).getKeyCode() == i().b();
    }
}
