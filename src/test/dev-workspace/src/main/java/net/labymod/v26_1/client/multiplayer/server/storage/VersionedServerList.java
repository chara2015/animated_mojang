package net.labymod.v26_1.client.multiplayer.server.storage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.network.server.storage.DefaultServerList;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/multiplayer/server/storage/VersionedServerList.class */
public class VersionedServerList extends DefaultServerList {
    @Override // net.labymod.core.client.network.server.storage.DefaultServerList, net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        try {
            this.serverList.clear();
            CompoundTag root = NbtIo.read(this.gameDirectory.toPath().resolve("servers.dat"));
            if (root == null) {
                return;
            } else {
                root.getList("servers").ifPresent(list -> {
                    for (int index = 0; index < list.size(); index++) {
                        list.getCompound(index).ifPresent(tag -> {
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
            ListTag list = new ListTag();
            for (StorageServerData data : this.serverList) {
                list.add(serverDataToTag(data));
            }
            CompoundTag root = new CompoundTag();
            root.put("servers", list);
            Path tempFile = File.createTempFile("servers", ".dat", this.gameDirectory).toPath();
            NbtIo.write(root, tempFile);
            Path oldFile = new File(this.gameDirectory, "servers.dat_old").toPath();
            Path newFile = new File(this.gameDirectory, "servers.dat").toPath();
            Util.safeReplaceFile(newFile, tempFile, oldFile);
            tempFile.toFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save();
    }

    private CompoundTag serverDataToTag(StorageServerData data) {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", data.getName());
        tag.putString("ip", data.address().toString());
        if (data.getIconBase64() != null) {
            tag.putString("icon", data.getIconBase64());
        }
        if (data.resourcePackStatus() == ServerResourcePackStatus.ENABLED) {
            tag.putBoolean("acceptTextures", true);
        } else if (data.resourcePackStatus() == ServerResourcePackStatus.DISABLED) {
            tag.putBoolean("acceptTextures", false);
        }
        if (data.hasMetadata()) {
            CompoundTag metaTag = new CompoundTag();
            for (Map.Entry<String, String> entry : data.metadata().entrySet()) {
                if (entry.getValue().length() > 65535) {
                    metaTag.putByteArray(entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
                } else {
                    metaTag.putString(entry.getKey(), entry.getValue());
                }
            }
            tag.put("labyModMetadata", metaTag);
        }
        return tag;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private StorageServerData tagToServerData(CompoundTag tag) throws MatchException {
        String str;
        StorageServerData data = StorageServerData.of(tag.getStringOr("name", ""), tag.getStringOr("ip", ""));
        if (tag.contains("icon")) {
            data.setIconBase64((String) tag.getString("icon").orElse(null));
        }
        if (tag.contains("acceptTextures")) {
            if (((Boolean) tag.getBoolean("acceptTextures").orElse(false)).booleanValue()) {
                data.setResourcePackStatus(ServerResourcePackStatus.ENABLED);
            } else {
                data.setResourcePackStatus(ServerResourcePackStatus.DISABLED);
            }
        } else {
            data.setResourcePackStatus(ServerResourcePackStatus.PROMPT);
        }
        if (tag.contains("labyModMetadata")) {
            CompoundTag metaTag = tag.getCompoundOrEmpty("labyModMetadata");
            for (String key : metaTag.keySet()) {
                ByteArrayTag byteArrayTag = metaTag.get(key);
                if (byteArrayTag instanceof StringTag) {
                    try {
                        String strValue = ((StringTag) byteArrayTag).value();
                        str = strValue;
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                } else if (byteArrayTag instanceof ByteArrayTag) {
                    ByteArrayTag byteArrayTag2 = byteArrayTag;
                    str = new String(byteArrayTag2.getAsByteArray(), StandardCharsets.UTF_8);
                }
                String value = str;
                data.metadata().put(key, value);
            }
        }
        if (tag.contains(StorageServerData.ACCEPTED_CODE_OF_CONDUCT_KEY)) {
            data.setAcceptedCodeOfConduct(tag.getIntOr(StorageServerData.ACCEPTED_CODE_OF_CONDUCT_KEY, 0));
        }
        return data;
    }
}
