package net.labymod.api.client.world.signobject.template;

import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.client.world.signobject.template.SignObjectTemplate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/template/SignObjectFactory.class */
public interface SignObjectFactory<O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate> {
    O newObject(M m, SignObjectPosition signObjectPosition);
}
