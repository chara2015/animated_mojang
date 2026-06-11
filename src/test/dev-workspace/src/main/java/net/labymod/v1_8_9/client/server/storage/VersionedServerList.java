package net.labymod.v1_8_9.client.server.storage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/server/storage/VersionedServerList.class */
public class VersionedServerList extends DefaultServerList {
    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        try {
            this.serverList.clear();
            dn root = dx.a(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            du list = root.c("servers", 10);
            for (int i = 0; i < list.c(); i++) {
                this.serverList.add(tagToServerData(list.b(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.load();
    }

    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void save() {
        try {
            du list = new du();
            for (StorageServerData data : this.serverList) {
                list.a(serverDataToTag(data));
            }
            dn root = new dn();
            root.a("servers", list);
            dx.a(root, new File(this.gameDirectory, "servers.dat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save();
    }

    private dn serverDataToTag(StorageServerData data) {
        dn tag = new dn();
        tag.a("name", data.getName());
        tag.a("ip", data.address().toString());
        if (data.getIconBase64() != null) {
            tag.a("icon", data.getIconBase64());
        }
        if (data.resourcePackStatus() == ServerResourcePackStatus.ENABLED) {
            tag.a("acceptTextures", true);
        } else if (data.resourcePackStatus() == ServerResourcePackStatus.DISABLED) {
            tag.a("acceptTextures", false);
        }
        if (data.hasMetadata()) {
            dn metaTag = new dn();
            for (Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.a(entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                } else {
                    metaTag.a(entry.getKey(), entry.getValue());
                }
            }
            tag.a("labyModMetadata", metaTag);
        }
        return tag;
    }

    private StorageServerData tagToServerData(dn tag) {
        String strA_;
        StorageServerData data = StorageServerData.of(tag.j("name"), tag.j("ip"));
        if (tag.b("icon", 8)) {
            data.setIconBase64(tag.j("icon"));
        }
        if (tag.b("acceptTextures", 1)) {
            if (tag.n("acceptTextures")) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            } else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        } else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (tag.b("labyModMetadata", 10)) {
            dn metaTag = tag.m("labyModMetadata");
            for (String key : metaTag.c()) {
                dl dlVarA = metaTag.a(key);
                if (dlVarA instanceof ea) {
                    strA_ = ((ea) dlVarA).a_();
                } else if (dlVarA instanceof dl) {
                    strA_ = new String(dlVarA.c(), StandardCharsets.UTF_8);
                }
                String value = strA_;
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
