package net.labymod.core.client.gui.lss.style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleSelectorList;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.style.modifier.attribute.VariableAttributePatch;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/DefaultStyleSelectorList.class */
public abstract class DefaultStyleSelectorList implements StyleSelectorList {
    private final WidgetModifier widgetModifier = Laby.references().widgetModifier();
    protected final Map<String, StyleInstructions> patches = new HashMap();
    protected final List<DefaultStyleInstruction> selectors = new ArrayList();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract StyleSheet styleSheet();

    @Override // net.labymod.api.client.gui.lss.style.StyleSelectorList
    public void add(String rawSubSelector, StyleBlock instruction) {
        this.selectors.add(new DefaultStyleInstruction(rawSubSelector, instruction));
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSelectorList
    public void applyToWidget(Widget widget, int skipDepth) {
        boolean modified = false;
        for (DefaultStyleInstruction block : this.selectors) {
            Selector selector = block.getSelector();
            if (selector.match(skipDepth, widget, false)) {
                for (Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                    String key = entry.getKey();
                    SingleInstruction instruction = entry.getValue();
                    String value = instruction.getValue();
                    AttributePatch patch = makePatch(widget, instruction, key, value, block);
                    if (patch != null) {
                        modified = true;
                        widget.addAttributePatch(selector, patch, skipDepth);
                    }
                }
            }
        }
        if (modified) {
            widget.updateState(true);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleSelectorList
    public Map<String, StyleInstructions> generateAttributePatches(Widget widget, int skipDepth) {
        this.patches.clear();
        for (DefaultStyleInstruction block : this.selectors) {
            Selector selector = block.getSelector();
            if (selector.match(skipDepth, widget, false)) {
                for (Map.Entry<String, SingleInstruction> entry : block.getInstructions().entrySet()) {
                    String key = entry.getKey();
                    if (!this.patches.containsKey(key)) {
                        SingleInstruction instruction = entry.getValue();
                        String value = instruction.getValue();
                        AttributePatch patch = makePatch(widget, instruction, key, value, block);
                        if (patch != null) {
                            this.patches.put(key, new StyleInstructions(selector, patch, skipDepth));
                        }
                    }
                }
            }
        }
        return this.patches;
    }

    private AttributePatch makePatch(Widget widget, SingleInstruction instruction, String key, String value, DefaultStyleInstruction block) {
        AttributePatch patch;
        if (this.widgetModifier.isVariableKey(key)) {
            patch = new VariableAttributePatch(instruction, value);
        } else {
            Forwarder forwarder = this.widgetModifier.findForwarder(widget, key);
            patch = this.widgetModifier.makeAttributePatch(widget, forwarder, instruction, value);
        }
        if (patch != null) {
            patch.setMeta(block);
        }
        if (patch != null) {
            try {
                patch.init();
            } catch (LssStyleException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return patch;
    }
}
