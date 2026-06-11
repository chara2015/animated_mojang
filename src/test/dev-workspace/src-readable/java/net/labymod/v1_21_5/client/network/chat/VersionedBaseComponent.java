package net.labymod.v1_21_5.client.network.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/network/chat/VersionedBaseComponent.class */
public abstract class VersionedBaseComponent<T extends Component, C> implements BaseComponent<T> {
    public final xu holder;

    public VersionedBaseComponent(xu holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.component.Component
    public Style style() {
        return this.holder.a();
    }

    @Override // net.labymod.api.client.component.Component
    public List<Component> getChildren() {
        return this.holder.getChildren();
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T copy() {
        return this.holder.f().getLabyComponent();
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T style(Style style) {
        this.holder.b((yd) style);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(Component component) {
        this.holder.b(((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(int index, Component component) {
        this.holder.c().add(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component remove(int index) {
        this.holder.c().remove(index);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component replace(int index, Component component) {
        this.holder.c().set(index, ((VersionedBaseComponent) component).holder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T setChildren(Collection<Component> children) {
        this.holder.c().clear();
        for (Component child : children) {
            if (child instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> versionedChild = (VersionedBaseComponent) child;
                this.holder.b(versionedChild.holder);
            }
        }
        return this;
    }

    public C getContents() {
        return (C) this.holder.b();
    }

    public xu getHolder() {
        return this.holder;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VersionedBaseComponent)) {
            if (obj instanceof xu) {
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
