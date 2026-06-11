package net.labymod.v1_21_11.client.network.chat;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.minecraft.network.chat.ComponentContents;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/MutableComponentAccessor.class */
public interface MutableComponentAccessor {
    VersionedBaseComponent<? extends Component, ?> getLabyComponent();

    List<Component> getChildren();

    void setContents(ComponentContents componentContents);
}
