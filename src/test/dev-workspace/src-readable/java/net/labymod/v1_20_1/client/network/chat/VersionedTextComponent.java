package net.labymod.v1_20_1.client.network.chat;

import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.TextComponent;
import net.labymod.v1_20_1.client.network.chat.contents.LiteralContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/network/chat/VersionedTextComponent.class */
public class VersionedTextComponent extends VersionedBaseComponent<TextComponent, sx> implements TextComponent {
    private static final String EMPTY = "";
    private boolean empty;

    public VersionedTextComponent(tj holder, boolean empty) {
        super(holder);
        this.empty = empty;
    }

    @Override // net.labymod.api.client.component.TextComponent
    public String getText() {
        if (this.empty) {
            return EMPTY;
        }
        ub ubVar = (sx) getContents();
        if (ubVar instanceof ub) {
            ub literal = ubVar;
            return literal.a();
        }
        return ubVar.toString();
    }

    @Override // net.labymod.api.client.component.TextComponent
    public TextComponent text(String text) {
        if (this.empty) {
            this.holder.setContents(new ub(text));
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

    @Override // net.labymod.v1_20_1.client.network.chat.VersionedBaseComponent
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VersionedBaseComponent)) {
            if (obj instanceof tj) {
                return this.holder.equals(obj);
            }
            return false;
        }
        if (!(obj instanceof VersionedTextComponent)) {
            return ((VersionedBaseComponent) obj).holder.equals(this.holder);
        }
        VersionedTextComponent textComponent = (VersionedTextComponent) obj;
        return (Objects.equals(getText(), textComponent.getText()) || this.holder.b().equals(textComponent.holder.b())) && this.holder.a().equals(textComponent.holder.a()) && this.holder.c().equals(textComponent.holder.c());
    }

    @Override // net.labymod.v1_20_1.client.network.chat.VersionedBaseComponent
    public int hashCode() {
        return Objects.hash(getText(), this.holder.a(), this.holder.c());
    }
}
