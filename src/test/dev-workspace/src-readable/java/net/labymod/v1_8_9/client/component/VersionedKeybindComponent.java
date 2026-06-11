package net.labymod.v1_8_9.client.component;

import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/component/VersionedKeybindComponent.class */
public class VersionedKeybindComponent extends fa {
    private static Function<String, Supplier<Component>> keyResolver = keybind -> {
        return () -> {
            return Component.text(keybind);
        };
    };

    public VersionedKeybindComponent(String keybind) {
        super(keybind);
    }

    public static Function<String, Supplier<Component>> getKeyResolver() {
        return keyResolver;
    }

    public static void setKeyResolver(Function<String, Supplier<Component>> keyResolver2) {
        keyResolver = keyResolver2;
    }

    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public fa f() {
        VersionedKeybindComponent component = new VersionedKeybindComponent(g());
        component.a(b().m());
        for (eu sibling : a()) {
            component.a(sibling.f());
        }
        return component;
    }

    public String toString() {
        return "KeybindComponent{keybind='" + g() + "', siblings=" + String.valueOf(this.a) + ", style=" + String.valueOf(b()) + "}";
    }
}
