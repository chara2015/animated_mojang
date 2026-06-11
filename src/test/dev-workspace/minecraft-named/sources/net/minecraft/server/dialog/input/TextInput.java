package net.minecraft.server.dialog.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/TextInput.class */
public final class TextInput extends Record implements InputControl {
    private final int width;
    private final Component label;
    private final boolean labelVisible;
    private final String initial;
    private final int maxLength;
    private final Optional<MultilineOptions> multiline;
    public static final MapCodec<TextInput> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Dialog.WIDTH_CODEC.optionalFieldOf(Display.TAG_WIDTH, 200).forGetter((v0) -> {
            return v0.width();
        }), ComponentSerialization.CODEC.fieldOf("label").forGetter((v0) -> {
            return v0.label();
        }), Codec.BOOL.optionalFieldOf("label_visible", true).forGetter((v0) -> {
            return v0.labelVisible();
        }), Codec.STRING.optionalFieldOf("initial", "").forGetter((v0) -> {
            return v0.initial();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_length", 32).forGetter((v0) -> {
            return v0.maxLength();
        }), MultilineOptions.CODEC.optionalFieldOf("multiline").forGetter((v0) -> {
            return v0.multiline();
        })).apply($$0, (v1, v2, v3, v4, v5, v6) -> {
            return new TextInput(v1, v2, v3, v4, v5, v6);
        });
    }).validate($$02 -> {
        if ($$02.initial.length() > $$02.maxLength()) {
            return DataResult.error(() -> {
                return "Default text length exceeds allowed size";
            });
        }
        return DataResult.success($$02);
    });

    public TextInput(int $$0, Component $$1, boolean $$2, String $$3, int $$4, Optional<MultilineOptions> $$5) {
        this.width = $$0;
        this.label = $$1;
        this.labelVisible = $$2;
        this.initial = $$3;
        this.maxLength = $$4;
        this.multiline = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextInput.class), TextInput.class, "width;label;labelVisible;initial;maxLength;multiline", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->labelVisible:Z", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->initial:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->maxLength:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->multiline:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextInput.class), TextInput.class, "width;label;labelVisible;initial;maxLength;multiline", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->labelVisible:Z", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->initial:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->maxLength:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->multiline:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextInput.class, Object.class), TextInput.class, "width;label;labelVisible;initial;maxLength;multiline", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->labelVisible:Z", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->initial:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->maxLength:I", "FIELD:Lnet/minecraft/server/dialog/input/TextInput;->multiline:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public Component label() {
        return this.label;
    }

    public boolean labelVisible() {
        return this.labelVisible;
    }

    public String initial() {
        return this.initial;
    }

    public int maxLength() {
        return this.maxLength;
    }

    public Optional<MultilineOptions> multiline() {
        return this.multiline;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/TextInput$MultilineOptions.class */
    public static final class MultilineOptions extends Record {
        private final Optional<Integer> maxLines;
        private final Optional<Integer> height;
        public static final int MAX_HEIGHT = 512;
        public static final Codec<MultilineOptions> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(ExtraCodecs.POSITIVE_INT.optionalFieldOf("max_lines").forGetter((v0) -> {
                return v0.maxLines();
            }), ExtraCodecs.intRange(1, 512).optionalFieldOf(Display.TAG_HEIGHT).forGetter((v0) -> {
                return v0.height();
            })).apply($$0, MultilineOptions::new);
        });

        public MultilineOptions(Optional<Integer> $$0, Optional<Integer> $$1) {
            this.maxLines = $$0;
            this.height = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MultilineOptions.class), MultilineOptions.class, "maxLines;height", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->maxLines:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->height:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MultilineOptions.class), MultilineOptions.class, "maxLines;height", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->maxLines:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->height:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MultilineOptions.class, Object.class), MultilineOptions.class, "maxLines;height", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->maxLines:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/TextInput$MultilineOptions;->height:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Optional<Integer> maxLines() {
            return this.maxLines;
        }

        public Optional<Integer> height() {
            return this.height;
        }
    }

    @Override // net.minecraft.server.dialog.input.InputControl
    public MapCodec<TextInput> mapCodec() {
        return MAP_CODEC;
    }
}
