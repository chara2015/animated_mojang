package net.labymod.core.client.gui.lss.style;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.lss.style.reader.DefaultStyleBlock;
import net.labymod.core.client.gui.lss.style.reader.DefaultStyleRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/DefaultStyleSheet.class */
public class DefaultStyleSheet implements StyleSheet {
    private static int LOAD_INDEX_NEXT = 0;
    private final ThemeFile file;
    private final List<StyleBlock> blocks;
    private final List<StyleSheet> imports;
    private final List<StyleRule> rules;
    private final List<MediaRule> mediaQueries;
    private LinkMetaList linkMetaList;
    private final int priority;
    private final int loadIndex;
    private boolean loaded;

    public DefaultStyleSheet(ThemeFile file) {
        this.blocks = new ArrayList();
        this.imports = new ArrayList();
        this.rules = new ArrayList();
        this.mediaQueries = new ArrayList();
        this.file = file;
        this.priority = 0;
        int i = LOAD_INDEX_NEXT;
        LOAD_INDEX_NEXT = i + 1;
        this.loadIndex = i;
    }

    private DefaultStyleSheet(DefaultStyleSheet styleSheet, int priority) {
        this.blocks = new ArrayList();
        this.imports = new ArrayList();
        this.rules = new ArrayList();
        this.mediaQueries = new ArrayList();
        this.file = styleSheet.file;
        this.priority = priority;
        int i = LOAD_INDEX_NEXT;
        LOAD_INDEX_NEXT = i + 1;
        this.loadIndex = i;
        for (StyleBlock block : styleSheet.blocks) {
            this.blocks.add(new DefaultStyleBlock(this, (DefaultStyleBlock) block));
        }
        for (StyleRule rule : styleSheet.rules) {
            this.rules.add(mapRule(rule));
        }
        for (MediaRule mediaQuery : styleSheet.mediaQueries) {
            this.mediaQueries.add(new MediaRule(mapRule(mediaQuery.getStyleRule()), mediaQuery.getRequirements(), mediaQuery.getIdentifier()));
        }
    }

    private StyleRule mapRule(StyleRule rule) {
        List<StyleBlock> blocks = new ArrayList<>(rule.getBlocks().size());
        for (StyleBlock block : rule.getBlocks()) {
            blocks.add(new DefaultStyleBlock(this, (DefaultStyleBlock) block));
        }
        return new DefaultStyleRule(rule.sourceStyleSheet(), blocks, (DefaultStyleRule) rule);
    }

    @Override // net.labymod.api.client.resources.ThemeResourceLocation
    public ResourceLocation resource() {
        return this.file.toResourceLocation();
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public ThemeFile file() {
        return this.file;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public void addBlock(StyleBlock block) {
        if (!this.blocks.contains(block)) {
            this.blocks.add(block);
            ((DefaultStyleBlock) block).setStyleSheet(this);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public List<StyleBlock> getBlocks() {
        return this.blocks;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public void addImport(StyleSheet styleSheet) {
        if (!this.imports.contains(styleSheet)) {
            this.imports.add(styleSheet);
            mergeImport(styleSheet);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public List<StyleSheet> getImports() {
        return this.imports;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public List<MediaRule> getMediaRules() {
        return this.mediaQueries;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    @Nullable
    public LinkMetaList getLinkMetaList() {
        return this.linkMetaList;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public void addRule(StyleRule rule) {
        Object processed = rule.process();
        if (processed != null && (processed instanceof MediaRule)) {
            this.mediaQueries.add((MediaRule) processed);
        } else if (!this.rules.contains(rule)) {
            this.rules.add(rule);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public List<StyleRule> getRules() {
        return this.rules;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public StyleRule getRule(String key) {
        for (StyleRule rule : this.rules) {
            if (rule.getKey().equals(key)) {
                return rule;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public StyleRule getRule(String key, String value) {
        for (StyleRule rule : this.rules) {
            if (rule.getKey().equals(key) && rule.getValue().equals(value)) {
                return rule;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public void applyToWidget(Widget widget) {
        for (StyleBlock block : this.blocks) {
            block.applyToWidget(widget, 0);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public List<StyleInstructions> getMatchingMediaRules(Widget widget) {
        List<StyleInstructions> instructions = null;
        for (MediaRule mediaQuery : this.mediaQueries) {
            if (mediaQuery.matches()) {
                for (StyleBlock block : mediaQuery.getBlocks()) {
                    if (instructions == null) {
                        instructions = new ArrayList<>();
                    }
                    instructions.addAll(block.generateAttributePatches(widget, 0).values());
                }
            }
        }
        return instructions;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultStyleSheet that = (DefaultStyleSheet) o;
        return Objects.equals(this.file, that.file);
    }

    public int hashCode() {
        return this.file.hashCode();
    }

    public void setLinkMetaList(LinkMetaList linkMetaList) {
        this.linkMetaList = linkMetaList;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public int getPriority() {
        return this.priority;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public int getLoadIndex() {
        return this.loadIndex;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSheet
    public StyleSheet withPriority(int priority) {
        return new DefaultStyleSheet(this, priority);
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull StyleSheet o) {
        return Integer.compare(getPriority(), o.getPriority());
    }

    private void mergeImport(StyleSheet styleSheet) {
        if (this.loaded) {
            throw new IllegalStateException("This StyleSheet doesn't accept any more imports because it has already been loaded");
        }
        List<StyleBlock> styleSheetBlocks = styleSheet.getBlocks();
        for (int i = styleSheetBlocks.size() - 1; i >= 0; i--) {
            StyleBlock block = styleSheetBlocks.get(i);
            boolean found = false;
            Iterator<StyleBlock> it = this.blocks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StyleBlock styleBlock = it.next();
                if (styleBlock.getRawSelector().equals(block.getRawSelector())) {
                    for (Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                        Map<String, SingleInstruction> instructions = styleBlock.getInstructions();
                        if (!instructions.containsKey(entry.getKey())) {
                            instructions.put(entry.getKey(), entry.getValue());
                        }
                    }
                    found = true;
                }
            }
            if (!found) {
                this.blocks.add(0, block);
            }
        }
        List<StyleRule> styleSheetRules = styleSheet.getRules();
        for (int i2 = styleSheetRules.size() - 1; i2 >= 0; i2--) {
            StyleRule rule = styleSheetRules.get(i2);
            this.rules.add(0, rule);
        }
        List<MediaRule> styleSheetMediaQueries = styleSheet.getMediaRules();
        for (int i3 = styleSheetMediaQueries.size() - 1; i3 >= 0; i3--) {
            MediaRule mediaQuery = styleSheetMediaQueries.get(i3);
            this.mediaQueries.add(0, mediaQuery);
        }
    }

    public void setLoaded() {
        this.loaded = true;
    }
}
