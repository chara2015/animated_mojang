package net.labymod.core.client.gui.lss.style;

import java.util.Map;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleInstructions;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/DefaultStyleInstruction.class */
public class DefaultStyleInstruction implements StyleInstructions {
    private Selector selector;
    private final StyleBlock block;

    public DefaultStyleInstruction(String rawSubSelector, StyleBlock block) {
        this.selector = new DefaultSelector(rawSubSelector);
        this.block = block;
    }

    public DefaultStyleInstruction(Selector selector, StyleBlock block) {
        this.selector = selector;
        this.block = block;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleInstructions
    public Selector getSelector() {
        return this.selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleInstructions
    public Map<String, SingleInstruction> getInstructions() {
        return this.block.getInstructions();
    }

    @Override // net.labymod.api.client.gui.lss.style.StyleInstructions
    public StyleBlock getBlock() {
        return this.block;
    }
}
