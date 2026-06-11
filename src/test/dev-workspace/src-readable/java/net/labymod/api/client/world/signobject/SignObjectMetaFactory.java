package net.labymod.api.client.world.signobject;

import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/SignObjectMetaFactory.class */
public interface SignObjectMetaFactory<M extends SignObjectMeta<T>, T extends SignObjectTemplate> {
    M newMeta(T t, String[] strArr);
}
