package net.labymod.api.client.chat;

import java.util.List;
import java.util.Map;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatSymbolRegistry.class */
@Referenceable
public interface ChatSymbolRegistry {
    List<Character> getSymbols();

    void addSymbol(char c);

    void reloadSymbols();

    Map<Character, TextColor> getTextColors();

    Map<TextColor, Character> getInverseTextColors();

    Map<Character, TextDecoration> getTextDecorations();

    Map<TextDecoration, Character> getInverseTextDecorations();

    Style getStyle(char c);

    default Character getParagraph() {
        return (char) 167;
    }

    default Character getAmpersand() {
        return '&';
    }
}
