package net.labymod.core.main.user.shop.item.model;

import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/model/AttachmentPosition.class */
public enum AttachmentPosition {
    HEAD_TOP,
    FACE(Side.FRONT),
    CHEST(Side.FRONT),
    BACK(Side.BACK),
    HIPS(new FloatVector2(0.0f, -6.0f)),
    SHOULDER(new FloatVector2(0.0f, 7.0f)),
    ARM,
    LEGS_BACK(Side.BACK),
    FEET(new FloatVector2(0.0f, -2.0f));

    private final Side side;
    private final FloatVector2 shift;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/model/AttachmentPosition$Side.class */
    public enum Side {
        FRONT,
        BACK,
        NONE
    }

    AttachmentPosition() {
        this(Side.NONE, new FloatVector2());
    }

    AttachmentPosition(Side side) {
        this(side, new FloatVector2());
    }

    AttachmentPosition(FloatVector2 shift) {
        this(Side.NONE, shift);
    }

    AttachmentPosition(Side side, FloatVector2 shift) {
        this.side = side;
        this.shift = shift;
    }

    public Side side() {
        return this.side;
    }

    public FloatVector2 shift() {
        return this.shift;
    }
}
