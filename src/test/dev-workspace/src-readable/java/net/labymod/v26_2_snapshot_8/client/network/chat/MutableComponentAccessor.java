package net.labymod.v26_2_snapshot_8.client.network.chat;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.minecraft.network.chat.ComponentContents;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/network/chat/MutableComponentAccessor.class */
public interface MutableComponentAccessor {
    VersionedBaseComponent<? extends Component, ?> getLabyComponent();

    List<Component> getChildren();

    void setContents(ComponentContents componentContents);
}
