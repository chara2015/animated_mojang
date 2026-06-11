package net.labymod.core.client.session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.models.Implements;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.api.util.io.web.request.FormData;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/DefaultMinecraftServices.class */
@Singleton
@Implements(MinecraftServices.class)
public class DefaultMinecraftServices implements MinecraftServices {
    private static final String URL_PROFILE = "https://api.minecraftservices.com/minecraft/profile%s";
    private static final Gson GSON;
    private final SessionAccessor sessionAccessor;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DefaultMinecraftServices.class.desiredAssertionStatus();
        GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    }

    @Inject
    public DefaultMinecraftServices(SessionAccessor sessionAccessor) {
        this.sessionAccessor = sessionAccessor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.session.MinecraftServices
    public Response<MojangTextureChangedResponse> getProfile() {
        String accessToken = this.sessionAccessor.getSession().getAccessToken();
        return ((GsonRequest) ((GsonRequest) Request.ofGson(MojangTextureChangedResponse.class, (Supplier<Gson>) () -> {
            return GSON;
        }).url(String.format(Locale.ROOT, URL_PROFILE, ""), new Object[0])).authorization("Bearer", accessToken)).executeSync();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request] */
    @Override // net.labymod.api.client.session.MinecraftServices
    public Response<MojangTextureChangedResponse> changeSkin(MinecraftServices.SkinVariant variant, MinecraftServices.SkinPayload payload) throws IOException {
        String accessToken = this.sessionAccessor.getSession().getAccessToken();
        boolean hasGameImage = payload.hasGameImage();
        ?? Authorization = ((GsonRequest) ((GsonRequest) Request.ofGson(MojangTextureChangedResponse.class, (Supplier<Gson>) () -> {
            return GSON;
        }).url(String.format(Locale.ROOT, URL_PROFILE, "/skins"), new Object[0])).method(Request.Method.POST)).authorization("Bearer", accessToken);
        if (hasGameImage) {
            GameImage image = payload.getGameImage();
            if (!$assertionsDisabled && image == null) {
                throw new AssertionError();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.write("png", stream);
            byte[] bytes = stream.toByteArray();
            List<FormData> form = new ArrayList<>();
            form.add(FormData.builder().name("variant").value(variant.getId()).build());
            form.add(FormData.builder().name("file").fileName("skin.png").contentType("image/png").value(bytes).build());
            Authorization.form(form);
        } else {
            JsonObject body = new JsonObject();
            body.addProperty("variant", variant.getId());
            body.addProperty("url", payload.getUrl());
            Authorization.json(body);
        }
        return Authorization.executeSync();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.session.MinecraftServices
    public Response<MojangTextureChangedResponse> hideCape() {
        String accessToken = this.sessionAccessor.getSession().getAccessToken();
        return ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(MojangTextureChangedResponse.class, (Supplier<Gson>) () -> {
            return GSON;
        }).url(String.format(Locale.ROOT, URL_PROFILE, "/capes/active"), new Object[0])).method(Request.Method.DELETE)).authorization("Bearer", accessToken)).executeSync();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.session.MinecraftServices
    public Response<MojangTextureChangedResponse> showCape(String capeId) {
        String accessToken = this.sessionAccessor.getSession().getAccessToken();
        JsonObject body = new JsonObject();
        body.addProperty("capeId", capeId);
        return ((GsonRequest) ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(MojangTextureChangedResponse.class, (Supplier<Gson>) () -> {
            return GSON;
        }).url(String.format(Locale.ROOT, URL_PROFILE, "/capes/active"), new Object[0])).method(Request.Method.PUT)).authorization("Bearer", accessToken)).json((Object) body)).executeSync();
    }
}
