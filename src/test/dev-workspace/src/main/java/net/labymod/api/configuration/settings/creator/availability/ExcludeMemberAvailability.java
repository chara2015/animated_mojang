package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/availability/ExcludeMemberAvailability.class */
public class ExcludeMemberAvailability implements MemberAvailability {
    @Override // net.labymod.api.configuration.settings.creator.availability.MemberAvailability
    public boolean isAvailable(MemberAvailabilityContext context) {
        MemberInspector inspector = context.inspector();
        return !inspector.isAnnotationPresent(Exclude.class);
    }
}
