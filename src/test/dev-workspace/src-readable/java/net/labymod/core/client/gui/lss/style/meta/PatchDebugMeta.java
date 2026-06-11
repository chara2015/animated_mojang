package net.labymod.core.client.gui.lss.style.meta;

import net.labymod.api.client.gui.lss.style.modifier.attribute.PatchMeta;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.core.client.gui.lss.style.DefaultStyleInstruction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/meta/PatchDebugMeta.class */
public class PatchDebugMeta implements PatchMeta {
    private final DefaultStyleInstruction instruction;

    public PatchDebugMeta(DefaultStyleInstruction instruction) {
        this.instruction = instruction;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.attribute.PatchMeta
    public StyleBlock block() {
        return this.instruction.getBlock();
    }

    public DefaultStyleInstruction getInstruction() {
        return this.instruction;
    }
}
