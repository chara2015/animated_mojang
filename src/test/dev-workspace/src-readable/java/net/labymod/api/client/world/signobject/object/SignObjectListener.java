package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/SignObjectListener.class */
public interface SignObjectListener<M extends SignObjectMeta<? extends SignObjectTemplate>> {
    void onDispose(SignObject<M> signObject);
}
