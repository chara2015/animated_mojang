package net.labymod.v1_21_11.mixins.client.scoreboard;

import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/scoreboard/MixinPlayerTeam.class */
@Mixin({PlayerTeam.class})
public abstract class MixinPlayerTeam implements ScoreboardTeam {
    private final ScoreboardTeamUpdateEvent updateEvent = new ScoreboardTeamUpdateEvent(this);

    @Shadow
    private ChatFormatting color;

    @Shadow
    private Component getPlayerSuffix;

    @Shadow
    private Component getPlayers;

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
        if (this.getPlayerSuffix == (newPrefix == null ? PlainTextContents.EMPTY : newPrefix)) {
            ci.cancel();
        }
    }

    @Inject(method = {"setPlayerSuffix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(Component newSuffix, CallbackInfo ci) {
        if (this.getPlayers == (newSuffix == null ? PlainTextContents.EMPTY : newSuffix)) {
            ci.cancel();
        }
    }

    @NotNull
    public String getTeamName() {
        return getName();
    }

    @NotNull
    public Collection<String> getEntries() {
        return getPlayers();
    }

    public boolean hasEntry(@NotNull String name) {
        return getPlayers().contains(name);
    }

    @NotNull
    public net.labymod.api.client.component.Component getPrefix() {
        return getPlayerPrefix().getLabyComponent();
    }

    @NotNull
    public net.labymod.api.client.component.Component getSuffix() {
        return getPlayerSuffix().getLabyComponent();
    }

    @NotNull
    public net.labymod.api.client.component.Component formatDisplayName(@NotNull net.labymod.api.client.component.Component component) {
        Style style = component.style();
        if (this.color != ChatFormatting.RESET && style.getColor() == null) {
            switch (AnonymousClass1.$SwitchMap$net$minecraft$ChatFormatting[this.color.ordinal()]) {
                case 1:
                    style = style.color(NamedTextColor.BLACK);
                    break;
                case 2:
                    style = style.color(NamedTextColor.DARK_BLUE);
                    break;
                case 3:
                    style = style.color(NamedTextColor.DARK_GREEN);
                    break;
                case 4:
                    style = style.color(NamedTextColor.DARK_AQUA);
                    break;
                case 5:
                    style = style.color(NamedTextColor.DARK_RED);
                    break;
                case 6:
                    style = style.color(NamedTextColor.DARK_PURPLE);
                    break;
                case 7:
                    style = style.color(NamedTextColor.GOLD);
                    break;
                case 8:
                    style = style.color(NamedTextColor.GRAY);
                    break;
                case 9:
                    style = style.color(NamedTextColor.DARK_GRAY);
                    break;
                case 10:
                    style = style.color(NamedTextColor.BLUE);
                    break;
                case 11:
                    style = style.color(NamedTextColor.GREEN);
                    break;
                case 12:
                    style = style.color(NamedTextColor.AQUA);
                    break;
                case 13:
                    style = style.color(NamedTextColor.RED);
                    break;
                case 14:
                    style = style.color(NamedTextColor.LIGHT_PURPLE);
                    break;
                case 15:
                    style = style.color(NamedTextColor.YELLOW);
                    break;
                case 16:
                    style = style.color(NamedTextColor.WHITE);
                    break;
                case 17:
                    style = style.decorate(TextDecoration.OBFUSCATED);
                    break;
                case 18:
                    style = style.decorate(TextDecoration.BOLD);
                    break;
                case 19:
                    style = style.decorate(TextDecoration.STRIKETHROUGH);
                    break;
                case 20:
                    style = style.decorate(TextDecoration.UNDERLINED);
                    break;
                case 21:
                    style = style.decorate(TextDecoration.ITALIC);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(this.color));
            }
        }
        return net.labymod.api.client.component.Component.empty().style(style).append(getPrefix()).append(component).append(getSuffix());
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.scoreboard.MixinPlayerTeam$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/scoreboard/MixinPlayerTeam$1.class */
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
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.OBFUSCATED.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.BOLD.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.STRIKETHROUGH.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.UNDERLINE.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[ChatFormatting.ITALIC.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
        }
    }
}

