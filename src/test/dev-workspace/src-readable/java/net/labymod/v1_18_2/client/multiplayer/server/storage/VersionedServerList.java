package net.labymod.v1_18_2.client.multiplayer.server.storage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/multiplayer/server/storage/VersionedServerList.class */
public class VersionedServerList extends DefaultServerList {
    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        try {
            this.serverList.clear();
            ok root = ou.b(new File(this.gameDirectory, "servers.dat"));
            if (root == null) {
                return;
            }
            oq list = root.c("servers", 10);
            for (int i = 0; i < list.size(); i++) {
                this.serverList.add(tagToServerData(list.a(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.load();
    }

    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void save() {
        try {
            oq list = new oq();
            for (StorageServerData data : this.serverList) {
                list.add(serverDataToTag(data));
            }
            ok root = new ok();
            root.a("servers", list);
            File tempFile = File.createTempFile("servers", ".dat", this.gameDirectory);
            ou.b(root, tempFile);
            File oldFile = new File(this.gameDirectory, "servers.dat_old");
            File newFile = new File(this.gameDirectory, "servers.dat");
            ad.a(newFile, tempFile, oldFile);
            tempFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save();
    }

    private ok serverDataToTag(StorageServerData data) {
        ok tag = new ok();
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
            ok metaTag = new ok();
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

    private StorageServerData tagToServerData(ok tag) {
        String strE_;
        StorageServerData data = StorageServerData.of(tag.l("name"), tag.l("ip"));
        if (tag.b("icon", 8)) {
            data.setIconBase64(tag.l("icon"));
        }
        if (tag.b("acceptTextures", 1)) {
            if (tag.q("acceptTextures")) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            } else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        } else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (tag.b("labyModMetadata", 10)) {
            ok metaTag = tag.p("labyModMetadata");
            for (String key : metaTag.d()) {
                oh ohVarC = metaTag.c(key);
                if (ohVarC instanceof pb) {
                    strE_ = ohVarC.e_();
                } else if (ohVarC instanceof oh) {
                    oh byteArrayTag = ohVarC;
                    strE_ = new String(byteArrayTag.d(), StandardCharsets.UTF_8);
                }
                String value = strE_;
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
