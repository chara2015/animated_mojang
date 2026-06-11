package net.labymod.v1_21_11.client.network.chat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.minecraft.network.chat.MutableComponent;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/VersionedBaseComponent.class */
public abstract class VersionedBaseComponent<T extends Component, C> implements BaseComponent<T> {
    public final MutableComponent holder;

    public VersionedBaseComponent(MutableComponent holder) {
        this.holder = holder;
    }

    public Style style() {
        return (Style) CastUtil.cast(this.holder.getStyle());
    }

    public List<Component> getChildren() {
        return ((MutableComponentAccessor) CastUtil.requireInstanceOf(this.holder, MutableComponentAccessor.class)).getChildren();
    }

    public T copy() {
        return ((MutableComponentAccessor) CastUtil.requireInstanceOf(this.holder.copy(), MutableComponentAccessor.class)).getLabyComponent();
    }

    public T style(Style style) {
        this.holder.setStyle((net.minecraft.network.chat.Style) CastUtil.cast(style));
        return this;
    }

    public T append(Component component) {
        this.holder.append(((VersionedBaseComponent) component).holder);
        return this;
    }

    public T append(int index, Component component) {
        this.holder.getSiblings().add(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    public Component remove(int index) {
        this.holder.getSiblings().remove(index);
        return this;
    }

    public Component replace(int index, Component component) {
        this.holder.getSiblings().set(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    public T setChildren(Collection<Component> children) {
        this.holder.getSiblings().clear();
        Iterator<Component> it = children.iterator();
        while (it.hasNext()) {
            VersionedBaseComponent<?, ?> versionedBaseComponent = (Component) it.next();
            if (versionedBaseComponent instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> versionedChild = versionedBaseComponent;
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
