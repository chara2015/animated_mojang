package net.minecraft.client.gui.font.providers;

import com.mojang.blaze3d.font.SpaceProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.font.providers.BitmapProvider;
import net.minecraft.client.gui.font.providers.UnihexProvider;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/GlyphProviderType.class */
public enum GlyphProviderType implements StringRepresentable {
    BITMAP("bitmap", BitmapProvider.Definition.CODEC),
    TTF("ttf", TrueTypeGlyphProviderDefinition.CODEC),
    SPACE("space", SpaceProvider.Definition.CODEC),
    UNIHEX("unihex", UnihexProvider.Definition.CODEC),
    REFERENCE("reference", ProviderReferenceDefinition.CODEC);

    public static final Codec<GlyphProviderType> CODEC = StringRepresentable.fromEnum(GlyphProviderType::values);
    private final String name;
    private final MapCodec<? extends GlyphProviderDefinition> codec;

    GlyphProviderType(String $$0, MapCodec mapCodec) {
        this.name = $$0;
        this.codec = mapCodec;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    public MapCodec<? extends GlyphProviderDefinition> mapCodec() {
        return this.codec;
    }
}
