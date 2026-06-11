package net.labymod.v1_12_2.mixins.client.component;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/component/MixinChatComponentStyle.class */
@Mixin({he.class})
public abstract class MixinChatComponentStyle<T extends Component> implements BaseComponent<T> {

    @Shadow
    protected List<hh> a;

    @Shadow
    private hn b;

    @Shadow
    public abstract hh a(hn hnVar);

    @Shadow
    public abstract hh a(hh hhVar);

    @Shadow
    public abstract hn b();

    @Override // net.labymod.api.client.component.Component
    public Style style() {
        return b();
    }

    @Override // net.labymod.api.client.component.Component
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.a);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T copy() {
        T t = plainCopy();
        t.style(style());
        Iterator<hh> it = this.a.iterator();
        while (it.hasNext()) {
            t.append(((hh) it.next()).copy());
        }
        return t;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T style(Style style) {
        return a((hn) style);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(Component component) {
        return a((hh) component);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(int index, Component component) {
        ((hh) component).b().a(b());
        this.a.add(index, (hh) component);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component remove(int index) {
        this.a.remove(index);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component replace(int index, Component component) {
        ((hh) component).b().a(b());
        this.a.set(index, (hh) component);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T setChildren(Collection<Component> children) {
        this.a.clear();
        this.a.addAll(children);
        return this;
    }

    @Overwrite
    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
