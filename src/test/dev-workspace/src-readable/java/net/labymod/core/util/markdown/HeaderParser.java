package net.labymod.core.util.markdown;

import java.util.List;
import net.labymod.api.util.markdown.MarkdownComponent;
import net.labymod.api.util.markdown.MarkdownHeaderComponent;
import net.labymod.api.util.markdown.MarkdownParser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/markdown/HeaderParser.class */
public class HeaderParser implements MarkdownComponent.Parser {
    @Override // net.labymod.api.util.markdown.MarkdownComponent.Parser
    public int parse(MarkdownParser.MarkdownLine line, int lineIndex, int lastLineIndex, MarkdownParser.MarkdownLine[] lines, List<MarkdownComponent> components) {
        int level = 0;
        int textIndex = -1;
        char[] trimmedChars = line.getChars();
        int i = 0;
        while (true) {
            if (i < trimmedChars.length) {
                char trimmedChar = trimmedChars[i];
                if (trimmedChar == ' ' && level != 0) {
                    textIndex = i;
                    break;
                }
                if (trimmedChar == '#') {
                    level++;
                }
                i++;
            } else {
                break;
            }
        }
        if (level != 0 && textIndex != -1) {
            components.add(new MarkdownHeaderComponent(level, line.getText().substring(textIndex).trim()));
            return 1;
        }
        return 0;
    }
}
