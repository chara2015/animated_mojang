package net.labymod.api.client.resources;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.Constants;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.TextFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/ThemeResourceRegistry.class */
public class ThemeResourceRegistry {
    private static final Map<String, ThemeResourceLocation> RESOURCES = new HashMap();
    private static final Map<String, Icon> ICONS = new HashMap();

    static {
        for (Field field : Constants.NamedThemeResource.class.getDeclaredFields()) {
            String id = field.getName();
            try {
                RESOURCES.put(id, (ThemeResourceLocation) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        RESOURCES.put("COMMON", Textures.SpriteCommon.TEXTURE);
        RESOURCES.put("MINECRAFT_ICONS", Textures.SpriteMinecraftIcons.TEXTURE);
        registerIcons(Textures.class);
    }

    private static void registerIcons(Class<?> declaringClass) {
        for (Class<?> declaredClass : declaringClass.getDeclaredClasses()) {
            registerIcons(declaredClass);
        }
        for (Field field : declaringClass.getDeclaredFields()) {
            if (field.getType() == Icon.class) {
                try {
                    Icon icon = (Icon) field.get(null);
                    TextFormat nameFormat = TextFormat.SNAKE_CASE;
                    String name = field.getName();
                    for (String formattedName : new String[]{nameFormat.toDashCase(name), name}) {
                        ICONS.put(formattedName.toUpperCase(Locale.US), icon);
                    }
                } catch (IllegalAccessException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    public static ThemeResourceLocation valueOf(String id) {
        return RESOURCES.get(id.toUpperCase(Locale.US));
    }

    public static Icon getIcon(String id) {
        return ICONS.get(id.toUpperCase(Locale.US));
    }
}
