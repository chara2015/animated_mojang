package net.minecraft.network.chat.contents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/PlainTextContents.class */
public interface PlainTextContents extends ComponentContents {
    public static final MapCodec<PlainTextContents> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf(Display.TextDisplay.TAG_TEXT).forGetter((v0) -> {
            return v0.text();
        })).apply($$0, PlainTextContents::create);
    });
    public static final PlainTextContents EMPTY = new PlainTextContents() { // from class: net.minecraft.network.chat.contents.PlainTextContents.1
        public String toString() {
            return "empty";
        }

        @Override // net.minecraft.network.chat.contents.PlainTextContents
        public String text() {
            return "";
        }
    };

    String text();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/PlainTextContents$LiteralContents.class */
    public static final class LiteralContents extends Record implements PlainTextContents {
        private final String text;

        public LiteralContents(String $$0) {
            this.text = $$0;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LiteralContents.class), LiteralContents.class, "text", "FIELD:Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;->text:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LiteralContents.class, Object.class), LiteralContents.class, "text", "FIELD:Lnet/minecraft/network/chat/contents/PlainTextContents$LiteralContents;->text:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.network.chat.contents.PlainTextContents
        public String text() {
            return this.text;
        }

        @Override // net.minecraft.network.chat.ComponentContents
        public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
            return $$0.accept(this.text);
        }

        @Override // net.minecraft.network.chat.ComponentContents
        public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
            return $$0.accept($$1, this.text);
        }

        @Override // java.lang.Record
        public String toString() {
            return "literal{" + this.text + "}";
        }
    }

    static PlainTextContents create(String $$0) {
        return $$0.isEmpty() ? EMPTY : new LiteralContents($$0);
    }

    @Override // net.minecraft.network.chat.ComponentContents
    default MapCodec<PlainTextContents> codec() {
        return MAP_CODEC;
    }
}
