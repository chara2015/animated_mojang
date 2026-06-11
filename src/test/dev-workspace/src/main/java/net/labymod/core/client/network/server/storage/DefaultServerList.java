package net.labymod.core.client.network.server.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Constants;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.client.network.server.storage.ServerFolder;
import net.labymod.api.client.network.server.storage.ServerList;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.util.Color;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/storage/DefaultServerList.class */
public abstract class DefaultServerList implements ServerList {
    private static final Gson GSON = new GsonBuilder().create();
    private static final Logging LOGGER = Logging.getLogger();
    protected final List<StorageServerData> serverList = Lists.newArrayList();
    protected final File gameDirectory = IOUtil.toFile(DefaultLabyModLoader.getInstance().getGameDirectory());
    protected final List<ServerFolder> folders = Lists.newArrayList();

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public StorageServerData get(int index) {
        return this.serverList.get(index);
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void remove(StorageServerData data) {
        this.serverList.remove(data);
        saveAsync();
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void add(StorageServerData data) {
        this.serverList.add(data);
        saveAsync();
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void load() {
        if (IOUtil.exists(Constants.Files.SERVER_FOLDERS)) {
            try {
                BufferedReader reader = Files.newBufferedReader(Constants.Files.SERVER_FOLDERS);
                try {
                    JsonReader jsonReader = new JsonReader(reader);
                    try {
                        ServerFolder[] folders = (ServerFolder[]) GSON.fromJson(jsonReader, ServerFolder[].class);
                        if (folders != null) {
                            this.folders.addAll(Lists.newArrayList(folders));
                        }
                        jsonReader.close();
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (Throwable th) {
                        try {
                            jsonReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } finally {
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load server folders", e);
            }
        }
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void save() {
        try {
            if (this.folders.isEmpty()) {
                if (IOUtil.exists(Constants.Files.SERVER_FOLDERS)) {
                    IOUtil.delete(Constants.Files.SERVER_FOLDERS);
                }
            } else {
                GsonUtil.writeJson(GSON, Constants.Files.SERVER_FOLDERS, this.folders);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save server folders", e);
        }
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void saveAsync() {
        Debounce.of("server-list-save", 1000L, this::save);
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public int size() {
        return this.serverList.size();
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void swap(int index1, int index2, ServerFolder layer) {
        MoveActionType moveActionType;
        if (index1 < index2) {
            moveActionType = MoveActionType.INSERT_BELOW;
        } else {
            moveActionType = MoveActionType.INSERT_ABOVE;
        }
        move(index1, index2, moveActionType, layer);
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public boolean move(int source, int destination, MoveActionType type, ServerFolder layer) {
        int endIndex;
        boolean success = false;
        try {
            ServerFolder sourceFolder = getFolder(source);
            ServerFolder destinationFolder = getFolder(destination);
            boolean isInAFolder = layer != null;
            boolean sourceIsServer = isInAFolder || sourceFolder == null;
            boolean destinationIsServer = isInAFolder || destinationFolder == null;
            int destinationStart = destinationIsServer ? destination : destinationFolder.getStartIndex();
            int destinationEnd = destinationIsServer ? destination : destinationFolder.getEndIndex();
            if (type == MoveActionType.ADD_TO_FOLDER) {
                if (sourceIsServer && !destinationIsServer) {
                    moveServer(source, destinationEnd + 1, true);
                    success = true;
                }
            } else if (type == MoveActionType.INSERT_ABOVE) {
                if (sourceIsServer) {
                    boolean sameIndex = source + 1 == destination;
                    if (!sameIndex) {
                        moveServer(source, destinationStart, isInAFolder);
                        success = true;
                    }
                } else {
                    moveFolder(source, destinationStart);
                    success = true;
                }
            } else if (type == MoveActionType.INSERT_BELOW) {
                if (sourceIsServer) {
                    boolean sameIndex2 = source == destinationEnd + 1;
                    if (!sameIndex2) {
                        moveServer(source, destinationEnd + 1, isInAFolder);
                        success = true;
                    }
                } else {
                    moveFolder(source, destinationEnd + 1);
                    success = true;
                }
            } else if (type == MoveActionType.REMOVE_FROM_FOLDER && sourceFolder != null) {
                if (source == sourceFolder.getStartIndex()) {
                    endIndex = sourceFolder.getEndIndex() + 1;
                } else {
                    endIndex = sourceFolder.getEndIndex();
                }
                moveServer(source, endIndex, false);
                success = true;
            }
            saveAsync();
        } catch (Exception e) {
            LOGGER.error("Failed to move server", e);
        }
        return success;
    }

    private void moveServer(int source, int destination, boolean prioritizeResizeFolder) {
        int offset;
        if (source != destination) {
            StorageServerData data = this.serverList.remove(source);
            int shift = source + 1 < destination ? -1 : 0;
            this.serverList.add(Math.max(destination + shift, 0), data);
        }
        for (ServerFolder folder : this.folders) {
            int start = folder.getStartIndex();
            int end = folder.getEndIndex();
            if (prioritizeResizeFolder) {
                if (source >= start && source <= end) {
                    folder.shrink();
                }
                if (destination >= start && destination <= end + 1) {
                    folder.expand();
                }
            } else {
                if (source >= start && source <= end) {
                    folder.shrink();
                }
                if (destination > start && destination < end) {
                    folder.expand();
                }
            }
            int start2 = folder.getStartIndex();
            if (prioritizeResizeFolder) {
                offset = source < start2 ? 0 - 1 : 0;
                if (destination < start2) {
                    offset++;
                }
            } else {
                offset = source < start2 ? 0 - 1 : 0;
                if (destination <= start2) {
                    offset++;
                }
            }
            folder.shift(offset);
        }
        this.folders.removeIf((v0) -> {
            return v0.isEmpty();
        });
    }

    private void moveFolder(int source, int destination) {
        ServerFolder folder = getFolder(source);
        if (folder == null) {
            return;
        }
        this.folders.remove(folder);
        int length = folder.getLength();
        for (int i = 0; i < length; i++) {
            int sourceShift = source < destination ? 0 : i;
            int destShift = source + 1 < destination ? -i : 0;
            moveServer(source + sourceShift, destination + i + destShift, false);
        }
        this.folders.add(folder);
        this.folders.sort(Comparator.comparingInt((v0) -> {
            return v0.getStartIndex();
        }));
        folder.shift(source < destination ? (destination - source) - length : destination - source);
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void replace(int index, StorageServerData data) {
        this.serverList.set(index, data);
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public void update(StorageServerData serverData) {
        for (int i = 0; i < this.serverList.size(); i++) {
            StorageServerData currentData = this.serverList.get(i);
            if (currentData.equals(serverData)) {
                replace(i, serverData);
                return;
            }
        }
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public boolean has(ServerAddress address) {
        StorageServerData serverData = null;
        Iterator<StorageServerData> it = this.serverList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StorageServerData storageServerData = it.next();
            if (address.equals(storageServerData.address())) {
                serverData = storageServerData;
                break;
            }
        }
        return serverData != null;
    }

    @Override // net.labymod.api.client.network.server.storage.ServerList
    public int index(StorageServerData serverData) {
        return this.serverList.indexOf(serverData);
    }

    public ServerFolder getFolder(int index) {
        for (ServerFolder folder : this.folders) {
            if (index >= folder.getStartIndex() && index <= folder.getEndIndex()) {
                return folder;
            }
        }
        return null;
    }

    public ServerFolder getOrCreateFolder(int index) {
        ServerFolder folder = getFolder(index);
        if (folder == null) {
            List<ServerFolder> list = this.folders;
            ServerFolder serverFolder = new ServerFolder("", index, 1, Color.WHITE);
            folder = serverFolder;
            list.add(serverFolder);
            this.folders.sort(Comparator.comparingInt((v0) -> {
                return v0.getStartIndex();
            }));
        }
        return folder;
    }

    public void removeFolder(ServerFolder folder, boolean removeServers) {
        if (removeServers) {
            int start = folder.getStartIndex();
            for (int i = 0; i < folder.getLength(); i++) {
                this.serverList.remove(start);
            }
        }
        this.folders.remove(folder);
    }

    public boolean hasFolder(int index) {
        return getFolder(index) != null;
    }
}
