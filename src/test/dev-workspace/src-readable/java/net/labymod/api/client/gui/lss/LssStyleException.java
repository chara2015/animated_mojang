package net.labymod.api.client.gui.lss;

import java.util.Locale;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/LssStyleException.class */
public class LssStyleException extends Exception {
    private final StyleSheet styleSheet;
    private final SingleInstruction instruction;

    public LssStyleException(StyleSheet styleSheet, SingleInstruction instruction, String message) {
        super(String.format(Locale.ROOT, "Failed to parse style instruction from %s:%s: %s", styleSheet.file(), Integer.valueOf(instruction.getLineNumber()), message));
        this.styleSheet = styleSheet;
        this.instruction = instruction;
    }

    public StyleSheet styleSheet() {
        return this.styleSheet;
    }

    public SingleInstruction instruction() {
        return this.instruction;
    }
}
