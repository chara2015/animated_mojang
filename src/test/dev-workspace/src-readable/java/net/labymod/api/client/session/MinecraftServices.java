package net.labymod.api.client.session;

import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.web.request.Response;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/MinecraftServices.class */
@Referenceable
public interface MinecraftServices {
    Response<MojangTextureChangedResponse> getProfile();

    Response<MojangTextureChangedResponse> changeSkin(SkinVariant skinVariant, SkinPayload skinPayload) throws IOException;

    Response<MojangTextureChangedResponse> hideCape();

    Response<MojangTextureChangedResponse> showCape(String str);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/MinecraftServices$SkinVariant.class */
    public enum SkinVariant {
        CLASSIC("classic"),
        SLIM("slim");

        public static final SkinVariant[] VALUES = values();
        private final String id;

        SkinVariant(String id) {
            this.id = id;
        }

        public static SkinVariant of(String id) {
            for (SkinVariant variant : VALUES) {
                if (variant.id.equalsIgnoreCase(id)) {
                    return variant;
                }
            }
            return null;
        }

        public String getId() {
            return this.id;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/MinecraftServices$SkinPayload.class */
    public static class SkinPayload {
        private String url;
        private GameImage gameImage;

        public SkinPayload(String url) {
            this.url = url;
        }

        public SkinPayload(GameImage gameImage) {
            this.gameImage = gameImage;
        }

        @Nullable
        public String getUrl() {
            return this.url;
        }

        @Nullable
        public GameImage getGameImage() {
            return this.gameImage;
        }

        public boolean hasGameImage() {
            return this.gameImage != null;
        }
    }
}
