package net.labymod.core.main.user;

import java.util.Objects;
import java.util.UUID;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.user.GameUserProfile;
import net.labymod.api.user.WhitelistState;
import net.labymod.api.user.group.Group;
import net.labymod.core.main.user.group.GroupHolder;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/DefaultGameUserProfile.class */
public class DefaultGameUserProfile implements GameUserProfile {
    private final UUID uuid;
    private final GroupHolder groupHolder = new GroupHolder();
    private WhitelistState whitelistState = WhitelistState.UNKNOWN;

    public DefaultGameUserProfile(UUID uuid) {
        this.uuid = uuid;
    }

    @Override // net.labymod.api.user.GameUserProfile
    @NotNull
    public UUID getUuid() {
        return this.uuid;
    }

    @Override // net.labymod.api.user.GameUserProfile
    @NotNull
    public WhitelistState whitelistState() {
        return this.whitelistState;
    }

    public void setWhitelistState(@NotNull WhitelistState whitelistState) {
        this.whitelistState = whitelistState;
    }

    @Override // net.labymod.api.user.GameUserProfile
    @NotNull
    public Group visibleGroup() {
        return this.groupHolder.visibleGroup();
    }

    public void setVisibleGroup(int id) {
        this.groupHolder.setId(id);
    }

    public boolean isLegacyGroup() {
        return this.groupHolder.isLegacy();
    }

    @Override // net.labymod.api.user.GameUserProfile
    @NotNull
    public TextColor displayColor() {
        return visibleGroup().getTextColor();
    }

    public GroupHolder groupHolder() {
        return this.groupHolder;
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultGameUserProfile that = (DefaultGameUserProfile) o;
        return Objects.equals(this.uuid, that.uuid);
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }

    public String toString() {
        return this.uuid.toString();
    }
}
