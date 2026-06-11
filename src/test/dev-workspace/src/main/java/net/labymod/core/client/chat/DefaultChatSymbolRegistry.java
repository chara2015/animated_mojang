package net.labymod.core.client.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.Implements;
import net.labymod.api.util.collection.map.BiMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatSymbolRegistry.class */
@Singleton
@Implements(ChatSymbolRegistry.class)
public class DefaultChatSymbolRegistry implements ChatSymbolRegistry {
    private final Map<Character, Style> styles = new HashMap();
    private final BiMap<Character, TextColor> colors = new BiMap<>();
    private final BiMap<Character, TextDecoration> decorations = new BiMap<>();
    private final List<Character> symbols = new ArrayList();
    private boolean symbolsLoaded;

    @Inject
    public DefaultChatSymbolRegistry() {
        registerStyle('a', createStyleWithColor(NamedTextColor.GREEN));
        registerStyle('b', createStyleWithColor(NamedTextColor.AQUA));
        registerStyle('c', createStyleWithColor(NamedTextColor.RED));
        registerStyle('d', createStyleWithColor(NamedTextColor.LIGHT_PURPLE));
        registerStyle('e', createStyleWithColor(NamedTextColor.YELLOW));
        registerStyle('f', createStyleWithColor(NamedTextColor.WHITE));
        registerStyle('0', createStyleWithColor(NamedTextColor.BLACK));
        registerStyle('1', createStyleWithColor(NamedTextColor.DARK_BLUE));
        registerStyle('2', createStyleWithColor(NamedTextColor.DARK_GREEN));
        registerStyle('3', createStyleWithColor(NamedTextColor.DARK_AQUA));
        registerStyle('4', createStyleWithColor(NamedTextColor.DARK_RED));
        registerStyle('5', createStyleWithColor(NamedTextColor.DARK_PURPLE));
        registerStyle('6', createStyleWithColor(NamedTextColor.GOLD));
        registerStyle('7', createStyleWithColor(NamedTextColor.GRAY));
        registerStyle('8', createStyleWithColor(NamedTextColor.DARK_GRAY));
        registerStyle('9', createStyleWithColor(NamedTextColor.BLUE));
        registerStyle('k', createStyleWithDecoration(TextDecoration.OBFUSCATED));
        registerStyle('l', createStyleWithDecoration(TextDecoration.BOLD));
        registerStyle('m', createStyleWithDecoration(TextDecoration.STRIKETHROUGH));
        registerStyle('n', createStyleWithDecoration(TextDecoration.UNDERLINED));
        registerStyle('o', createStyleWithDecoration(TextDecoration.ITALIC));
        registerStyle('r', Style.empty());
    }

    private void registerStyle(char c, Style style) {
        this.styles.put(Character.valueOf(c), style);
        if (style.getColor() != null) {
            this.colors.put(Character.valueOf(c), style.getColor());
        }
        for (TextDecoration decoration : TextDecoration.getValues()) {
            if (style.hasDecoration(decoration)) {
                this.decorations.put(Character.valueOf(c), decoration);
            }
        }
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public List<Character> getSymbols() {
        if (!this.symbolsLoaded) {
            reloadSymbols();
        }
        return this.symbols;
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public void addSymbol(char c) {
        this.symbols.add(Character.valueOf(c));
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public void reloadSymbols() {
        this.symbols.clear();
        try {
            InputStream inputStream = Constants.Resources.SYMBOLS.openStream();
            try {
                Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                while (reader.ready()) {
                    try {
                        char c = (char) reader.read();
                        addSymbol(c);
                    } catch (Throwable th) {
                        try {
                            reader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                reader.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.symbolsLoaded = true;
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public Map<Character, TextColor> getTextColors() {
        return this.colors.getKeysToValues();
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public Map<TextColor, Character> getInverseTextColors() {
        return this.colors.getValuesToKeys();
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public Map<Character, TextDecoration> getTextDecorations() {
        return this.decorations.getKeysToValues();
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public Map<TextDecoration, Character> getInverseTextDecorations() {
        return this.decorations.getValuesToKeys();
    }

    @Override // net.labymod.api.client.chat.ChatSymbolRegistry
    public Style getStyle(char c) {
        return this.styles.get(Character.valueOf(c));
    }

    private Style createStyleWithColor(TextColor color) {
        return Style.builder().color(color).build();
    }

    private Style createStyleWithDecoration(TextDecoration decoration) {
        return Style.builder().decorate(decoration).build();
    }
}
