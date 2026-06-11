package net.labymod.api.addon.integration;

import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/integration/AddonIntegrationService.class */
@Referenceable
public interface AddonIntegrationService {
    void registerIntegration(String str, Class<? extends AddonIntegration> cls);

    void registerIntegration(String str, Class<? extends AddonIntegration> cls, Object... objArr);

    Collection<AddonIntegrationMeta> getByIntegrating(String str);

    Collection<AddonIntegrationMeta> getByIntegrated(String str);
}
