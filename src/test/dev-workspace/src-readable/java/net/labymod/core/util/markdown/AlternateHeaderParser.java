package net.labymod.core.util.markdown;

import java.util.List;
import net.labymod.api.util.markdown.MarkdownAlternateHeaderComponent;
import net.labymod.api.util.markdown.MarkdownComponent;
import net.labymod.api.util.markdown.MarkdownParser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/markdown/AlternateHeaderParser.class */
public class AlternateHeaderParser implements MarkdownComponent.Parser {
    @Override // net.labymod.api.util.markdown.MarkdownComponent.Parser
    public int parse(MarkdownParser.MarkdownLine line, int lineIndex, int lastLineIndex, MarkdownParser.MarkdownLine[] lines, List<MarkdownComponent> components) {
        int i;
        if (lineIndex == lastLineIndex) {
            return 0;
        }
        MarkdownParser.MarkdownLine nextLine = lines[lineIndex + 1];
        int level = 0;
        for (char c : nextLine.getTrimmedChars()) {
            if (c == '=') {
                if (level == 0 || level == 1) {
                    i = 1;
                } else {
                    return 0;
                }
            } else if (c == '-') {
                if (level == 0 || level == 2) {
                    i = 2;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
            level = i;
        }
        if (level == 0) {
            return 0;
        }
        components.add(new MarkdownAlternateHeaderComponent(level, line.getTrimmedText()));
        return 2;
    }
}
