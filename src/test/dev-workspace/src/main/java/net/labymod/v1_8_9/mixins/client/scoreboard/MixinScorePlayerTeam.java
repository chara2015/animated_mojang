package net.labymod.v1_8_9.mixins.client.scoreboard;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/scoreboard/MixinScorePlayerTeam.class */
@Mixin({aul.class})
public abstract class MixinScorePlayerTeam implements ScoreboardTeam {

    @Shadow
    @Final
    private String b;

    @Shadow
    private String e;

    @Shadow
    private String f;
    private final ScoreboardTeamUpdateEvent updateEvent = new ScoreboardTeamUpdateEvent(this);
    private Component prefix = Component.empty();
    private String rawPrefix = "";
    private Component suffix = Component.empty();
    private String rawSuffix = "";

    @Shadow
    public abstract Collection<String> d();

    @Inject(method = {"setNamePrefix"}, at = {@At("TAIL")})
    private void labyMod$updatePlayerPrefix(String prefix, CallbackInfo callback) {
        this.prefix = LegacyComponentSerializer.legacySection().deserialize(prefix);
        this.rawPrefix = prefix;
        ave.A().a(() -> {
            return (ScoreboardTeamUpdateEvent) Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setNameSuffix"}, at = {@At("TAIL")})
    private void labyMod$updatePlayerSuffix(String suffix, CallbackInfo callback) {
        this.suffix = LegacyComponentSerializer.legacySection().deserialize(suffix);
        this.rawSuffix = suffix;
        ave.A().a(() -> {
            return (ScoreboardTeamUpdateEvent) Laby.fireEvent(this.updateEvent);
        });
    }

    @Inject(method = {"setNamePrefix"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/scoreboard/ScorePlayerTeam;namePrefixSPT:Ljava/lang/String;", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$cancelScoreboardPrefixUpdate(String newPrefix, CallbackInfo ci) {
        if (this.e.equals(newPrefix)) {
            ci.cancel();
        }
    }

    @Inject(method = {"setNameSuffix"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$cancelScoreboardSuffixUpdate(String newPrefix, CallbackInfo ci) {
        if (Objects.equals(this.f, newPrefix)) {
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
        return this.prefix;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component getSuffix() {
        return this.suffix;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
    @NotNull
    public Component formatDisplayName(@NotNull Component component) {
        return LegacyComponentSerializer.legacySection().deserialize(this.rawPrefix + LegacyComponentSerializer.legacySection().serialize(component) + this.rawSuffix);
    }
}
