package net.labymod.v26_1_1.client.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/component/VersionedIconContents.class */
public class VersionedIconContents implements ComponentContents {
    private static final MapCodec<VersionedIconContents> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("icon").forGetter(versionedIconContents -> {
            return versionedIconContents.icon().getResourceLocation().toString();
        })).apply($$0, s -> {
            return new VersionedIconContents(Icon.texture(Laby.references().resourceLocationFactory().parse(s)));
        });
    });
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
    public <T> Optional<T> visit(@NotNull FormattedText.ContentConsumer<T> consumer) {
        return consumer.accept(this.placeholder);
    }

    public MapCodec<? extends ComponentContents> codec() {
        return CODEC;
    }

    @NotNull
    public <T> Optional<T> visit(@NotNull FormattedText.StyledContentConsumer<T> consumer, @NotNull Style style) {
        return consumer.accept(style, this.placeholder);
    }

    @NotNull
    public String toString() {
        return "icon{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "'}";
    }
}
