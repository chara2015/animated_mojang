package net.labymod.api.client.sound;

import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/sound/SoundType.class */
public abstract class SoundType {
    public static final SoundType BUTTON_CLICK = create("ui.button.click");
    public static final SoundType SWITCH_TOGGLE_ON = create("ui.switch.on", BUTTON_CLICK);
    public static final SoundType SWITCH_TOGGLE_OFF = create("ui.switch.off", BUTTON_CLICK);
    public static final SoundType HUD_ALIGN = create("hudwidget.align", Constants.Resources.SOUND_HUDWIDGET_ALIGN);
    public static final SoundType HUD_ATTACH = create("hudwidget.attach", Constants.Resources.SOUND_HUDWIDGET_ATTACH);
    public static final SoundType HUD_TRASH = create("hudwidget.detach", Constants.Resources.SOUND_HUDWIDGET_TRASH);
    public static final SoundType SERVER_MOVE = create("ui.server.move", Constants.Resources.SOUND_UI_SERVER_MOVE);
    protected final String identifier;
    protected final SoundType parent;
    protected ResourceLocation location;

    protected SoundType(String identifier, ResourceLocation location, SoundType parent) {
        this.identifier = identifier;
        this.location = location;
        this.parent = parent;
    }

    public static SoundType create(String identifier, ResourceLocation location) {
        return Laby.references().soundService().createSoundType(identifier, location, null);
    }

    public static SoundType create(String identifier, SoundType parent) {
        return Laby.references().soundService().createSoundType(identifier, null, parent);
    }

    public static SoundType create(String identifier) {
        return Laby.references().soundService().createSoundType(identifier, null, null);
    }

    @NotNull
    public String getIdentifier() {
        return this.identifier;
    }

    @Nullable
    public ResourceLocation getLocation() {
        if (this.location != null) {
            return this.location;
        }
        if (this.parent == null) {
            return null;
        }
        return this.parent.getLocation();
    }

    public SoundType getParent() {
        return this.parent;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoundType)) {
            return false;
        }
        SoundType soundType = (SoundType) o;
        return this.identifier.equals(soundType.identifier) && Objects.equals(this.parent, soundType.parent) && Objects.equals(this.location, soundType.location);
    }

    public int hashCode() {
        int result = this.identifier != null ? this.identifier.hashCode() : 0;
        return (31 * ((31 * result) + (this.parent != null ? this.parent.hashCode() : 0))) + (this.location != null ? this.location.hashCode() : 0);
    }

    public String toString() {
        return "SoundType{identifier='" + this.identifier + "', parent=" + String.valueOf(this.parent) + ", location=" + String.valueOf(this.location) + "}";
    }
}
