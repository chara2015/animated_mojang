package net.labymod.api.laby3d.vertex;

import net.labymod.laby3d.api.vertex.DefaultVertexDescription;
import net.labymod.laby3d.api.vertex.VertexAttributes;
import net.labymod.laby3d.api.vertex.VertexDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/vertex/VertexDescriptions.class */
public interface VertexDescriptions {

    @Deprecated(forRemoval = true, since = "4.3.38")
    public static final String OVERLAY_UV_NAME = "OverlayUV";
    public static final String POSITION_NAME = "Position";
    public static final VertexDescription POSITION = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).build();
    public static final String COLOR_NAME = "Color";
    public static final VertexDescription POSITION_COLOR = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(COLOR_NAME, VertexAttributes.COLOR).build();
    public static final String UV_NAME = "UV";
    public static final VertexDescription POSITION_UV_COLOR = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).build();

    @Deprecated(forRemoval = true, since = "4.3.38")
    public static final String LIGHT_MAP_UV_NAME = "LightMapUV";
    public static final String FONT_WEIGHT_NAME = "FontWeight";
    public static final VertexDescription MSDF_GLYPH = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(LIGHT_MAP_UV_NAME, VertexAttributes.PACKED_UV).addAttribute(FONT_WEIGHT_NAME, VertexAttributes.GENERIC_FLOAT).build();
    public static final String NORMAL_NAME = "Normal";
    public static final VertexDescription POSITION_COLOR_NORMAL = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(NORMAL_NAME, VertexAttributes.NORMAL).addPadding(1).build();
    public static final String UV0_NAME = "UV0";
    public static final String UV2_NAME = "UV2";
    public static final VertexDescription POSITION_COLOR_UV_LIGHTMAP = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(UV0_NAME, VertexAttributes.UV).addAttribute(UV2_NAME, VertexAttributes.PACKED_UV).build();
    public static final VertexDescription POSITION_UV_COLOR_NORMAL = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(NORMAL_NAME, VertexAttributes.NORMAL).addPadding(1).build();
    public static final String HAS_LIGHTING = "HasLighting";
    public static final VertexDescription SCHEMATIC = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(HAS_LIGHTING, VertexAttributes.GENERIC_FLOAT).addAttribute(NORMAL_NAME, VertexAttributes.NORMAL).addPadding(1).build();
    public static final String UV1_NAME = "UV1";
    public static final VertexDescription MODEL = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV0_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(UV1_NAME, VertexAttributes.PACKED_UV).addAttribute(UV2_NAME, VertexAttributes.PACKED_UV).addAttribute(NORMAL_NAME, VertexAttributes.NORMAL).addPadding(1).build();
    public static final String GLOWING_NAME = "Glowing";
    public static final String BONE_ID_NAME = "BoneId";
    public static final String RAINBOW_DURATION_NAME = "RainbowDuration";
    public static final VertexDescription COSMETIC = DefaultVertexDescription.builder().addAttribute(POSITION_NAME, VertexAttributes.POSITION).addAttribute(UV_NAME, VertexAttributes.UV).addAttribute(COLOR_NAME, VertexAttributes.COLOR).addAttribute(GLOWING_NAME, VertexAttributes.GENERIC_FLOAT).addAttribute(NORMAL_NAME, VertexAttributes.NORMAL).addPadding(1).addAttribute(BONE_ID_NAME, VertexAttributes.GENERIC_INT).addAttribute(RAINBOW_DURATION_NAME, VertexAttributes.GENERIC_FLOAT).build();
}
