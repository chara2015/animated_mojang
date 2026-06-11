package net.labymod.api.configuration.settings.creator.visibility;

import java.util.function.BooleanSupplier;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.annotation.ModLoaderRequirement;
import net.labymod.api.configuration.loader.annotation.ModRequirement;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/visibility/ModLoaderVisibilityEvaluator.class */
public class ModLoaderVisibilityEvaluator extends VisibilityEvaluator<ModLoaderRequirement> {
    private final LabyAPI labyAPI;

    public ModLoaderVisibilityEvaluator(LabyAPI labyAPI) {
        super(ModLoaderRequirement.class);
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.configuration.settings.creator.visibility.VisibilityEvaluator
    public BooleanSupplier canSeeElement(ModLoaderRequirement modLoaderRequirement, MemberInspector element) {
        boolean requiresInstalled = modLoaderRequirement.state() == ModRequirement.RequirementState.INSTALLED;
        ModLoaderRegistry modLoaderRegistry = this.labyAPI.modLoaderRegistry();
        return () -> {
            ModLoader loader = modLoaderRegistry.getById(modLoaderRequirement.loaderId());
            return requiresInstalled == (loader != null);
        };
    }
}
