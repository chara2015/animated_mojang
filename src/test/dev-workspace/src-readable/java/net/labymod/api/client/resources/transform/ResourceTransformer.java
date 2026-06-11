package net.labymod.api.client.resources.transform;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/transform/ResourceTransformer.class */
@Referenceable(named = true)
public interface ResourceTransformer {
    byte[] transform(byte[] bArr);

    default int priority() {
        return 0;
    }
}
