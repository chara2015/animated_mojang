package net.labymod.api.addon.integration;

import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/integration/AddonIntegrationMeta.class */
public class AddonIntegrationMeta {
    private final Class<? extends AddonIntegration> integrationClass;
    private final LoadedAddon addon;
    private final String integratedAddonNamespace;
    private final Object[] constructionArguments;
    private AddonIntegration integration;
    private LoadedAddon integratedAddon;

    public AddonIntegrationMeta(Class<? extends AddonIntegration> integrationClass, LoadedAddon addon, String integratedAddonNamespace, Object[] constructionArguments) {
        this.integrationClass = integrationClass;
        this.addon = addon;
        this.integratedAddonNamespace = integratedAddonNamespace;
        this.constructionArguments = constructionArguments;
    }

    @NotNull
    public AddonIntegration integration() {
        if (this.integration == null) {
            try {
                this.integration = (AddonIntegration) Reflection.instantiateWithArgs(this.integrationClass, this.constructionArguments);
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Failed to create addon integration from class " + this.integrationClass.getName(), exception);
            }
        }
        return this.integration;
    }

    @NotNull
    public LoadedAddon addon() {
        return this.addon;
    }

    @NotNull
    public String getIntegratedAddonNamespace() {
        return this.integratedAddonNamespace;
    }

    @Nullable
    public LoadedAddon getIntegratedAddon() {
        return this.integratedAddon;
    }

    public void setIntegratedAddon(LoadedAddon integratedAddon) {
        if (integratedAddon != null && !integratedAddon.info().getNamespace().equals(this.integratedAddonNamespace)) {
            throw new IllegalArgumentException("Invalid addon, expected namespace " + this.integratedAddonNamespace + ", got " + integratedAddon.info().getNamespace());
        }
        this.integratedAddon = integratedAddon;
    }
}
