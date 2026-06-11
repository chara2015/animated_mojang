package net.labymod.v1_21_3.client.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/component/VersionedIconContents.class */
public class VersionedIconContents implements xw {
    private static final MapCodec<VersionedIconContents> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("icon").forGetter(versionedIconContents -> {
            return versionedIconContents.icon().getResourceLocation().toString();
        })).apply($$0, s -> {
            return new VersionedIconContents(Icon.texture(Laby.references().resourceLocationFactory().parse(s)));
        });
    });
    private static final a<VersionedIconContents> TYPE = new a<>(CODEC, "icon");
    private Icon icon;
    private int width;
    private int height;
    private String placeholder;

    public VersionedIconContents(Icon icon) {
        this.icon = icon;
    }

    @NotNull
    public Icon icon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int width() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @NotNull
    public String placeholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @NotNull
    public <T> Optional<T> a(@NotNull a<T> consumer) {
        return consumer.accept(this.placeholder);
    }

    public a<?> a() {
        return TYPE;
    }

    @NotNull
    public <T> Optional<T> a(@NotNull b<T> consumer, @NotNull ys style) {
        return consumer.accept(style, this.placeholder);
    }

    @NotNull
    public String toString() {
        return "icon{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "'}";
    }
}
