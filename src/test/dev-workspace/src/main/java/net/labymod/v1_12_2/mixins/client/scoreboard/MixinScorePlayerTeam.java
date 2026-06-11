package net.labymod.v1_12_2.mixins.client.scoreboard;

import java.util.Collection;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/scoreboard/MixinScorePlayerTeam.class */
@Mixin({bhh.class})
public abstract class MixinScorePlayerTeam implements ScoreboardTeam {

    @Shadow
    @Final
    private String b;

    @Shadow
    private String e;

    @Shadow
    private String f;
    private final ScoreboardTeamUpdateEvent updateEvent = new ScoreboardTeamUpdateEvent(this);
    private Component labyMod$prefix = Component.empty();
    private String rawPrefix = "";
    private Component labyMod$suffix = Component.empty();
    private String rawSuffix = "";

    @Shadow
    public abstract Collection<String> d();

    @Inject(method = {"setPrefix"}, at = {@At("TAIL")})
    private void labyMod$updatePlayerPrefix(String prefix, CallbackInfo callback) {
        this.labyMod$prefix = LegacyComponentSerializer.legacySection().deserialize(prefix);
        this.rawPrefix = prefix;
        bib.z().a(() -> {
            return (ScoreboardTeamUpdateEvent) Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setSuffix"}, at = {@At("TAIL")})
    private void labyMod$updatePlayerSuffix(String suffix, CallbackInfo callback) {
        this.labyMod$suffix = LegacyComponentSerializer.legacySection().deserialize(suffix);
        this.rawSuffix = suffix;
        bib.z().a(() -> {
            return (ScoreboardTeamUpdateEvent) Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setPrefix"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/scoreboard/ScorePlayerTeam;prefix:Ljava/lang/String;", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(String newPrefix, CallbackInfo ci) {
        if (Objects.equals(this.e, newPrefix)) {
            ci.cancel();
        }
    }

    @Inject(method = {"setSuffix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(String newSuffix, CallbackInfo ci) {
        if (Objects.equals(this.f, newSuffix)) {
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public String getTeamName() {
        return this.b;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Collection<String> getEntries() {
        return d();
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    public boolean hasEntry(@NotNull String name) {
        return d().contains(name);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component getPrefix() {
        return this.labyMod$prefix;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component getSuffix() {
        return this.labyMod$suffix;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component formatDisplayName(@NotNull Component component) {
        return LegacyComponentSerializer.legacySection().deserialize(this.rawPrefix + LegacyComponentSerializer.legacySection().serialize(component) + this.rawSuffix);
    }
}
