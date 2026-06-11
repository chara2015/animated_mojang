package net.labymod.api.client.component;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ComponentUtil.class */
public class ComponentUtil {
    private ComponentUtil() {
    }

    public static Component join(Component... components) {
        if (components.length == 0) {
            return Component.empty();
        }
        if (components.length == 1) {
            return components[0];
        }
        Component joined = Component.empty();
        for (int i = 0; i < components.length; i++) {
            if (i != 0) {
                if (i == components.length - 1) {
                    joined.append(Component.text(" ")).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                } else {
                    joined.append(Component.text(",")).append(Component.text(" "));
                }
            }
            joined.append(components[i]);
        }
        return joined;
    }

    public static Component join(List<Component> components) {
        int size = components.size();
        if (size == 0) {
            return Component.empty();
        }
        if (size == 1) {
            return components.get(0);
        }
        Component joined = Component.empty();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                if (i == size - 1) {
                    joined.append(Component.text(" ")).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                } else {
                    joined.append(Component.text(",")).append(Component.text(" "));
                }
            }
            joined.append(components.get(i));
        }
        return joined;
    }
}
