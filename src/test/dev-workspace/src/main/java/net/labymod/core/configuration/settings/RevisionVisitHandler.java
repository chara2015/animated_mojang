package net.labymod.core.configuration.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.BuildData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationLoadEvent;
import net.labymod.api.models.version.Version;
import net.labymod.api.revision.Revision;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.configuration.labymod.main.laby.DefaultOtherConfig;
import net.labymod.core.main.LabyMod;
import net.labymod.core.revision.PopupRevision;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/settings/RevisionVisitHandler.class */
public class RevisionVisitHandler {
    private static final String KEY = "lastStartedVersion";
    private final RevisionRegistry revisionRegistry = LabyMod.references().revisionRegistry();

    @Subscribe
    public void load(ConfigurationLoadEvent event) {
        if (event.clazz() != DefaultOtherConfig.class) {
            return;
        }
        JsonObject object = event.jsonObject();
        Version currentVersion = BuildData.version();
        if (object.has(KEY)) {
            JsonElement element = object.get(KEY);
            String lastStartedVersion = element.getAsString();
            Version parsedVersion = VersionDeserializer.from(lastStartedVersion);
            if (parsedVersion instanceof SemanticVersion) {
                SemanticVersion version = (SemanticVersion) parsedVersion;
                Revision lastRevision = this.revisionRegistry.getLastRevision("labymod");
                if (lastRevision != null && lastRevision.isRelevant() && version.isLowerThan((Version) lastRevision.version()) && (lastRevision instanceof PopupRevision)) {
                    PopupRevision popupRevision = (PopupRevision) lastRevision;
                    boolean isNewUser = lastStartedVersion.equals("0.0.0");
                    popupRevision.visit(isNewUser);
                }
            }
        } else {
            Revision lastRevision2 = this.revisionRegistry.getLastRevision("labymod");
            if (lastRevision2 != null && lastRevision2.isRelevant() && (lastRevision2 instanceof PopupRevision)) {
                PopupRevision popupRevision2 = (PopupRevision) lastRevision2;
                popupRevision2.visit(true);
            }
        }
        object.addProperty(KEY, currentVersion.toString());
    }
}
