package net.labymod.api.adventure;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/adventure/LegacyChatFormatting.class */
@Singleton
@Referenceable
@Deprecated
public class LegacyChatFormatting {
    private static final int INITIAL_CAPACITY = 22;
    private final Int2ObjectMap<Style> styles = new Int2ObjectOpenHashMap(22);
    private final Object2IntMap<TextColor> colorToCharacter = new Object2IntOpenHashMap(22);

    @Inject
    public LegacyChatFormatting() {
        registerStyle('a', Style.style(NamedTextColor.GREEN));
        registerStyle('b', Style.style(NamedTextColor.AQUA));
        registerStyle('c', Style.style(NamedTextColor.RED));
        registerStyle('d', Style.style(NamedTextColor.LIGHT_PURPLE));
        registerStyle('e', Style.style(NamedTextColor.YELLOW));
        registerStyle('f', Style.style(NamedTextColor.WHITE));
        registerStyle('0', Style.style(NamedTextColor.BLACK));
        registerStyle('1', Style.style(NamedTextColor.DARK_BLUE));
        registerStyle('2', Style.style(NamedTextColor.DARK_GREEN));
        registerStyle('3', Style.style(NamedTextColor.DARK_AQUA));
        registerStyle('4', Style.style(NamedTextColor.DARK_RED));
        registerStyle('5', Style.style(NamedTextColor.DARK_PURPLE));
        registerStyle('6', Style.style(NamedTextColor.GOLD));
        registerStyle('7', Style.style(NamedTextColor.GRAY));
        registerStyle('8', Style.style(NamedTextColor.DARK_GRAY));
        registerStyle('9', Style.style(NamedTextColor.BLUE));
        registerStyle('k', Style.style(TextDecoration.OBFUSCATED));
        registerStyle('l', Style.style(TextDecoration.BOLD));
        registerStyle('m', Style.style(TextDecoration.STRIKETHROUGH));
        registerStyle('n', Style.style(TextDecoration.UNDERLINED));
        registerStyle('o', Style.style(TextDecoration.ITALIC));
        registerStyle('r', Style.empty());
    }

    private void registerStyle(char key, Style style) {
        this.styles.put(key, style);
        TextColor color = style.getColor();
        if (color != null) {
            this.colorToCharacter.put(color, key);
        }
    }

    @Nullable
    public Style getStyle(char key) {
        return (Style) this.styles.get(key);
    }

    public char getColorCode(TextColor color) {
        Integer colorCode = this.colorToCharacter.getOrDefault(color, (Integer) null);
        if (colorCode == null) {
            return getColorCode(ColorUtil.getClosestDefaultTextColor(color));
        }
        return (char) colorCode.intValue();
    }
}
