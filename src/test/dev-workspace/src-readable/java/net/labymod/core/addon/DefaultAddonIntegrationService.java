package net.labymod.core.addon;

import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.addon.integration.AddonIntegration;
import net.labymod.api.addon.integration.AddonIntegrationMeta;
import net.labymod.api.addon.integration.AddonIntegrationService;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.addon.lifecycle.GlobalAddonEnableEvent;
import net.labymod.api.event.addon.lifecycle.GlobalAddonPostEnableEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/DefaultAddonIntegrationService.class */
@Singleton
@Implements(AddonIntegrationService.class)
public class DefaultAddonIntegrationService implements AddonIntegrationService {
    private static final Object[] EMPTY_CONSTRUCTION_ARGUMENTS = new Object[0];
    private final Collection<AddonIntegrationMeta> integrations = new ArrayList();

    public DefaultAddonIntegrationService() {
        Laby.labyAPI().eventBus().registerListener(this);
    }

    @Override // net.labymod.api.addon.integration.AddonIntegrationService
    public void registerIntegration(String integratedAddonNamespace, Class<? extends AddonIntegration> integration) {
        registerIntegration(integratedAddonNamespace, integration, EMPTY_CONSTRUCTION_ARGUMENTS);
    }

    @Override // net.labymod.api.addon.integration.AddonIntegrationService
    public void registerIntegration(String integratedAddonNamespace, Class<? extends AddonIntegration> integration, Object... constructionArguments) {
        LoadedAddon addon = Laby.labyAPI().addonService().getLastCallerAddon();
        if (addon == null) {
            throw new IllegalStateException("This method must be invoked from an addon!");
        }
        if (CollectionHelper.noneMatch(addon.info().getCompatibleAddonDependencies(Laby.labyAPI().labyModLoader().version()), dep -> {
            return dep.getNamespace().equals(integratedAddonNamespace);
        })) {
            throw new IllegalArgumentException("Addon " + addon.info().getNamespace() + " must have an addon dependency on " + integratedAddonNamespace + " (dependency can be marked as optional)");
        }
        AddonIntegrationMeta meta = new AddonIntegrationMeta(integration, addon, integratedAddonNamespace, constructionArguments);
        this.integrations.add(meta);
        Laby.labyAPI().addonService().getAddon(integratedAddonNamespace).ifPresent(integrated -> {
            loadIntegration(integrated, meta);
            postLoadIntegration(meta);
        });
    }

    private void loadIntegrations(LoadedAddon integratedAddon) {
        for (AddonIntegrationMeta meta : this.integrations) {
            if (meta.getIntegratedAddonNamespace().equals(integratedAddon.info().getNamespace())) {
                loadIntegration(integratedAddon, meta);
            }
        }
    }

    private void loadIntegration(LoadedAddon integratedAddon, AddonIntegrationMeta meta) {
        AddonIntegration integration = meta.integration();
        meta.setIntegratedAddon(integratedAddon);
        integration.load();
    }

    private void postLoadIntegrations(InstalledAddonInfo integratedAddon) {
        for (AddonIntegrationMeta meta : this.integrations) {
            if (meta.getIntegratedAddonNamespace().equals(integratedAddon.getNamespace())) {
                postLoadIntegration(meta);
            }
        }
    }

    private void postLoadIntegration(AddonIntegrationMeta meta) {
        AddonIntegration integration = meta.integration();
        AddonConfig integratedConfig = DefaultAddonService.getInstance().getMainConfiguration(meta.getIntegratedAddonNamespace());
        if (integratedConfig != null && integratedConfig.enabled() != null) {
            integratedConfig.enabled().addChangeListener((type, oldValue, newValue) -> {
                invokeEnableUpdate(integration, newValue != null && newValue.booleanValue());
            });
            invokeEnableUpdate(integration, integratedConfig.enabled().getOrDefault(true).booleanValue());
        }
    }

    private void invokeEnableUpdate(AddonIntegration integration, boolean enabled) {
        if (enabled) {
            integration.onIntegratedAddonEnable();
        } else {
            integration.onIntegratedAddonDisable();
        }
    }

    @Override // net.labymod.api.addon.integration.AddonIntegrationService
    public Collection<AddonIntegrationMeta> getByIntegrating(String addonNamespace) {
        return CollectionHelper.filter(this.integrations, meta -> {
            return meta.addon().info().getNamespace().equals(addonNamespace);
        });
    }

    @Override // net.labymod.api.addon.integration.AddonIntegrationService
    public Collection<AddonIntegrationMeta> getByIntegrated(String integratedAddonNamespace) {
        return CollectionHelper.filter(this.integrations, meta -> {
            return meta.getIntegratedAddonNamespace().equals(integratedAddonNamespace);
        });
    }

    @Subscribe
    public void addonEnabled(GlobalAddonEnableEvent event) {
        loadIntegrations(event.addon());
    }

    @Subscribe
    public void addonPostEnabled(GlobalAddonPostEnableEvent event) {
        postLoadIntegrations(event.addonInfo());
    }
}
