package net.labymod.core.addon.loader.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.BuildData;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.flint.downloader.AddonDownloadRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/update/AddonUpdater.class */
public class AddonUpdater extends AddonLoaderSubService {
    private static final String INVALIDATED_STRING = "INVALIDATED";
    private static final String DELETED_STRING = "DELETED";
    private final List<InstalledAddonInfo> invalidated;
    private final List<InstalledAddonInfo> deleted;
    private final Map<String, UpdateData> updates;

    public AddonUpdater(AddonLoader addonLoader) {
        super(addonLoader, AddonLoaderSubService.SubServiceStage.UPDATE);
        this.invalidated = new ArrayList();
        this.deleted = new ArrayList();
        this.updates = new HashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.addon.loader.AddonLoaderSubService
    public void handle() throws Exception {
        Response<AddonDownloadRequest.AddonDownloadResult> downloadResponse;
        List<InstalledAddonInfo> addons = new ArrayList<>(getAddons());
        if (this.labyModLoader.isAddonDevelopmentEnvironment() || addons.isEmpty()) {
            return;
        }
        this.logger.info("Checking for addon updates...", new Object[0]);
        JsonObject addonsToProof = new JsonObject();
        for (InstalledAddonInfo addonInfo : addons) {
            if (addonInfo.getFileHash() == null) {
                this.logger.warn("The addon {} has no file hash. It will not be checked for updates.", addonInfo.getNamespace());
            }
            addonsToProof.addProperty(addonInfo.getNamespace(), addonInfo.getFileHash());
        }
        Result resultExecuteSync = ((GsonRequest) ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(JsonObject.class).url(FlintUrls.PROOF_MODIFICATION_HASHES, FlintUrls.getCurrentReleaseChannel(), Integer.valueOf(BuildData.getBuildNumber()))).method(Request.Method.POST)).contentType("application/json")).write((Object) addonsToProof.toString())).executeSync();
        if (resultExecuteSync.hasException()) {
            this.logger.error("Could not check for addon updates", resultExecuteSync.exception());
        }
        if (!resultExecuteSync.isPresent()) {
            return;
        }
        HandledUpdateResponse response = handleUpdateResponse(addonsToProof, (JsonObject) resultExecuteSync.get());
        for (InstalledAddonInfo addonInfo2 : addons) {
            String namespace = addonInfo2.getNamespace();
            if (!addonInfo2.isFlintAddon()) {
                if (response.deleted.contains(namespace)) {
                    this.deleted.add(addonInfo2);
                    getAddons().remove(addonInfo2);
                    try {
                        Files.delete(addonInfo2.getPath());
                        this.logger.info("The addon {} has been deleted. This is likely due to a critical issue or the addon is no longer supported/maintained.", namespace);
                    } catch (Exception e) {
                        logError("The addon " + namespace + " was supposed to be deleted. Please delete it manually. Path: " + String.valueOf(addonInfo2.getPath().toAbsolutePath()), e);
                    }
                }
            } else if (response.invalidated.contains(namespace)) {
                this.invalidated.add(addonInfo2);
                getAddons().remove(addonInfo2);
                this.logger.info("The addon {} will not be enabled as it has been invalidated. This is likely due to a critical issue. The addon won't be loaded until the issue has been resolved.", namespace);
            } else if (response.updates.containsKey(namespace)) {
                UpdateData updateData = response.updates.get(namespace);
                this.logger.info("Updating the addon {} from version {} ({}) to {}...", namespace, updateData.getFromVersion(), updateData.getFromHash(), updateData.getToHash());
                try {
                    downloadResponse = AddonDownloadRequest.create().loadUniqueIdFromLoader().path(addonInfo2.getDirectoryPath(), addonInfo2.getFileName()).hash(updateData.getToHash()).namespace(addonInfo2.getNamespace()).existsStrategy((expectedNamespace, info) -> {
                        return true;
                    }).executeSync();
                } catch (Exception e2) {
                    logError("Something went wrong while updating the addon " + namespace, e2);
                }
                if (downloadResponse.hasException()) {
                    logError("Could not download the update of the addon " + namespace, downloadResponse.exception());
                } else {
                    if (downloadResponse.isPresent()) {
                        String toVersion = "unknown";
                        AddonDownloadRequest.AddonDownloadResult result = downloadResponse.get();
                        for (InstalledAddonInfo info2 : result.getAddonInfos()) {
                            if (info2.getNamespace().equals(namespace)) {
                                toVersion = info2.getVersion();
                            }
                            addAddon(info2, true);
                        }
                        this.logger.info("Successfully updated the addon {} from version {} ({}) to version {} ({}})...", namespace, updateData.getFromVersion(), updateData.getFromHash(), toVersion, updateData.getToHash());
                    }
                    this.updates.put(namespace, updateData);
                }
            }
        }
    }

    private HandledUpdateResponse handleUpdateResponse(JsonObject requestObject, JsonObject updateObject) {
        HandledUpdateResponse response = new HandledUpdateResponse(this);
        for (Map.Entry<String, JsonElement> entry : updateObject.entrySet()) {
            String namespace = entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                String correctHash = value.getAsString();
                if (correctHash.equals(INVALIDATED_STRING)) {
                    response.invalidated.add(namespace);
                } else if (correctHash.equals(DELETED_STRING)) {
                    response.deleted.add(namespace);
                } else {
                    InstalledAddonInfo addonInfo = getAddon(namespace);
                    if (addonInfo != null) {
                        String currentHash = requestObject.get(namespace).getAsString();
                        if (!correctHash.equals(currentHash)) {
                            response.updates.put(namespace, new UpdateData(this, addonInfo.getVersion(), currentHash, correctHash));
                        }
                    }
                }
            }
        }
        return response;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/update/AddonUpdater$UpdateData.class */
    public class UpdateData {
        private final String fromVersion;
        private final String fromHash;
        private final String toHash;

        public UpdateData(AddonUpdater this$0, String fromVersion, String fromHash, String toHash) {
            this.fromVersion = fromVersion;
            this.fromHash = fromHash;
            this.toHash = toHash;
        }

        public String getFromVersion() {
            return this.fromVersion;
        }

        public String getFromHash() {
            return this.fromHash;
        }

        public String getToHash() {
            return this.toHash;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/loader/update/AddonUpdater$HandledUpdateResponse.class */
    private class HandledUpdateResponse {
        private final List<String> invalidated = new ArrayList();
        private final List<String> deleted = new ArrayList();
        private final Map<String, UpdateData> updates = new HashMap();

        public HandledUpdateResponse(AddonUpdater addonUpdater) {
        }
    }
}
