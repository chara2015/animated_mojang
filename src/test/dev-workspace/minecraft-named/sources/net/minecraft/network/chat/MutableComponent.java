package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.util.FormattedCharSequence;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/MutableComponent.class */
public final class MutableComponent implements Component {
    private final ComponentContents contents;
    private final List<Component> siblings;
    private Style style;
    private FormattedCharSequence visualOrderText = FormattedCharSequence.EMPTY;
    private Language decomposedWith;

    MutableComponent(ComponentContents $$0, List<Component> $$1, Style $$2) {
        this.contents = $$0;
        this.siblings = $$1;
        this.style = $$2;
    }

    public static MutableComponent create(ComponentContents $$0) {
        return new MutableComponent($$0, Lists.newArrayList(), Style.EMPTY);
    }

    @Override // net.minecraft.network.chat.Component
    public ComponentContents getContents() {
        return this.contents;
    }

    @Override // net.minecraft.network.chat.Component
    public List<Component> getSiblings() {
        return this.siblings;
    }

    public MutableComponent setStyle(Style $$0) {
        this.style = $$0;
        return this;
    }

    @Override // net.minecraft.network.chat.Component
    public Style getStyle() {
        return this.style;
    }

    public MutableComponent append(String $$0) {
        if ($$0.isEmpty()) {
            return this;
        }
        return append(Component.literal($$0));
    }

    public MutableComponent append(Component $$0) {
        this.siblings.add($$0);
        return this;
    }

    public MutableComponent withStyle(UnaryOperator<Style> $$0) {
        setStyle((Style) $$0.apply(getStyle()));
        return this;
    }

    public MutableComponent withStyle(Style $$0) {
        setStyle($$0.applyTo(getStyle()));
        return this;
    }

    public MutableComponent withStyle(ChatFormatting... $$0) {
        setStyle(getStyle().applyFormats($$0));
        return this;
    }

    public MutableComponent withStyle(ChatFormatting $$0) {
        setStyle(getStyle().applyFormat($$0));
        return this;
    }

    public MutableComponent withColor(int $$0) {
        setStyle(getStyle().withColor($$0));
        return this;
    }

    public MutableComponent withoutShadow() {
        setStyle(getStyle().withoutShadow());
        return this;
    }

    @Override // net.minecraft.network.chat.Component
    public FormattedCharSequence getVisualOrderText() {
        Language $$0 = Language.getInstance();
        if (this.decomposedWith != $$0) {
            this.visualOrderText = $$0.getVisualOrder(this);
            this.decomposedWith = $$0;
        }
        return this.visualOrderText;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof MutableComponent) {
            MutableComponent $$1 = (MutableComponent) $$0;
            if (this.contents.equals($$1.contents) && this.style.equals($$1.style) && this.siblings.equals($$1.siblings)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int $$0 = (31 * 1) + this.contents.hashCode();
        return (31 * ((31 * $$0) + this.style.hashCode())) + this.siblings.hashCode();
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder(this.contents.toString());
        boolean $$1 = !this.style.isEmpty();
        boolean $$2 = !this.siblings.isEmpty();
        if ($$1 || $$2) {
            $$0.append('[');
            if ($$1) {
                $$0.append("style=");
                $$0.append(this.style);
            }
            if ($$1 && $$2) {
                $$0.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT);
            }
            if ($$2) {
                $$0.append("siblings=");
                $$0.append(this.siblings);
            }
            $$0.append(']');
        }
        return $$0.toString();
    }
}
