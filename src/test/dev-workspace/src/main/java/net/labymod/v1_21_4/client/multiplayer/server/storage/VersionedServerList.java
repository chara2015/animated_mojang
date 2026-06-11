package net.labymod.v1_21_4.client.multiplayer.server.storage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/multiplayer/server/storage/VersionedServerList.class */
public class VersionedServerList extends DefaultServerList {
    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        try {
            this.serverList.clear();
            tq root = ud.a(this.gameDirectory.toPath().resolve("servers.dat"));
            if (root == null) {
                return;
            }
            tw list = root.c("servers", 10);
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
            tw list = new tw();
            for (StorageServerData data : this.serverList) {
                list.add(serverDataToTag(data));
            }
            tq root = new tq();
            root.a("servers", list);
            Path tempFile = File.createTempFile("servers", ".dat", this.gameDirectory).toPath();
            ud.b(root, tempFile);
            Path oldFile = new File(this.gameDirectory, "servers.dat_old").toPath();
            Path newFile = new File(this.gameDirectory, "servers.dat").toPath();
            af.a(newFile, tempFile, oldFile);
            tempFile.toFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save();
    }

    private tq serverDataToTag(StorageServerData data) {
        tq tag = new tq();
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
            tq metaTag = new tq();
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

    private StorageServerData tagToServerData(tq tag) {
        String strU_;
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
            tq metaTag = tag.p("labyModMetadata");
            for (String key : metaTag.e()) {
                tn tnVarC = metaTag.c(key);
                if (tnVarC instanceof ul) {
                    strU_ = tnVarC.u_();
                } else if (tnVarC instanceof tn) {
                    tn byteArrayTag = tnVarC;
                    strU_ = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                }
                String value = strU_;
                data.metadata().put(key, value);
            }
        }
        return data;
    }
}
