package net.labymod.api.labynet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.labynet.models.NameHistory;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.labynet.models.ServerManifest;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/LabyNetController.class */
@Referenceable
public interface LabyNetController {
    void loadServerData();

    Optional<ServerGroup> getCurrentServer();

    Optional<ServerGroup> getServerByName(String str);

    Optional<ServerGroup> getServerByIp(String str);

    Optional<ServerGroup> getServerByIp(ServerAddress serverAddress);

    void getOrLoadManifest(ServerGroup serverGroup, ResultCallback<ServerManifest> resultCallback);

    void loadNameHistory(UUID uuid, ResultCallback<List<NameHistory>> resultCallback);

    void loadNameHistory(String str, ResultCallback<List<NameHistory>> resultCallback);

    void loadNameByUniqueId(UUID uuid, ResultCallback<String> resultCallback);

    Result<String> loadNameByUniqueIdSync(UUID uuid);

    void loadUniqueIdByName(String str, ResultCallback<UUID> resultCallback);

    Result<UUID> loadUniqueIdByNameSync(String str);

    void loadServiceData(ServiceDataType serviceDataType, ResultCallback<ServiceStatus> resultCallback);

    void loadSocials(UUID uuid, ResultCallback<List<SocialMediaEntry>> resultCallback);
}
