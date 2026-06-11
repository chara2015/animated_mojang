package net.labymod.v1_21_11.client.network.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.network.chat.contents.TranslatableContentsAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/VersionedTranslatableComponent.class */
public class VersionedTranslatableComponent extends VersionedBaseComponent<TranslatableComponent, TranslatableContentsAccessor> implements TranslatableComponent {
    public VersionedTranslatableComponent(MutableComponent holder) {
        super(holder);
    }

    /* JADX INFO: renamed from: plainCopy, reason: merged with bridge method [inline-methods] */
    public TranslatableComponent m23plainCopy() {
        return ((MutableComponentAccessor) CastUtil.requireInstanceOf(Component.literal(getKey()), MutableComponentAccessor.class)).getLabyComponent();
    }

    public String getKey() {
        return getContents().getKey();
    }

    @Nullable
    public String getFallback() {
        return getContents().getFallback();
    }

    public List<net.labymod.api.client.component.Component> getArguments() {
        return getContents().getLabyArguments();
    }

    public TranslatableComponent argument(net.labymod.api.client.component.Component argument) {
        getContents().getLabyArguments().add(argument);
        return this;
    }

    public TranslatableComponent arguments(net.labymod.api.client.component.Component... arguments) {
        return arguments(List.of((Object[]) arguments));
    }

    public TranslatableComponent arguments(Collection<net.labymod.api.client.component.Component> arguments) {
        getContents().getLabyArguments().addAll(arguments);
        return this;
    }
}
