package net.labymod.core.main.user.shop.item.geometry;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.main.user.shop.JsonBodyDeserializer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/GeometryLoader.class */
public class GeometryLoader {
    private final GeometryFile geometryFile;

    public GeometryLoader(@NotNull GeometryFile geometryFile) {
        this.geometryFile = geometryFile;
    }

    public GeometryLoader(@NotNull String json) throws Exception {
        this(deserialize(json));
    }

    public GeometryLoader(@NotNull InputStream inputStream) throws Exception {
        this(deserialize(inputStream));
        inputStream.close();
    }

    public GeometryLoader(@NotNull File file) throws Exception {
        this(IOUtil.newInputStream(file.toPath()));
    }

    @NotNull
    public GeometryFile getGeometry() {
        return this.geometryFile;
    }

    private static GeometryFile deserialize(String json) throws Exception {
        return (GeometryFile) JsonBodyDeserializer.defaultGson().deserialize(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), GeometryFile.class);
    }

    private static GeometryFile deserialize(InputStream inputStream) throws Exception {
        return (GeometryFile) JsonBodyDeserializer.defaultGson().deserialize(inputStream, GeometryFile.class);
    }
}
