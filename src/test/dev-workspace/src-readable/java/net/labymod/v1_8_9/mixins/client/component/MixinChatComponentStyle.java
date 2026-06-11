package net.labymod.v1_8_9.mixins.client.component;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinChatComponentStyle.class */
@Mixin({es.class})
public abstract class MixinChatComponentStyle<T extends Component> implements BaseComponent<T> {

    @Shadow
    protected List<eu> a;

    @Shadow
    private ez b;

    @Shadow
    public abstract eu a(ez ezVar);

    @Shadow
    public abstract eu a(eu euVar);

    @Shadow
    public abstract ez b();

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
        Iterator<eu> it = this.a.iterator();
        while (it.hasNext()) {
            t.append(((eu) it.next()).copy());
        }
        return t;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T style(Style style) {
        return a((ez) style);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(Component component) {
        return a((eu) component);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(int index, Component component) {
        ((eu) component).b().a(b());
        this.a.add(index, (eu) component);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component remove(int index) {
        this.a.remove(index);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component replace(int index, Component component) {
        ((eu) component).b().a(b());
        this.a.set(index, (eu) component);
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
