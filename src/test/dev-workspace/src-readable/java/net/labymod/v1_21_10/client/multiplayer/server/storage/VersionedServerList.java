package net.labymod.v1_21_10.client.multiplayer.server.storage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/multiplayer/server/storage/VersionedServerList.class */
public class VersionedServerList extends DefaultServerList {
    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        try {
            this.serverList.clear();
            up root = vc.a(this.gameDirectory.toPath().resolve("servers.dat"));
            if (root == null) {
                return;
            } else {
                root.o("servers").ifPresent(list -> {
                    for (int index = 0; index < list.size(); index++) {
                        list.a(index).ifPresent(tag -> {
                            this.serverList.add(tagToServerData(tag));
                        });
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.load();
    }

    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void save() {
        try {
            uv list = new uv();
            for (StorageServerData data : this.serverList) {
                list.add(serverDataToTag(data));
            }
            up root = new up();
            root.a("servers", list);
            Path tempFile = File.createTempFile("servers", ".dat", this.gameDirectory).toPath();
            vc.b(root, tempFile);
            Path oldFile = new File(this.gameDirectory, "servers.dat_old").toPath();
            Path newFile = new File(this.gameDirectory, "servers.dat").toPath();
            ag.a(newFile, tempFile, oldFile);
            tempFile.toFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save();
    }

    private up serverDataToTag(StorageServerData data) {
        up tag = new up();
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
            up metaTag = new up();
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

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private StorageServerData tagToServerData(up tag) throws MatchException {
        String str;
        StorageServerData data = StorageServerData.of(tag.b("name", ""), tag.b("ip", ""));
        if (tag.b("icon")) {
            data.setIconBase64((String) tag.i("icon").orElse(null));
        }
        if (tag.b("acceptTextures")) {
            if (((Boolean) tag.q("acceptTextures").orElse(false)).booleanValue()) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            } else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        } else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (tag.b("labyModMetadata")) {
            up metaTag = tag.n("labyModMetadata");
            for (String key : metaTag.e()) {
                um umVarA = metaTag.a(key);
                if (umVarA instanceof vn) {
                    try {
                        String strValue = ((vn) umVarA).k();
                        str = strValue;
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                } else if (umVarA instanceof um) {
                    um byteArrayTag = umVarA;
                    str = new String(byteArrayTag.e(), StandardCharsets.UTF_8);
                }
                String value = str;
                data.metadata().put(key, value);
            }
        }
        if (tag.b(StorageServerData.ACCEPTED_CODE_OF_CONDUCT_KEY)) {
            data.setAcceptedCodeOfConduct(tag.b(StorageServerData.ACCEPTED_CODE_OF_CONDUCT_KEY, 0));
        }
        return data;
    }
}
