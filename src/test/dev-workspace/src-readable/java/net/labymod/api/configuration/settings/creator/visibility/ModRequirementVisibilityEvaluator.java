package net.labymod.api.configuration.settings.creator.visibility;

import java.util.function.BooleanSupplier;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonService;
import net.labymod.api.configuration.loader.annotation.ModRequirement;
import net.labymod.api.configuration.loader.annotation.OptiFineRequirement;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.thirdparty.optifine.OptiFine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/visibility/ModRequirementVisibilityEvaluator.class */
public class ModRequirementVisibilityEvaluator extends VisibilityEvaluator<ModRequirement> {
    private final LabyAPI labyAPI;

    public ModRequirementVisibilityEvaluator(LabyAPI labyAPI) {
        super(ModRequirement.class);
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.configuration.settings.creator.visibility.VisibilityEvaluator
    public BooleanSupplier canSeeElement(ModRequirement modRequirement, MemberInspector element) {
        BooleanSupplier installedSupplier;
        boolean requiresInstalled = modRequirement.state() == ModRequirement.RequirementState.INSTALLED;
        String namespace = modRequirement.namespace();
        if (namespace.equals(OptiFine.NAMESPACE) || namespace.equals(OptiFine.FABRIC_MOD_ID)) {
            throw new IllegalStateException("Use @" + OptiFineRequirement.class.getName() + " instead of @" + ModRequirement.class.getName() + " for " + namespace);
        }
        ModRequirement.RequirementType type = modRequirement.type();
        if (type == ModRequirement.RequirementType.ADDON) {
            AddonService addonService = this.labyAPI.addonService();
            installedSupplier = () -> {
                return addonService.getAddon(namespace).isPresent();
            };
        } else {
            ModLoaderRegistry modLoaderRegistry = this.labyAPI.modLoaderRegistry();
            installedSupplier = () -> {
                ModLoader modLoader = modLoaderRegistry.getById(type.getLoaderId());
                if (modLoader == null) {
                    return false;
                }
                return modLoader.isModLoaded(namespace);
            };
        }
        BooleanSupplier booleanSupplier = installedSupplier;
        return () -> {
            return requiresInstalled == booleanSupplier.getAsBoolean();
        };
    }
}
