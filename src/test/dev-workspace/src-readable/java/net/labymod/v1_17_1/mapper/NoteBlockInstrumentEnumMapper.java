package net.labymod.v1_17_1.mapper;

import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.world.block.properties.BlockInstrument;
import net.labymod.api.mapper.AbstractEnumMapper;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mapper/NoteBlockInstrumentEnumMapper.class */
@AutoService(value = EnumMapper.class, versionSpecific = true)
public class NoteBlockInstrumentEnumMapper extends AbstractEnumMapper<clu, BlockInstrument> {
    public NoteBlockInstrumentEnumMapper() {
        super(clu.class, BlockInstrument.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public BlockInstrument from(clu source) throws MatchException {
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
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.mapper.NoteBlockInstrumentEnumMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mapper/NoteBlockInstrumentEnumMapper$1.class */
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
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.ZOMBIE.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.SKELETON.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.CREEPER.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.DRAGON.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.WITHER_SKELETON.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.PIGLIN.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[BlockInstrument.CUSTOM_HEAD.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument = new int[clu.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.a.ordinal()] = 1;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.b.ordinal()] = 2;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.c.ordinal()] = 3;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.d.ordinal()] = 4;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.e.ordinal()] = 5;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.f.ordinal()] = 6;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.g.ordinal()] = 7;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.h.ordinal()] = 8;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.i.ordinal()] = 9;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.j.ordinal()] = 10;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.k.ordinal()] = 11;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.l.ordinal()] = 12;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.m.ordinal()] = 13;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.n.ordinal()] = 14;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.o.ordinal()] = 15;
            } catch (NoSuchFieldError e38) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[clu.p.ordinal()] = 16;
            } catch (NoSuchFieldError e39) {
            }
        }
    }

    @Override // net.labymod.api.mapper.EnumMapper
    public clu to(BlockInstrument destination) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[destination.ordinal()]) {
            case 1:
                return clu.a;
            case 2:
                return clu.b;
            case 3:
                return clu.c;
            case 4:
                return clu.d;
            case 5:
                return clu.e;
            case 6:
                return clu.f;
            case 7:
                return clu.g;
            case 8:
                return clu.h;
            case 9:
                return clu.i;
            case 10:
                return clu.j;
            case 11:
                return clu.k;
            case 12:
                return clu.l;
            case 13:
                return clu.m;
            case 14:
                return clu.n;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return clu.o;
            case 16:
                return clu.p;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
            case 18:
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
            case ImGuiFlags.StyleColors.Button /* 21 */:
            case 22:
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return null;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(destination));
        }
    }
}
