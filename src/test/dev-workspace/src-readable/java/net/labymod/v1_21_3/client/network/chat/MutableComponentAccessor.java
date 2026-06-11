package net.labymod.v1_21_3.client.network.chat;

import java.util.List;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/network/chat/MutableComponentAccessor.class */
public interface MutableComponentAccessor {
    VersionedBaseComponent<? extends Component, ?> getLabyComponent();

    List<Component> getChildren();

    void setContents(xw xwVar);
}
