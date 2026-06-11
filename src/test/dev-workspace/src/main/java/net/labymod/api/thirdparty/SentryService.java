package net.labymod.api.thirdparty;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/thirdparty/SentryService.class */
@ApiStatus.Internal
@Referenceable
public interface SentryService {
    void capture(Throwable th);
}
