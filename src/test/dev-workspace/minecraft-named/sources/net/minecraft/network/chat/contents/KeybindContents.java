package net.minecraft.network.chat.contents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/KeybindContents.class */
public class KeybindContents implements ComponentContents {
    public static final MapCodec<KeybindContents> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("keybind").forGetter($$0 -> {
            return $$0.name;
        })).apply($$0, KeybindContents::new);
    });
    private final String name;
    private Supplier<Component> nameResolver;

    public KeybindContents(String $$0) {
        this.name = $$0;
    }

    private Component getNestedComponent() {
        if (this.nameResolver == null) {
            this.nameResolver = KeybindResolver.keyResolver.apply(this.name);
        }
        return this.nameResolver.get();
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
        return getNestedComponent().visit($$0);
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
        return getNestedComponent().visit($$0, $$1);
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof KeybindContents) {
            KeybindContents $$1 = (KeybindContents) $$0;
            if (this.name.equals($$1.name)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return "keybind{" + this.name + "}";
    }

    public String getName() {
        return this.name;
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public MapCodec<KeybindContents> codec() {
        return MAP_CODEC;
    }
}
