package net.labymod.v1_8_9.client.world.phys.hit;

import java.util.Objects;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarColor;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/world/phys/hit/VersionedBossBar.class */
public class VersionedBossBar implements BossBar {
    private static final UUID WITHER = UUID.fromString("8156883e-756e-4912-9063-8a871738ed3e");
    private static final UUID ENDER_DRAGON = UUID.fromString("d34aa2b8-31da-4d26-9655-e33c143f096c");
    private static final UUID OTHER = UUID.fromString("34e57efa-5783-46c7-a9fc-890296aaba1f");
    private uc lastDisplayData;
    private UUID uuid;
    private final BossBarColor bossBarColor;
    private final BossBarOverlay bossBarOverlay;
    private final BossBarProgressHandler progressHandler;
    private Component displayName;

    public VersionedBossBar(uc displayData) {
        setIdentifier(displayData);
        updateDisplayName();
        this.bossBarColor = BossBarColor.PINK;
        this.bossBarOverlay = BossBarOverlay.PROGRESS;
        this.progressHandler = new DynamicBossBarProgressHandler(t -> {
        }, () -> {
            uc currentDisplayData = this.lastDisplayData;
            if (currentDisplayData == null) {
                return 1.0f;
            }
            return currentDisplayData.bn() / currentDisplayData.bu();
        });
    }

    public void updateDisplayName() {
        TextComponent textComponentF_;
        ComponentMapper componentMapper = Laby.references().componentMapper();
        if (this.lastDisplayData == null) {
            textComponentF_ = Component.text("Dummy");
        } else {
            textComponentF_ = this.lastDisplayData.f_();
        }
        this.displayName = componentMapper.fromMinecraftComponent(textComponentF_);
    }

    @Override // net.labymod.api.client.world.BossBar
    @NotNull
    public UUID getIdentifier() {
        return this.uuid;
    }

    @Override // net.labymod.api.client.world.BossBar
    public Component displayName() {
        return this.displayName;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarColor bossBarColor() {
        return this.bossBarColor;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarOverlay bossBarOverlay() {
        return this.bossBarOverlay;
    }

    @Override // net.labymod.api.client.world.BossBar
    public BossBarProgressHandler progressHandler() {
        return this.progressHandler;
    }

    public void setIdentifier(uc displayData) {
        this.lastDisplayData = displayData;
        if (displayData instanceof ug) {
            this.uuid = ENDER_DRAGON;
        } else if (displayData instanceof uk) {
            this.uuid = WITHER;
        } else {
            this.uuid = OTHER;
        }
        updateDisplayName();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VersionedBossBar that = (VersionedBossBar) o;
        return (this.lastDisplayData == null || that.lastDisplayData == null) ? this.lastDisplayData == null && that.lastDisplayData == null : Objects.equals(this.lastDisplayData.f_(), that.lastDisplayData.f_()) && Objects.equals(Float.valueOf(this.lastDisplayData.bn()), Float.valueOf(that.lastDisplayData.bn())) && Objects.equals(Float.valueOf(this.lastDisplayData.bu()), Float.valueOf(that.lastDisplayData.bu()));
    }

    public uc getLastDisplayData() {
        return this.lastDisplayData;
    }

    public int hashCode() {
        return Objects.hash(this.lastDisplayData);
    }
}
