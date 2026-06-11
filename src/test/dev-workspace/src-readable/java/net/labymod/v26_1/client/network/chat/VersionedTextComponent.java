package net.labymod.v26_1.client.network.chat;

import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1.client.network.chat.contents.LiteralContentsAccessor;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/network/chat/VersionedTextComponent.class */
public class VersionedTextComponent extends VersionedBaseComponent<TextComponent, ComponentContents> implements TextComponent {
    private static final String EMPTY = "";
    private boolean empty;

    public VersionedTextComponent(MutableComponent holder, boolean empty) {
        super(holder);
        this.empty = empty;
    }

    @Override // net.labymod.api.client.component.TextComponent
    public String getText() {
        if (this.empty) {
            return EMPTY;
        }
        PlainTextContents.LiteralContents literalContents = (ComponentContents) getContents();
        if (literalContents instanceof PlainTextContents.LiteralContents) {
            PlainTextContents.LiteralContents literal = literalContents;
            return literal.text();
        }
        return literalContents.toString();
    }

    @Override // net.labymod.api.client.component.TextComponent
    public TextComponent text(String text) {
        if (this.empty) {
            ((MutableComponentAccessor) CastUtil.requireInstanceOf(this.holder, MutableComponentAccessor.class)).setContents(new PlainTextContents.LiteralContents(text));
            this.empty = false;
            return this;
        }
        LiteralContentsAccessor contents = getContents();
        if (contents instanceof LiteralContentsAccessor) {
            LiteralContentsAccessor literal = contents;
            literal.setText(text);
            return this;
        }
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public TextComponent plainCopy() {
        return this.empty ? ComponentService.empty() : ComponentService.textComponent(getText());
    }

    @Override // net.labymod.v26_1.client.network.chat.VersionedBaseComponent
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VersionedBaseComponent)) {
            if (obj instanceof MutableComponent) {
                return this.holder.equals(obj);
            }
            return false;
        }
        if (!(obj instanceof VersionedTextComponent)) {
            return ((VersionedBaseComponent) obj).holder.equals(this.holder);
        }
        VersionedTextComponent textComponent = (VersionedTextComponent) obj;
        return (Objects.equals(getText(), textComponent.getText()) || this.holder.getContents().equals(textComponent.holder.getContents())) && this.holder.getStyle().equals(textComponent.holder.getStyle()) && this.holder.getSiblings().equals(textComponent.holder.getSiblings());
    }

    @Override // net.labymod.v26_1.client.network.chat.VersionedBaseComponent
    public int hashCode() {
        return Objects.hash(getText(), this.holder.getStyle(), this.holder.getSiblings());
    }
}
