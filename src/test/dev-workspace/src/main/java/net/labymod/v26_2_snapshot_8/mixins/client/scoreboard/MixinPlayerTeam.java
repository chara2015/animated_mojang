package net.labymod.v26_2_snapshot_8.mixins.client.scoreboard;

import java.util.Collection;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.TeamColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/scoreboard/MixinPlayerTeam.class */
@Mixin({PlayerTeam.class})
public abstract class MixinPlayerTeam implements ScoreboardTeam {
    private final ScoreboardTeamUpdateEvent updateEvent = new ScoreboardTeamUpdateEvent(this);

    @Shadow
    private Optional<TeamColor> color;

    @Shadow
    private Component playerPrefix;

    @Shadow
    private Component playerSuffix;

    @Shadow
    public abstract Collection<String> getPlayers();

    @Shadow
    public abstract String getName();

    @Shadow
    public abstract Component getPlayerPrefix();

    @Shadow
    public abstract Component getPlayerSuffix();

    @Inject(method = {"setPlayerPrefix"}, at = {@At("TAIL")})
    private void updatePlayerPrefix(Component prefix, CallbackInfo callback) {
        Minecraft.getInstance().execute(() -> {
            Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setPlayerSuffix"}, at = {@At("TAIL")})
    private void updatePlayerSuffix(Component suffix, CallbackInfo callback) {
        Minecraft.getInstance().execute(() -> {
            Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setPlayerPrefix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(Component newPrefix, CallbackInfo ci) {
        if (this.playerPrefix == (newPrefix == null ? PlainTextContents.EMPTY : newPrefix)) {
            ci.cancel();
        }
    }

    @Inject(method = {"setPlayerSuffix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(Component newSuffix, CallbackInfo ci) {
        if (this.playerSuffix == (newSuffix == null ? PlainTextContents.EMPTY : newSuffix)) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public String getTeamName() {
        return getName();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Collection<String> getEntries() {
        return getPlayers();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    public boolean hasEntry(@NotNull String name) {
        return getPlayers().contains(name);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public net.labymod.api.client.component.Component getPrefix() {
        return getPlayerPrefix().getLabyComponent();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public net.labymod.api.client.component.Component getSuffix() {
        return getPlayerSuffix().getLabyComponent();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public net.labymod.api.client.component.Component formatDisplayName(@NotNull net.labymod.api.client.component.Component component) {
        Style style = component.style();
        Style finalStyle = style;
        if (finalStyle.getColor() == null) {
            finalStyle = (Style) this.color.map(color -> {
                switch (AnonymousClass1.$SwitchMap$net$minecraft$world$scores$TeamColor[color.ordinal()]) {
                    case 1:
                        return style.color(NamedTextColor.BLACK);
                    case 2:
                        return style.color(NamedTextColor.DARK_BLUE);
                    case 3:
                        return style.color(NamedTextColor.DARK_GREEN);
                    case 4:
                        return style.color(NamedTextColor.DARK_AQUA);
                    case 5:
                        return style.color(NamedTextColor.DARK_RED);
                    case 6:
                        return style.color(NamedTextColor.DARK_PURPLE);
                    case 7:
                        return style.color(NamedTextColor.GOLD);
                    case 8:
                        return style.color(NamedTextColor.GRAY);
                    case 9:
                        return style.color(NamedTextColor.DARK_GRAY);
                    case 10:
                        return style.color(NamedTextColor.BLUE);
                    case 11:
                        return style.color(NamedTextColor.GREEN);
                    case 12:
                        return style.color(NamedTextColor.AQUA);
                    case 13:
                        return style.color(NamedTextColor.RED);
                    case 14:
                        return style.color(NamedTextColor.LIGHT_PURPLE);
                    case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                        return style.color(NamedTextColor.YELLOW);
                    case 16:
                        return style.color(NamedTextColor.WHITE);
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
            }).orElse(style);
        }
        return net.labymod.api.client.component.Component.empty().style(finalStyle).append(getPrefix()).append(component).append(getSuffix());
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.mixins.client.scoreboard.MixinPlayerTeam$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/scoreboard/MixinPlayerTeam$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$scores$TeamColor = new int[TeamColor.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.BLACK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_BLUE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_GREEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_AQUA.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_RED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_PURPLE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.GOLD.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.GRAY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.DARK_GRAY.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.BLUE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.GREEN.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.AQUA.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.RED.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.LIGHT_PURPLE.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.YELLOW.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$TeamColor[TeamColor.WHITE.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
        }
    }
}
