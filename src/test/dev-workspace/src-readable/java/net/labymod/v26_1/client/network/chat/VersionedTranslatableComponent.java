package net.labymod.v26_1.client.network.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1.client.network.chat.contents.TranslatableContentsAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/network/chat/VersionedTranslatableComponent.class */
public class VersionedTranslatableComponent extends VersionedBaseComponent<TranslatableComponent, TranslatableContentsAccessor> implements TranslatableComponent {
    public VersionedTranslatableComponent(MutableComponent holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public TranslatableComponent plainCopy() {
        return (TranslatableComponent) ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.literal(getKey()), MutableComponentAccessor.class)).getLabyComponent();
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public String getKey() {
        return getContents().getKey();
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    @Nullable
    public String getFallback() {
        return getContents().getFallback();
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public List<net.labymod.api.client.component.Component> getArguments() {
        return getContents().getLabyArguments();
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent argument(net.labymod.api.client.component.Component argument) {
        getContents().getLabyArguments().add(argument);
        return this;
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(net.labymod.api.client.component.Component... arguments) {
        return arguments(List.of((Object[]) arguments));
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(Collection<net.labymod.api.client.component.Component> arguments) {
        getContents().getLabyArguments().addAll(arguments);
        return this;
    }
}
