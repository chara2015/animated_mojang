package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.settings.annotation.SettingDevelopment;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/availability/DevelopmentMemberAvailability.class */
public class DevelopmentMemberAvailability implements MemberAvailability {
    private final LabyAPI labyAPI;

    public DevelopmentMemberAvailability(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.configuration.settings.creator.availability.MemberAvailability
    public boolean isAvailable(MemberAvailabilityContext context) {
        MemberInspector inspector = context.inspector();
        boolean annotationPresent = inspector.isAnnotationPresent(SettingDevelopment.class);
        String namespace = context.namespace();
        boolean devEnvironment = this.labyAPI.labyModLoader().isDevelopmentEnvironment(namespace);
        return !annotationPresent || devEnvironment;
    }
}
