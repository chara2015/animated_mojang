package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.Objects;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/AttributePatch.class */
public abstract class AttributePatch {
    private final SingleInstruction instruction;
    private final String rawValue;
    private net.labymod.api.client.gui.lss.style.StyleInstructions meta;

    public abstract void init() throws LssStyleException;

    public abstract void patch(Widget widget);

    public abstract void unpatch(Widget widget);

    public AttributePatch(SingleInstruction instruction, String rawValue) {
        this.instruction = instruction;
        this.rawValue = rawValue;
    }

    public String getKey() {
        return this.instruction.getKey();
    }

    public SingleInstruction instruction() {
        return this.instruction;
    }

    public String rawValue() {
        return this.rawValue;
    }

    public void setMeta(net.labymod.api.client.gui.lss.style.StyleInstructions meta) {
        this.meta = meta;
    }

    public net.labymod.api.client.gui.lss.style.StyleInstructions getMeta() {
        return this.meta;
    }

    public boolean equalsKey(AttributePatch patch) {
        return getKey().equals(patch.getKey());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttributePatch that = (AttributePatch) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(rawValue(), that.rawValue());
    }

    public int hashCode() {
        int result = this.instruction != null ? this.instruction.hashCode() : 0;
        return (31 * ((31 * result) + (this.rawValue != null ? this.rawValue.hashCode() : 0))) + (this.meta != null ? this.meta.hashCode() : 0);
    }

    public String toString() {
        return this.instruction.toString();
    }
}
