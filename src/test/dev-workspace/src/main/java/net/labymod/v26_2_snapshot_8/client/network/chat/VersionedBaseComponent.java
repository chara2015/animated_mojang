package net.labymod.v26_2_snapshot_8.client.network.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.minecraft.network.chat.MutableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/network/chat/VersionedBaseComponent.class */
public abstract class VersionedBaseComponent<T extends Component, C> implements BaseComponent<T> {
    public final MutableComponent holder;

    public VersionedBaseComponent(MutableComponent holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.component.Component
    public Style style() {
        return (Style) CastUtil.cast(this.holder.getStyle());
    }

    @Override // net.labymod.api.client.component.Component
    public List<Component> getChildren() {
        return ((MutableComponentAccessor) CastUtil.requireInstanceOf(this.holder, MutableComponentAccessor.class)).getChildren();
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T copy() {
        return ((MutableComponentAccessor) CastUtil.requireInstanceOf(this.holder.copy(), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T style(Style style) {
        this.holder.setStyle((net.minecraft.network.chat.Style) CastUtil.cast(style));
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(Component component) {
        this.holder.append(((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(int index, Component component) {
        this.holder.getSiblings().add(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component remove(int index) {
        this.holder.getSiblings().remove(index);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component replace(int index, Component component) {
        this.holder.getSiblings().set(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T setChildren(Collection<Component> children) {
        this.holder.getSiblings().clear();
        for (Component child : children) {
            if (child instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> versionedChild = (VersionedBaseComponent) child;
                this.holder.append(versionedChild.holder);
            }
        }
        return this;
    }

    public C getContents() {
        return (C) this.holder.getContents();
    }

    public MutableComponent getHolder() {
        return this.holder;
    }

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
        return ((VersionedBaseComponent) obj).holder.equals(this.holder);
    }

    public int hashCode() {
        return this.holder.hashCode();
    }

    public String toString() {
        return this.holder.toString();
    }
}
