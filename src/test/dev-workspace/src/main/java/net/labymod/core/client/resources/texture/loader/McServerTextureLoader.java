package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerPinger;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.resources.texture.TextureRepository;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/McServerTextureLoader.class */
public class McServerTextureLoader implements TextureLoader {
    private final ServerPinger serverPinger = Laby.references().serverPinger();
    private final TextureRepository textureRepository;
    private TextureImage defaultIcon;

    public McServerTextureLoader(TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals("mcserver");
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation location, CompletableTextureImage target) throws IOException {
        String host = uri.getHost();
        if (host.equalsIgnoreCase("default")) {
            completeDefaultIcon(target);
            return;
        }
        int port = uri.getPort();
        if (port != -1) {
            host = host + ":" + port;
        }
        ServerAddress address = ServerAddress.resolve(host);
        ServerInfo serverInfo = this.serverPinger.pingServer(host, address, 5000, true);
        if (serverInfo == null || serverInfo.getRawIcon() == null) {
            completeDefaultIcon(target);
        } else {
            this.textureRepository.executeTextureLoader(serverInfo.getRawIcon(), target);
        }
    }

    private void completeDefaultIcon(CompletableTextureImage target) {
        if (this.defaultIcon != null) {
            target.executeCompletableListeners(this.defaultIcon);
        } else {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                try {
                    this.defaultIcon = new TextureImage(Textures.DEFAULT_SERVER_ICON);
                    target.executeCompletableListeners(this.defaultIcon);
                } catch (IOException exception) {
                    target.stopLoadingOnError();
                    exception.printStackTrace();
                }
            });
        }
    }
}
