package net.labymod.v26_1.mapper;

import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.world.block.properties.BlockInstrument;
import net.labymod.api.mapper.AbstractEnumMapper;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.service.annotation.AutoService;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mapper/NoteBlockInstrumentEnumMapper.class */
@AutoService(value = EnumMapper.class, versionSpecific = true)
public class NoteBlockInstrumentEnumMapper extends AbstractEnumMapper<NoteBlockInstrument, BlockInstrument> {
    public NoteBlockInstrumentEnumMapper() {
        super(NoteBlockInstrument.class, BlockInstrument.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public BlockInstrument from(NoteBlockInstrument source) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[source.ordinal()]) {
            case 1:
                return BlockInstrument.HARP;
            case 2:
                return BlockInstrument.BASEDRUM;
            case 3:
                return BlockInstrument.SNARE;
            case 4:
                return BlockInstrument.HAT;
            case 5:
                return BlockInstrument.BASS;
            case 6:
                return BlockInstrument.FLUTE;
            case 7:
                return BlockInstrument.BELL;
            case 8:
                return BlockInstrument.GUITAR;
            case 9:
                return BlockInstrument.CHIME;
            case 10:
                return BlockInstrument.XYLOPHONE;
            case 11:
                return BlockInstrument.IRON_XYLOPHONE;
            case 12:
                return BlockInstrument.COW_BELL;
            case 13:
                return BlockInstrument.DIDGERIDOO;
            case 14:
                return BlockInstrument.BIT;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return BlockInstrument.BANJO;
            case 16:
                return BlockInstrument.PLING;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return BlockInstrument.TRUMPET;
            case 18:
                return BlockInstrument.TRUMPET_EXPOSED;
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                return BlockInstrument.TRUMPET_OXIDIZED;
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return BlockInstrument.TRUMPET_WEATHERED;
            case ImGuiFlags.StyleColors.Button /* 21 */:
                return BlockInstrument.ZOMBIE;
            case 22:
                return BlockInstrument.SKELETON;
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return BlockInstrument.CREEPER;
            case ImGuiFlags.StyleColors.Header /* 24 */:
                return BlockInstrument.DRAGON;
            case ImGuiFlags.StyleColors.HeaderHovered /* 25 */:
                return BlockInstrument.WITHER_SKELETON;
            case 26:
                return BlockInstrument.PIGLIN;
            case ImGuiFlags.StyleColors.Separator /* 27 */:
                return BlockInstrument.CUSTOM_HEAD;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_1.mapper.NoteBlockInstrumentEnumMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mapper/NoteBlockInstrumentEnumMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument = new int[BlockInstrument.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.HARP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.BASEDRUM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.SNARE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.HAT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.BASS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.FLUTE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.BELL.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.GUITAR.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.CHIME.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.XYLOPHONE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.IRON_XYLOPHONE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.COW_BELL.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.DIDGERIDOO.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.BIT.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.BANJO.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.PLING.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.TRUMPET.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.TRUMPET_EXPOSED.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.TRUMPET_OXIDIZED.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.TRUMPET_WEATHERED.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.ZOMBIE.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.SKELETON.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.CREEPER.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.DRAGON.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.WITHER_SKELETON.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.PIGLIN.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.CUSTOM_HEAD.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument = new int[NoteBlockInstrument.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.HARP.ordinal()] = 1;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.BASEDRUM.ordinal()] = 2;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.SNARE.ordinal()] = 3;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.HAT.ordinal()] = 4;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.BASS.ordinal()] = 5;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.FLUTE.ordinal()] = 6;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.BELL.ordinal()] = 7;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.GUITAR.ordinal()] = 8;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.CHIME.ordinal()] = 9;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.XYLOPHONE.ordinal()] = 10;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.IRON_XYLOPHONE.ordinal()] = 11;
            } catch (NoSuchFieldError e38) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.COW_BELL.ordinal()] = 12;
            } catch (NoSuchFieldError e39) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.DIDGERIDOO.ordinal()] = 13;
            } catch (NoSuchFieldError e40) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.BIT.ordinal()] = 14;
            } catch (NoSuchFieldError e41) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.BANJO.ordinal()] = 15;
            } catch (NoSuchFieldError e42) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.PLING.ordinal()] = 16;
            } catch (NoSuchFieldError e43) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.TRUMPET.ordinal()] = 17;
            } catch (NoSuchFieldError e44) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.TRUMPET_EXPOSED.ordinal()] = 18;
            } catch (NoSuchFieldError e45) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.TRUMPET_OXIDIZED.ordinal()] = 19;
            } catch (NoSuchFieldError e46) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.TRUMPET_WEATHERED.ordinal()] = 20;
            } catch (NoSuchFieldError e47) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.ZOMBIE.ordinal()] = 21;
            } catch (NoSuchFieldError e48) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.SKELETON.ordinal()] = 22;
            } catch (NoSuchFieldError e49) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.CREEPER.ordinal()] = 23;
            } catch (NoSuchFieldError e50) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.DRAGON.ordinal()] = 24;
            } catch (NoSuchFieldError e51) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.WITHER_SKELETON.ordinal()] = 25;
            } catch (NoSuchFieldError e52) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.PIGLIN.ordinal()] = 26;
            } catch (NoSuchFieldError e53) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[NoteBlockInstrument.CUSTOM_HEAD.ordinal()] = 27;
            } catch (NoSuchFieldError e54) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public NoteBlockInstrument to(BlockInstrument destination) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[destination.ordinal()]) {
            case 1:
                return NoteBlockInstrument.HARP;
            case 2:
                return NoteBlockInstrument.BASEDRUM;
            case 3:
                return NoteBlockInstrument.SNARE;
            case 4:
                return NoteBlockInstrument.HAT;
            case 5:
                return NoteBlockInstrument.BASS;
            case 6:
                return NoteBlockInstrument.FLUTE;
            case 7:
                return NoteBlockInstrument.BELL;
            case 8:
                return NoteBlockInstrument.GUITAR;
            case 9:
                return NoteBlockInstrument.CHIME;
            case 10:
                return NoteBlockInstrument.XYLOPHONE;
            case 11:
                return NoteBlockInstrument.IRON_XYLOPHONE;
            case 12:
                return NoteBlockInstrument.COW_BELL;
            case 13:
                return NoteBlockInstrument.DIDGERIDOO;
            case 14:
                return NoteBlockInstrument.BIT;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return NoteBlockInstrument.BANJO;
            case 16:
                return NoteBlockInstrument.PLING;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return NoteBlockInstrument.TRUMPET;
            case 18:
                return NoteBlockInstrument.TRUMPET_EXPOSED;
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                return NoteBlockInstrument.TRUMPET_OXIDIZED;
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return NoteBlockInstrument.TRUMPET_WEATHERED;
            case ImGuiFlags.StyleColors.Button /* 21 */:
                return NoteBlockInstrument.ZOMBIE;
            case 22:
                return NoteBlockInstrument.SKELETON;
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return NoteBlockInstrument.CREEPER;
            case ImGuiFlags.StyleColors.Header /* 24 */:
                return NoteBlockInstrument.DRAGON;
            case ImGuiFlags.StyleColors.HeaderHovered /* 25 */:
                return NoteBlockInstrument.WITHER_SKELETON;
            case 26:
                return NoteBlockInstrument.PIGLIN;
            case ImGuiFlags.StyleColors.Separator /* 27 */:
                return NoteBlockInstrument.CUSTOM_HEAD;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
