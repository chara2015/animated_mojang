package net.labymod.core.main.user.shop;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.laby.lib.bedrock.BedrockGson;
import net.laby.lib.core.serialization.DefaultEnumTypeAdapterFactory;
import net.laby.lib.http.validation.BodyDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/JsonBodyDeserializer.class */
public class JsonBodyDeserializer {
    private static final Gson GSON = BedrockGson.builder().registerTypeAdapterFactory(DefaultEnumTypeAdapterFactory.create()).create();
    private static final BodyDeserializer DEFAULT = new BodyDeserializer() { // from class: net.labymod.core.main.user.shop.JsonBodyDeserializer.1
        public <T> T deserialize(InputStream inputStream, Class<T> cls) throws Exception {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            try {
                T t = (T) JsonBodyDeserializer.GSON.fromJson(inputStreamReader, cls);
                inputStreamReader.close();
                return t;
            } catch (Throwable th) {
                try {
                    inputStreamReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    };

    public static BodyDeserializer defaultGson() {
        return DEFAULT;
    }
}
