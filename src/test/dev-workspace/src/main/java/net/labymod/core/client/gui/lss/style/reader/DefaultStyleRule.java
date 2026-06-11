package net.labymod.core.client.gui.lss.style.reader;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.Query;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/reader/DefaultStyleRule.class */
public class DefaultStyleRule implements StyleRule {
    private static final WidgetModifier WIDGET_MODIFIER = Laby.references().widgetModifier();
    private final StyleSheet sourceStyleSheet;
    private final List<StyleBlock> blocks;
    private final String key;
    private final String value;
    private final Query query;

    public DefaultStyleRule(StyleSheet sourceStyleSheet, String key, String value) {
        this(sourceStyleSheet, key + " " + value);
    }

    public DefaultStyleRule(StyleSheet sourceStyleSheet, String content) {
        this.sourceStyleSheet = sourceStyleSheet;
        this.blocks = new ArrayList();
        String[] parts = content.split(" ", 2);
        this.key = parts[0];
        this.value = parts[1];
        this.query = WIDGET_MODIFIER.findQuery(this.key);
    }

    public DefaultStyleRule(StyleSheet sourceStyleSheet, List<StyleBlock> blocks, DefaultStyleRule rule) {
        this.sourceStyleSheet = sourceStyleSheet;
        this.blocks = blocks;
        this.key = rule.key;
        this.value = rule.value;
        this.query = rule.query;
    }

    @Override // net.labymod.api.client.gui.lss.style.reader.StyleRule
    @NotNull
    public StyleSheet sourceStyleSheet() {
        return this.sourceStyleSheet;
    }

    @Override // net.labymod.api.client.gui.lss.style.reader.StyleRule
    public String getKey() {
        return this.key;
    }

    @Override // net.labymod.api.client.gui.lss.style.reader.StyleRule
    public String getValue() {
        return this.value;
    }

    @Override // net.labymod.api.client.gui.lss.style.reader.StyleRule
    public List<StyleBlock> getBlocks() {
        return this.blocks;
    }

    @Override // net.labymod.api.client.gui.lss.style.reader.StyleRule
    public Object process() {
        if (this.query == null) {
            return null;
        }
        return this.query.process(this);
    }
}
