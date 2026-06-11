package net.labymod.v1_21_10.client.network.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.v1_21_10.client.network.chat.contents.TranslatableContentsAccessor;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/network/chat/VersionedTranslatableComponent.class */
public class VersionedTranslatableComponent extends VersionedBaseComponent<TranslatableComponent, TranslatableContentsAccessor> implements TranslatableComponent {
    public VersionedTranslatableComponent(ym holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public TranslatableComponent plainCopy() {
        return (TranslatableComponent) xx.b(getKey()).getLabyComponent();
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
    public List<Component> getArguments() {
        return getContents().getLabyArguments();
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent argument(Component argument) {
        getContents().getLabyArguments().add(argument);
        return this;
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(Component... arguments) {
        return arguments(List.of((Object[]) arguments));
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(Collection<Component> arguments) {
        getContents().getLabyArguments().addAll(arguments);
        return this;
    }
}
