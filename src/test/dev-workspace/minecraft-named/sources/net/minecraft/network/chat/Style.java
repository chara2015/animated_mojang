package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/Style.class */
public final class Style {
    public static final Style EMPTY = new Style(null, null, null, null, null, null, null, null, null, null, null);
    public static final int NO_SHADOW = 0;
    final TextColor color;
    final Integer shadowColor;
    final Boolean bold;
    final Boolean italic;
    final Boolean underlined;
    final Boolean strikethrough;
    final Boolean obfuscated;
    final ClickEvent clickEvent;
    final HoverEvent hoverEvent;
    final String insertion;
    final FontDescription font;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/Style$Serializer.class */
    public static class Serializer {
        public static final MapCodec<Style> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(TextColor.CODEC.optionalFieldOf("color").forGetter($$0 -> {
                return Optional.ofNullable($$0.color);
            }), ExtraCodecs.ARGB_COLOR_CODEC.optionalFieldOf("shadow_color").forGetter($$02 -> {
                return Optional.ofNullable($$02.shadowColor);
            }), Codec.BOOL.optionalFieldOf("bold").forGetter($$03 -> {
                return Optional.ofNullable($$03.bold);
            }), Codec.BOOL.optionalFieldOf("italic").forGetter($$04 -> {
                return Optional.ofNullable($$04.italic);
            }), Codec.BOOL.optionalFieldOf("underlined").forGetter($$05 -> {
                return Optional.ofNullable($$05.underlined);
            }), Codec.BOOL.optionalFieldOf("strikethrough").forGetter($$06 -> {
                return Optional.ofNullable($$06.strikethrough);
            }), Codec.BOOL.optionalFieldOf("obfuscated").forGetter($$07 -> {
                return Optional.ofNullable($$07.obfuscated);
            }), ClickEvent.CODEC.optionalFieldOf("click_event").forGetter($$08 -> {
                return Optional.ofNullable($$08.clickEvent);
            }), HoverEvent.CODEC.optionalFieldOf("hover_event").forGetter($$09 -> {
                return Optional.ofNullable($$09.hoverEvent);
            }), Codec.STRING.optionalFieldOf("insertion").forGetter($$010 -> {
                return Optional.ofNullable($$010.insertion);
            }), FontDescription.CODEC.optionalFieldOf("font").forGetter($$011 -> {
                return Optional.ofNullable($$011.font);
            })).apply($$0, Style::create);
        });
        public static final Codec<Style> CODEC = MAP_CODEC.codec();
        public static final StreamCodec<RegistryFriendlyByteBuf, Style> TRUSTED_STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistriesTrusted(CODEC);
    }

    private static Style create(Optional<TextColor> $$0, Optional<Integer> $$1, Optional<Boolean> $$2, Optional<Boolean> $$3, Optional<Boolean> $$4, Optional<Boolean> $$5, Optional<Boolean> $$6, Optional<ClickEvent> $$7, Optional<HoverEvent> $$8, Optional<String> $$9, Optional<FontDescription> $$10) {
        Style $$11 = new Style($$0.orElse(null), $$1.orElse(null), $$2.orElse(null), $$3.orElse(null), $$4.orElse(null), $$5.orElse(null), $$6.orElse(null), $$7.orElse(null), $$8.orElse(null), $$9.orElse(null), $$10.orElse(null));
        if ($$11.equals(EMPTY)) {
            return EMPTY;
        }
        return $$11;
    }

    private Style(TextColor $$0, Integer $$1, Boolean $$2, Boolean $$3, Boolean $$4, Boolean $$5, Boolean $$6, ClickEvent $$7, HoverEvent $$8, String $$9, FontDescription $$10) {
        this.color = $$0;
        this.shadowColor = $$1;
        this.bold = $$2;
        this.italic = $$3;
        this.underlined = $$4;
        this.strikethrough = $$5;
        this.obfuscated = $$6;
        this.clickEvent = $$7;
        this.hoverEvent = $$8;
        this.insertion = $$9;
        this.font = $$10;
    }

    public TextColor getColor() {
        return this.color;
    }

    public Integer getShadowColor() {
        return this.shadowColor;
    }

    public boolean isBold() {
        return this.bold == Boolean.TRUE;
    }

    public boolean isItalic() {
        return this.italic == Boolean.TRUE;
    }

    public boolean isStrikethrough() {
        return this.strikethrough == Boolean.TRUE;
    }

    public boolean isUnderlined() {
        return this.underlined == Boolean.TRUE;
    }

    public boolean isObfuscated() {
        return this.obfuscated == Boolean.TRUE;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }

    public String getInsertion() {
        return this.insertion;
    }

    public FontDescription getFont() {
        return this.font != null ? this.font : FontDescription.DEFAULT;
    }

    private static <T> Style checkEmptyAfterChange(Style $$0, T $$1, T $$2) {
        if ($$1 != null && $$2 == null && $$0.equals(EMPTY)) {
            return EMPTY;
        }
        return $$0;
    }

    public Style withColor(TextColor $$0) {
        if (Objects.equals(this.color, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style($$0, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.color, $$0);
    }

    public Style withColor(ChatFormatting $$0) {
        return withColor($$0 != null ? TextColor.fromLegacyFormat($$0) : null);
    }

    public Style withColor(int $$0) {
        return withColor(TextColor.fromRgb($$0));
    }

    public Style withShadowColor(int $$0) {
        if (Objects.equals(this.shadowColor, Integer.valueOf($$0))) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, Integer.valueOf($$0), this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.shadowColor, Integer.valueOf($$0));
    }

    public Style withoutShadow() {
        return withShadowColor(0);
    }

    public Style withBold(Boolean $$0) {
        if (Objects.equals(this.bold, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, $$0, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.bold, $$0);
    }

    public Style withItalic(Boolean $$0) {
        if (Objects.equals(this.italic, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, $$0, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.italic, $$0);
    }

    public Style withUnderlined(Boolean $$0) {
        if (Objects.equals(this.underlined, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, $$0, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.underlined, $$0);
    }

    public Style withStrikethrough(Boolean $$0) {
        if (Objects.equals(this.strikethrough, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, $$0, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.strikethrough, $$0);
    }

    public Style withObfuscated(Boolean $$0) {
        if (Objects.equals(this.obfuscated, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, $$0, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.obfuscated, $$0);
    }

    public Style withClickEvent(ClickEvent $$0) {
        if (Objects.equals(this.clickEvent, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, $$0, this.hoverEvent, this.insertion, this.font), this.clickEvent, $$0);
    }

    public Style withHoverEvent(HoverEvent $$0) {
        if (Objects.equals(this.hoverEvent, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, $$0, this.insertion, this.font), this.hoverEvent, $$0);
    }

    public Style withInsertion(String $$0) {
        if (Objects.equals(this.insertion, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, $$0, this.font), this.insertion, $$0);
    }

    public Style withFont(FontDescription $$0) {
        if (Objects.equals(this.font, $$0)) {
            return this;
        }
        return checkEmptyAfterChange(new Style(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, $$0), this.font, $$0);
    }

    public Style applyFormat(ChatFormatting $$0) {
        TextColor $$1 = this.color;
        Boolean $$2 = this.bold;
        Boolean $$3 = this.italic;
        Boolean $$4 = this.strikethrough;
        Boolean $$5 = this.underlined;
        Boolean $$6 = this.obfuscated;
        switch ($$0) {
            case OBFUSCATED:
                $$6 = true;
                break;
            case BOLD:
                $$2 = true;
                break;
            case STRIKETHROUGH:
                $$4 = true;
                break;
            case UNDERLINE:
                $$5 = true;
                break;
            case ITALIC:
                $$3 = true;
                break;
            case RESET:
                return EMPTY;
            default:
                $$1 = TextColor.fromLegacyFormat($$0);
                break;
        }
        return new Style($$1, this.shadowColor, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style applyLegacyFormat(ChatFormatting $$0) {
        TextColor $$1 = this.color;
        Boolean $$2 = this.bold;
        Boolean $$3 = this.italic;
        Boolean $$4 = this.strikethrough;
        Boolean $$5 = this.underlined;
        Boolean $$6 = this.obfuscated;
        switch ($$0) {
            case OBFUSCATED:
                $$6 = true;
                break;
            case BOLD:
                $$2 = true;
                break;
            case STRIKETHROUGH:
                $$4 = true;
                break;
            case UNDERLINE:
                $$5 = true;
                break;
            case ITALIC:
                $$3 = true;
                break;
            case RESET:
                return EMPTY;
            default:
                $$6 = false;
                $$2 = false;
                $$4 = false;
                $$5 = false;
                $$3 = false;
                $$1 = TextColor.fromLegacyFormat($$0);
                break;
        }
        return new Style($$1, this.shadowColor, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style applyFormats(ChatFormatting... $$0) {
        TextColor $$1 = this.color;
        Boolean $$2 = this.bold;
        Boolean $$3 = this.italic;
        Boolean $$4 = this.strikethrough;
        Boolean $$5 = this.underlined;
        Boolean $$6 = this.obfuscated;
        for (ChatFormatting $$7 : $$0) {
            switch ($$7) {
                case OBFUSCATED:
                    $$6 = true;
                    break;
                case BOLD:
                    $$2 = true;
                    break;
                case STRIKETHROUGH:
                    $$4 = true;
                    break;
                case UNDERLINE:
                    $$5 = true;
                    break;
                case ITALIC:
                    $$3 = true;
                    break;
                case RESET:
                    return EMPTY;
                default:
                    $$1 = TextColor.fromLegacyFormat($$7);
                    break;
            }
        }
        return new Style($$1, this.shadowColor, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style applyTo(Style $$0) {
        if (this == EMPTY) {
            return $$0;
        }
        if ($$0 == EMPTY) {
            return this;
        }
        return new Style(this.color != null ? this.color : $$0.color, this.shadowColor != null ? this.shadowColor : $$0.shadowColor, this.bold != null ? this.bold : $$0.bold, this.italic != null ? this.italic : $$0.italic, this.underlined != null ? this.underlined : $$0.underlined, this.strikethrough != null ? this.strikethrough : $$0.strikethrough, this.obfuscated != null ? this.obfuscated : $$0.obfuscated, this.clickEvent != null ? this.clickEvent : $$0.clickEvent, this.hoverEvent != null ? this.hoverEvent : $$0.hoverEvent, this.insertion != null ? this.insertion : $$0.insertion, this.font != null ? this.font : $$0.font);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.minecraft.network.chat.Style$1Collector] */
    public String toString() {
        final StringBuilder $$0 = new StringBuilder("{");
        ?? r0 = new Object(this) { // from class: net.minecraft.network.chat.Style.1Collector
            private boolean isNotFirst;

            private void prependSeparator() {
                if (this.isNotFirst) {
                    $$0.append(',');
                }
                this.isNotFirst = true;
            }

            void addFlagString(String $$02, Boolean $$1) {
                if ($$1 != null) {
                    prependSeparator();
                    if (!$$1.booleanValue()) {
                        $$0.append('!');
                    }
                    $$0.append($$02);
                }
            }

            void addValueString(String $$02, Object $$1) {
                if ($$1 != null) {
                    prependSeparator();
                    $$0.append($$02);
                    $$0.append('=');
                    $$0.append($$1);
                }
            }
        };
        r0.addValueString("color", this.color);
        r0.addValueString("shadowColor", this.shadowColor);
        r0.addFlagString("bold", this.bold);
        r0.addFlagString("italic", this.italic);
        r0.addFlagString("underlined", this.underlined);
        r0.addFlagString("strikethrough", this.strikethrough);
        r0.addFlagString("obfuscated", this.obfuscated);
        r0.addValueString("clickEvent", this.clickEvent);
        r0.addValueString("hoverEvent", this.hoverEvent);
        r0.addValueString("insertion", this.insertion);
        r0.addValueString("font", this.font);
        $$0.append("}");
        return $$0.toString();
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof Style)) {
            return false;
        }
        Style $$1 = (Style) $$0;
        return this.bold == $$1.bold && Objects.equals(getColor(), $$1.getColor()) && Objects.equals(getShadowColor(), $$1.getShadowColor()) && this.italic == $$1.italic && this.obfuscated == $$1.obfuscated && this.strikethrough == $$1.strikethrough && this.underlined == $$1.underlined && Objects.equals(this.clickEvent, $$1.clickEvent) && Objects.equals(this.hoverEvent, $$1.hoverEvent) && Objects.equals(this.insertion, $$1.insertion) && Objects.equals(this.font, $$1.font);
    }

    public int hashCode() {
        return Objects.hash(this.color, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion);
    }
}
