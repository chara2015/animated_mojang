package net.labymod.api.client.render.matrix;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/StackProviderFactory.class */
@Referenceable
public interface StackProviderFactory {
    Stack create();

    Stack create(Object obj);

    Stack create(Object obj, Object obj2);
}
