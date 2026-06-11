package net.minecraft.network.chat.contents;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/TranslatableContents.class */
public class TranslatableContents implements ComponentContents {
    private final String key;
    private final String fallback;
    private final Object[] args;
    private Language decomposedWith;
    private List<FormattedText> decomposedParts = ImmutableList.of();
    public static final Object[] NO_ARGS = new Object[0];
    private static final Codec<Object> PRIMITIVE_ARG_CODEC = ExtraCodecs.JAVA.validate(TranslatableContents::filterAllowedArguments);
    private static final Codec<Object> ARG_CODEC = Codec.either(PRIMITIVE_ARG_CODEC, ComponentSerialization.CODEC).xmap($$0 -> {
        return $$0.map($$0 -> {
            return $$0;
        }, $$02 -> {
            return Objects.requireNonNullElse($$02.tryCollapseToString(), $$02);
        });
    }, $$02 -> {
        if (!($$02 instanceof Component)) {
            return Either.left($$02);
        }
        Component $$1 = (Component) $$02;
        return Either.right($$1);
    });
    public static final MapCodec<TranslatableContents> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("translate").forGetter($$0 -> {
            return $$0.key;
        }), Codec.STRING.lenientOptionalFieldOf("fallback").forGetter($$02 -> {
            return Optional.ofNullable($$02.fallback);
        }), ARG_CODEC.listOf().optionalFieldOf("with").forGetter($$03 -> {
            return adjustArgs($$03.args);
        })).apply($$0, TranslatableContents::create);
    });
    private static final FormattedText TEXT_PERCENT = FormattedText.of("%");
    private static final FormattedText TEXT_NULL = FormattedText.of("null");
    private static final Pattern FORMAT_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    private static DataResult<Object> filterAllowedArguments(Object $$0) {
        if (!isAllowedPrimitiveArgument($$0)) {
            return DataResult.error(() -> {
                return "This value needs to be parsed as component";
            });
        }
        return DataResult.success($$0);
    }

    public static boolean isAllowedPrimitiveArgument(Object $$0) {
        return ($$0 instanceof Number) || ($$0 instanceof Boolean) || ($$0 instanceof String);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Optional<List<Object>> adjustArgs(Object[] $$0) {
        return $$0.length == 0 ? Optional.empty() : Optional.of(Arrays.asList($$0));
    }

    private static Object[] adjustArgs(Optional<List<Object>> $$0) {
        return (Object[]) $$0.map($$02 -> {
            return $$02.isEmpty() ? NO_ARGS : $$02.toArray();
        }).orElse(NO_ARGS);
    }

    private static TranslatableContents create(String $$0, Optional<String> $$1, Optional<List<Object>> $$2) {
        return new TranslatableContents($$0, $$1.orElse(null), adjustArgs($$2));
    }

    public TranslatableContents(String $$0, String $$1, Object[] $$2) {
        this.key = $$0;
        this.fallback = $$1;
        this.args = $$2;
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public MapCodec<TranslatableContents> codec() {
        return MAP_CODEC;
    }

    private void decompose() {
        Language $$0 = Language.getInstance();
        if ($$0 == this.decomposedWith) {
            return;
        }
        this.decomposedWith = $$0;
        String $$1 = this.fallback != null ? $$0.getOrDefault(this.key, this.fallback) : $$0.getOrDefault(this.key);
        try {
            ImmutableList.Builder<FormattedText> $$2 = ImmutableList.builder();
            Objects.requireNonNull($$2);
            decomposeTemplate($$1, (v1) -> {
                r2.add(v1);
            });
            this.decomposedParts = $$2.build();
        } catch (TranslatableFormatException e) {
            this.decomposedParts = ImmutableList.of(FormattedText.of($$1));
        }
    }

    private void decomposeTemplate(String $$0, Consumer<FormattedText> $$1) {
        int i;
        Matcher $$2 = FORMAT_PATTERN.matcher($$0);
        int $$3 = 0;
        int $$4 = 0;
        while ($$2.find($$4)) {
            try {
                int $$5 = $$2.start();
                int $$6 = $$2.end();
                if ($$5 > $$4) {
                    String $$7 = $$0.substring($$4, $$5);
                    if ($$7.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    $$1.accept(FormattedText.of($$7));
                }
                String $$8 = $$2.group(2);
                String $$9 = $$0.substring($$5, $$6);
                if ("%".equals($$8) && "%%".equals($$9)) {
                    $$1.accept(TEXT_PERCENT);
                } else if ("s".equals($$8)) {
                    String $$10 = $$2.group(1);
                    if ($$10 != null) {
                        i = Integer.parseInt($$10) - 1;
                    } else {
                        i = $$3;
                        $$3++;
                    }
                    int $$11 = i;
                    $$1.accept(getArgument($$11));
                } else {
                    throw new TranslatableFormatException(this, "Unsupported format: '" + $$9 + "'");
                }
                $$4 = $$6;
            } catch (IllegalArgumentException $$13) {
                throw new TranslatableFormatException(this, $$13);
            }
        }
        if ($$4 < $$0.length()) {
            String $$12 = $$0.substring($$4);
            if ($$12.indexOf(37) != -1) {
                throw new IllegalArgumentException();
            }
            $$1.accept(FormattedText.of($$12));
        }
    }

    private FormattedText getArgument(int $$0) {
        if ($$0 < 0 || $$0 >= this.args.length) {
            throw new TranslatableFormatException(this, $$0);
        }
        Object $$1 = this.args[$$0];
        if (!($$1 instanceof Component)) {
            return $$1 == null ? TEXT_NULL : FormattedText.of($$1.toString());
        }
        Component $$2 = (Component) $$1;
        return $$2;
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
        decompose();
        for (FormattedText $$2 : this.decomposedParts) {
            Optional<T> $$3 = $$2.visit($$0, $$1);
            if ($$3.isPresent()) {
                return $$3;
            }
        }
        return Optional.empty();
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
        decompose();
        for (FormattedText $$1 : this.decomposedParts) {
            Optional<T> $$2 = $$1.visit($$0);
            if ($$2.isPresent()) {
                return $$2;
            }
        }
        return Optional.empty();
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public MutableComponent resolve(CommandSourceStack $$0, Entity $$1, int $$2) throws CommandSyntaxException {
        Object[] $$3 = new Object[this.args.length];
        for (int $$4 = 0; $$4 < $$3.length; $$4++) {
            Object $$5 = this.args[$$4];
            if ($$5 instanceof Component) {
                Component $$6 = (Component) $$5;
                $$3[$$4] = ComponentUtils.updateForEntity($$0, $$6, $$1, $$2);
            } else {
                $$3[$$4] = $$5;
            }
        }
        return MutableComponent.create(new TranslatableContents(this.key, this.fallback, $$3));
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof TranslatableContents) {
            TranslatableContents $$1 = (TranslatableContents) $$0;
            if (Objects.equals(this.key, $$1.key) && Objects.equals(this.fallback, $$1.fallback) && Arrays.equals(this.args, $$1.args)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int $$0 = Objects.hashCode(this.key);
        return (31 * ((31 * $$0) + Objects.hashCode(this.fallback))) + Arrays.hashCode(this.args);
    }

    public String toString() {
        return "translation{key='" + this.key + "'" + (this.fallback != null ? ", fallback='" + this.fallback + "'" : "") + ", args=" + Arrays.toString(this.args) + "}";
    }

    public String getKey() {
        return this.key;
    }

    public String getFallback() {
        return this.fallback;
    }

    public Object[] getArgs() {
        return this.args;
    }
}
