package net.labymod.v1_16_5.mixins.client.scoreboard;

import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/scoreboard/MixinPlayerTeam.class */
@Mixin({ddl.class})
public abstract class MixinPlayerTeam implements ScoreboardTeam {
    private final ScoreboardTeamUpdateEvent updateEvent = new ScoreboardTeamUpdateEvent(this);

    @Shadow
    private k k;

    @Shadow
    private nr e;

    @Shadow
    private nr f;

    @Shadow
    public abstract Collection<String> g();

    @Shadow
    public abstract String b();

    @Shadow
    public abstract nr e();

    @Shadow
    public abstract nr f();

    @Inject(method = {"setPlayerPrefix"}, at = {@At("TAIL")})
    private void updatePlayerPrefix(nr prefix, CallbackInfo callback) {
        djz.C().execute(() -> {
            Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setPlayerSuffix"}, at = {@At("TAIL")})
    private void updatePlayerSuffix(nr suffix, CallbackInfo callback) {
        djz.C().execute(() -> {
            Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setPlayerPrefix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(nr newPrefix, CallbackInfo ci) {
        if (this.e == (newPrefix == null ? oe.d : newPrefix)) {
            ci.cancel();
        }
    }

    @Inject(method = {"setPlayerSuffix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(nr newSuffix, CallbackInfo ci) {
        if (this.f == (newSuffix == null ? oe.d : newSuffix)) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public String getTeamName() {
        return b();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Collection<String> getEntries() {
        return g();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    public boolean hasEntry(@NotNull String name) {
        return g().contains(name);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component getPrefix() {
        return e();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component getSuffix() {
        return f();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component formatDisplayName(@NotNull Component component) {
        Style style = component.style();
        if (this.k != k.v && style.getColor() == null) {
            switch (AnonymousClass1.$SwitchMap$net$minecraft$ChatFormatting[this.k.ordinal()]) {
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
                case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
                    style = style.color(NamedTextColor.YELLOW);
                    break;
                case 16:
                    style = style.color(NamedTextColor.WHITE);
                    break;
                case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
                    style = style.decorate(TextDecoration.OBFUSCATED);
                    break;
                case 18:
                    style = style.decorate(TextDecoration.BOLD);
                    break;
                case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
                    style = style.decorate(TextDecoration.STRIKETHROUGH);
                    break;
                case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                    style = style.decorate(TextDecoration.UNDERLINED);
                    break;
                case ImGuiFlags.StyleColors.Button /* 21 */:
                    style = style.decorate(TextDecoration.ITALIC);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(this.k));
            }
        }
        return Component.empty().style(style).append(getPrefix()).append(component).append(getSuffix());
    }

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.scoreboard.MixinPlayerTeam$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/scoreboard/MixinPlayerTeam$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$ChatFormatting = new int[k.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.g.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.h.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.i.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.j.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.k.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.l.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.m.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.n.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.o.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.p.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.q.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.r.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.s.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.t.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$ChatFormatting[k.u.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
        }
    }
}
