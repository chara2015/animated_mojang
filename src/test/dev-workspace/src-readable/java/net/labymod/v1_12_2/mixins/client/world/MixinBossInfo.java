package net.labymod.v1_12_2.mixins.client.world;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/MixinBossInfo.class */
@Mixin({bjj.class})
public abstract class MixinBossInfo extends tt implements BossBar {
    private final BossBarProgressHandler labyMod$progressHandler;
    private Component labyMod$displayName;
    private BossBarColor labyMod$bossBarColor;
    private BossBarOverlay labyMod$bossBarOverlay;

    public MixinBossInfo(UUID lvt_1_1_, hh lvt_2_1_, a lvt_3_1_, b lvt_4_1_) {
        super(lvt_1_1_, lvt_2_1_, lvt_3_1_, lvt_4_1_);
        this.labyMod$progressHandler = new DynamicBossBarProgressHandler(this::a, this::f);
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labymod$init(ik packet, CallbackInfo ci) {
        init();
    }

    public void init() {
        this.labyMod$displayName = Laby.references().componentMapper().fromMinecraftComponent(e());
        this.labyMod$bossBarColor = labyMod$toLabyModColor(g());
        this.labyMod$bossBarOverlay = labyMod$toLabyModOverlay(h());
    }

    public void a(hh lvt_1_1_) {
        super.a(lvt_1_1_);
        init();
    }

    public void a(a lvt_1_1_) {
        super.a(lvt_1_1_);
        init();
    }

    public void a(b lvt_1_1_) {
        super.a(lvt_1_1_);
        init();
    }

    @Override // net.labymod.api.client.world.BossBar
    @NotNull
    public UUID getIdentifier() {
        return d();
    }

    @Override // net.labymod.api.client.world.BossBar
    public Component displayName() {
        return this.labyMod$displayName;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarColor bossBarColor() {
        return this.labyMod$bossBarColor;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarOverlay bossBarOverlay() {
        return this.labyMod$bossBarOverlay;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarProgressHandler progressHandler() {
        return this.labyMod$progressHandler;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private BossBarColor labyMod$toLabyModColor(a color) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$BossInfo$Color[color.ordinal()]) {
            case 1:
                return BossBarColor.PINK;
            case 2:
                return BossBarColor.BLUE;
            case 3:
                return BossBarColor.RED;
            case 4:
                return BossBarColor.GREEN;
            case 5:
                return BossBarColor.YELLOW;
            case 6:
                return BossBarColor.PURPLE;
            case 7:
                return BossBarColor.WHITE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.mixins.client.world.MixinBossInfo$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/MixinBossInfo$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$BossInfo$Color;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$BossInfo$Overlay = new int[b.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Overlay[b.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Overlay[b.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Overlay[b.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Overlay[b.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Overlay[b.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            $SwitchMap$net$minecraft$world$BossInfo$Color = new int[a.values().length];
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.d.ordinal()] = 4;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.f.ordinal()] = 6;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$world$BossInfo$Color[a.g.ordinal()] = 7;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private BossBarOverlay labyMod$toLabyModOverlay(b overlay) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$BossInfo$Overlay[overlay.ordinal()]) {
            case 1:
                return BossBarOverlay.PROGRESS;
            case 2:
                return BossBarOverlay.NOTCHED_6;
            case 3:
                return BossBarOverlay.NOTCHED_10;
            case 4:
                return BossBarOverlay.NOTCHED_12;
            case 5:
                return BossBarOverlay.NOTCHED_20;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
