package net.labymod.api;

import java.util.Collections;
import java.util.List;
import net.labymod.api.models.version.Version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/BuildDataModel.class */
public final class BuildDataModel {
    private Version version;
    private int buildNumber;
    private String releaseType;
    private String commitReference;
    private String branchName;
    private String chatTrustFeature;
    private List<Version> scheduledForRemovals;

    public Version version() {
        return this.version;
    }

    public String getReleaseType() {
        return this.releaseType;
    }

    public String getCommitReference() {
        return this.commitReference;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public String getChatTrustFeature() {
        return this.chatTrustFeature;
    }

    public int getBuildNumber() {
        return this.buildNumber;
    }

    public List<Version> scheduledForRemovals() {
        if (this.scheduledForRemovals == null) {
            return Collections.emptyList();
        }
        return this.scheduledForRemovals;
    }
}
