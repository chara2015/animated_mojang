package net.minecraft.world.scores;

import java.util.function.IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/DisplaySlot.class */
public enum DisplaySlot implements StringRepresentable {
    LIST(0, "list"),
    SIDEBAR(1, "sidebar"),
    BELOW_NAME(2, "below_name"),
    TEAM_BLACK(3, "sidebar.team.black"),
    TEAM_DARK_BLUE(4, "sidebar.team.dark_blue"),
    TEAM_DARK_GREEN(5, "sidebar.team.dark_green"),
    TEAM_DARK_AQUA(6, "sidebar.team.dark_aqua"),
    TEAM_DARK_RED(7, "sidebar.team.dark_red"),
    TEAM_DARK_PURPLE(8, "sidebar.team.dark_purple"),
    TEAM_GOLD(9, "sidebar.team.gold"),
    TEAM_GRAY(10, "sidebar.team.gray"),
    TEAM_DARK_GRAY(11, "sidebar.team.dark_gray"),
    TEAM_BLUE(12, "sidebar.team.blue"),
    TEAM_GREEN(13, "sidebar.team.green"),
    TEAM_AQUA(14, "sidebar.team.aqua"),
    TEAM_RED(15, "sidebar.team.red"),
    TEAM_LIGHT_PURPLE(16, "sidebar.team.light_purple"),
    TEAM_YELLOW(17, "sidebar.team.yellow"),
    TEAM_WHITE(18, "sidebar.team.white");

    public static final StringRepresentable.EnumCodec<DisplaySlot> CODEC = StringRepresentable.fromEnum(DisplaySlot::values);
    public static final IntFunction<DisplaySlot> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.id();
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    private final int id;
    private final String name;

    DisplaySlot(int $$0, String $$1) {
        this.id = $$0;
        this.name = $$1;
    }

    public int id() {
        return this.id;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    /* JADX INFO: renamed from: net.minecraft.world.scores.DisplaySlot$1, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/DisplaySlot$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$ChatFormatting = new int[ChatFormatting.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.BLACK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_BLUE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_GREEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_AQUA.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_RED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_PURPLE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.GOLD.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.GRAY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.DARK_GRAY.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.BLUE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.GREEN.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.AQUA.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.RED.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.LIGHT_PURPLE.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.YELLOW.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.WHITE.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.BOLD.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.ITALIC.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.UNDERLINE.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.RESET.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.OBFUSCATED.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.STRIKETHROUGH.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static DisplaySlot teamColorToSlot(ChatFormatting $$0) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$ChatFormatting[$$0.ordinal()]) {
            case 1:
                return TEAM_BLACK;
            case 2:
                return TEAM_DARK_BLUE;
            case 3:
                return TEAM_DARK_GREEN;
            case 4:
                return TEAM_DARK_AQUA;
            case 5:
                return TEAM_DARK_RED;
            case 6:
                return TEAM_DARK_PURPLE;
            case 7:
                return TEAM_GOLD;
            case 8:
                return TEAM_GRAY;
            case 9:
                return TEAM_DARK_GRAY;
            case 10:
                return TEAM_BLUE;
            case 11:
                return TEAM_GREEN;
            case 12:
                return TEAM_AQUA;
            case 13:
                return TEAM_RED;
            case 14:
                return TEAM_LIGHT_PURPLE;
            case 15:
                return TEAM_YELLOW;
            case 16:
                return TEAM_WHITE;
            case EntityEvent.FIREWORKS_EXPLODE /* 17 */:
            case 18:
            case EntityEvent.SQUID_ANIM_SYNCH /* 19 */:
            case 20:
            case 21:
            case EntityEvent.REDUCED_DEBUG_INFO /* 22 */:
                return null;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
