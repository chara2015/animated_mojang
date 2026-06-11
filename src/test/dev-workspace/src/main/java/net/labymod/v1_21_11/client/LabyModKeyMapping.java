package net.labymod.v1_21_11.client;

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
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/LabyModKeyMapping.class */
public class LabyModKeyMapping extends gfh {
    private static final Map<amo, a> CUSTOM_CATEGORIES = new HashMap();
    private static final Function<Hotkey, gfh> KEY_MAPPINGS = Laby.references().functionMemoizeStorage().memoize(hotkey -> {
        amo categoryId = amo.b("labymod", hotkey.namespace());
        return new LabyModKeyMapping(I18n.translate(hotkey.translationKey(), new Object[0]), hotkey.accessor(), CUSTOM_CATEGORIES.computeIfAbsent(categoryId, a::a));
    });
    private final KeyAccessor keyAccessor;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return super.a((gfh) obj);
    }

    private LabyModKeyMapping(String name, KeyAccessor accessor, a category) {
        super(name, accessor.get() instanceof MouseButton ? b.c : b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }

    public static gfh create(Hotkey hotkey) {
        return KEY_MAPPINGS.apply(hotkey);
    }

    @NotNull
    public a l() {
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
    public boolean o() {
        return ((MinecraftInputMapping) this).getKeyCode() == l().b();
    }
}
