package net.labymod.v1_16_5.mixins.client.chat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/chat/MixinBaseComponent.class */
@Mixin({nn.class})
@Implements({@Interface(iface = BaseComponent.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinBaseComponent<T extends Component> implements BaseComponent<T> {

    @Shadow
    private ob f;

    @Shadow
    @Final
    protected List<nr> a;

    @Shadow
    public abstract nx a(ob obVar);

    @Shadow
    public abstract nx shadow$a(nr nrVar);

    @Shadow
    public abstract nx shadow$e();

    @Shadow
    public abstract ob c();

    @Override // net.labymod.api.client.component.Component
    public Style style() {
        return this.f;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T style(Style style) {
        return a((ob) style);
    }

    @Override // net.labymod.api.client.component.Component
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.a);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T setChildren(Collection<Component> children) {
        this.a.clear();
        this.a.addAll(children);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public T append(int index, Component component) {
        this.a.add(index, (nr) component);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component remove(int index) {
        this.a.remove(index);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public Component replace(int index, Component component) {
        this.a.set(index, (nr) component);
        return this;
    }

    @Intrinsic
    public T component$copy() {
        return shadow$e();
    }

    @Intrinsic
    public T component$append(Component component) {
        return shadow$a((nr) component);
    }

    @Redirect(method = {"copy"}, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"))
    public boolean labyMod$copyChildren(List<nr> instance, Collection<nr> children) {
        for (nr child : children) {
            instance.add(child.e());
        }
        return true;
    }
}
