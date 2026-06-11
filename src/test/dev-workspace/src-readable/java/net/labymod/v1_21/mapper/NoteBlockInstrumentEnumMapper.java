package net.labymod.v1_21.mapper;

import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.world.block.properties.BlockInstrument;
import net.labymod.api.mapper.AbstractEnumMapper;
import net.labymod.api.mapper.EnumMapper;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mapper/NoteBlockInstrumentEnumMapper.class */
@AutoService(value = EnumMapper.class, versionSpecific = true)
public class NoteBlockInstrumentEnumMapper extends AbstractEnumMapper<dud, BlockInstrument> {
    public NoteBlockInstrumentEnumMapper() {
        super(dud.class, BlockInstrument.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.mapper.EnumMapper
    public BlockInstrument from(dud source) throws MatchException {
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
                return BlockInstrument.ZOMBIE;
            case 18:
                return BlockInstrument.SKELETON;
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                return BlockInstrument.CREEPER;
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return BlockInstrument.DRAGON;
            case ImGuiFlags.StyleColors.Button /* 21 */:
                return BlockInstrument.WITHER_SKELETON;
            case 22:
                return BlockInstrument.PIGLIN;
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return BlockInstrument.CUSTOM_HEAD;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21.mapper.NoteBlockInstrumentEnumMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mapper/NoteBlockInstrumentEnumMapper$1.class */
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
            $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument = new int[dud.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.a.ordinal()] = 1;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.b.ordinal()] = 2;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.c.ordinal()] = 3;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.d.ordinal()] = 4;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.e.ordinal()] = 5;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.f.ordinal()] = 6;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.g.ordinal()] = 7;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.h.ordinal()] = 8;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.i.ordinal()] = 9;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.j.ordinal()] = 10;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.k.ordinal()] = 11;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.l.ordinal()] = 12;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.m.ordinal()] = 13;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.n.ordinal()] = 14;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.o.ordinal()] = 15;
            } catch (NoSuchFieldError e38) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.p.ordinal()] = 16;
            } catch (NoSuchFieldError e39) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.q.ordinal()] = 17;
            } catch (NoSuchFieldError e40) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.r.ordinal()] = 18;
            } catch (NoSuchFieldError e41) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.s.ordinal()] = 19;
            } catch (NoSuchFieldError e42) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.t.ordinal()] = 20;
            } catch (NoSuchFieldError e43) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.u.ordinal()] = 21;
            } catch (NoSuchFieldError e44) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.v.ordinal()] = 22;
            } catch (NoSuchFieldError e45) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$state$properties$NoteBlockInstrument[dud.w.ordinal()] = 23;
            } catch (NoSuchFieldError e46) {
            }
        }
    }

    @Override // net.labymod.api.mapper.EnumMapper
    public dud to(BlockInstrument destination) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$world$block$properties$BlockInstrument[destination.ordinal()]) {
            case 1:
                return dud.a;
            case 2:
                return dud.b;
            case 3:
                return dud.c;
            case 4:
                return dud.d;
            case 5:
                return dud.e;
            case 6:
                return dud.f;
            case 7:
                return dud.g;
            case 8:
                return dud.h;
            case 9:
                return dud.i;
            case 10:
                return dud.j;
            case 11:
                return dud.k;
            case 12:
                return dud.l;
            case 13:
                return dud.m;
            case 14:
                return dud.n;
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                return dud.o;
            case 16:
                return dud.p;
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                return dud.q;
            case 18:
                return dud.r;
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                return dud.s;
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return dud.t;
            case ImGuiFlags.StyleColors.Button /* 21 */:
                return dud.u;
            case 22:
                return dud.v;
            case ImGuiFlags.StyleColors.ButtonActive /* 23 */:
                return dud.w;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(destination));
        }
    }
}
