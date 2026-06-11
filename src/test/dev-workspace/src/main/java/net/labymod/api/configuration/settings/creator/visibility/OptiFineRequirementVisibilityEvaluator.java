package net.labymod.api.configuration.settings.creator.visibility;

import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.loader.annotation.ModRequirement;
import net.labymod.api.configuration.loader.annotation.OptiFineRequirement;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.thirdparty.optifine.OptiFine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/visibility/OptiFineRequirementVisibilityEvaluator.class */
public class OptiFineRequirementVisibilityEvaluator extends VisibilityEvaluator<OptiFineRequirement> {
    public OptiFineRequirementVisibilityEvaluator() {
        super(OptiFineRequirement.class);
    }

    @Override // net.labymod.api.configuration.settings.creator.visibility.VisibilityEvaluator
    public BooleanSupplier canSeeElement(OptiFineRequirement optiFineRequirement, MemberInspector element) {
        boolean requiresInstalled = optiFineRequirement.value() == ModRequirement.RequirementState.INSTALLED;
        return () -> {
            return OptiFine.isPresent() == requiresInstalled;
        };
    }
}
