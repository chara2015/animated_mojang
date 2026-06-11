package net.labymod.api.client.gui.lss.style;

import java.util.List;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.resources.ThemeResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/StyleSheet.class */
public interface StyleSheet extends ThemeResourceLocation, Comparable<StyleSheet> {
    ThemeFile file();

    void addBlock(StyleBlock styleBlock);

    List<StyleBlock> getBlocks();

    void addImport(StyleSheet styleSheet);

    List<StyleSheet> getImports();

    List<MediaRule> getMediaRules();

    @Nullable
    LinkMetaList getLinkMetaList();

    void addRule(StyleRule styleRule);

    List<StyleRule> getRules();

    StyleRule getRule(String str);

    StyleRule getRule(String str, String str2);

    void applyToWidget(Widget widget);

    List<net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions> getMatchingMediaRules(Widget widget);

    int getPriority();

    int getLoadIndex();

    StyleSheet withPriority(int i);
}
