package net.labymod.api.client.resources.pack;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePackScanner.class */
@Referenceable
public interface ResourcePackScanner {
    void addBlacklist(ResourceFile resourceFile);

    void removeBlacklist(ResourceFile resourceFile);

    void removeBlacklist(String str);

    @ApiStatus.Internal
    void scan();
}
