package net.labymod.api.client.blockentity;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/blockentity/SignBlockEntity.class */
public interface SignBlockEntity extends BlockEntity {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/blockentity/SignBlockEntity$SignType.class */
    public enum SignType {
        WALL_SIGN,
        STANDING_SIGN,
        WALL_HANGING_SIGN,
        CEILING_HANGING_SIGN
    }

    float getRotationYaw();

    SignType getType();

    boolean hasSide(@NotNull SignSide signSide);

    @NotNull
    Component[] getLines(@NotNull SignSide signSide) throws IllegalArgumentException;

    @NotNull
    default Component[] getLines() {
        return getLines(SignSide.FRONT);
    }

    @NotNull
    default String[] getStringLines() {
        return getStringLines(SignSide.FRONT);
    }

    @NotNull
    default String[] getStringLines(@NotNull SignSide side) throws IllegalArgumentException {
        Component[] lines = getLines(side);
        String[] stringLines = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            stringLines[i] = PlainTextComponentSerializer.plainText().serialize(lines[i]);
        }
        return stringLines;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/blockentity/SignBlockEntity$SignSide.class */
    public enum SignSide {
        FRONT,
        BACK { // from class: net.labymod.api.client.blockentity.SignBlockEntity.SignSide.1
            @Override // net.labymod.api.client.blockentity.SignBlockEntity.SignSide
            public float modifyYaw(float yaw) {
                return yaw - 180.0f;
            }
        };

        public float modifyYaw(float yaw) {
            return yaw;
        }
    }
}
