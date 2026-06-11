package net.labymod.core.client.render.model;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.model.AttachmentPosition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/CosmeticModelFocus.class */
public class CosmeticModelFocus {
    private static final float FOCUS_TRANSITION_DURATION = 700.0f;
    private static final FloatVector3 DEFAULT_TRANSLATION = new FloatVector3();
    private static final FloatVector3 DEFAULT_SCALE = new FloatVector3(1.0f);
    private final FloatVector3 translation = new FloatVector3();
    private final FloatVector3 rotation = new FloatVector3();
    private final FloatVector3 scale = new FloatVector3(1.0f);
    private long focusTransitionStart;

    public void applyFocus(PlayerModelWidget modelWidget) {
        if (TimeUtil.getMillis() - this.focusTransitionStart <= FOCUS_TRANSITION_DURATION) {
            modelWidget.setScrolling(false);
            long focusTransitionEnd = (long) (this.focusTransitionStart + FOCUS_TRANSITION_DURATION);
            modelWidget.translation().set(CubicBezier.EASE_IN_OUT.interpolateVector(modelWidget.translation(), this.translation, this.focusTransitionStart, focusTransitionEnd, TimeUtil.getMillis()));
            FloatVector3 modelRotation = modelWidget.rotation();
            this.rotation.set(MathHelper.convertRadians(modelRotation.getX(), this.rotation.getX()), MathHelper.convertRadians(modelRotation.getY(), this.rotation.getY()), MathHelper.convertRadians(modelRotation.getZ(), this.rotation.getZ()));
            modelWidget.rotation().set(CubicBezier.EASE_IN_OUT.interpolateVector(modelRotation, this.rotation, this.focusTransitionStart, focusTransitionEnd, TimeUtil.getMillis()));
            modelWidget.scale().set(CubicBezier.EASE_IN_OUT.interpolateVector(modelWidget.scale(), this.scale, this.focusTransitionStart, focusTransitionEnd, TimeUtil.getMillis()));
            return;
        }
        modelWidget.setScrolling(true);
    }

    public void updateModelFocus(PlayerModelWidget modelWidget, CosmeticDetails itemDetails, Cosmetic cosmetic) {
        updateModelFocus(modelWidget, itemDetails, cosmetic, null);
    }

    public void updateModelFocus(PlayerModelWidget modelWidget, CosmeticDetails itemDetails, Cosmetic cosmetic, GameUserItem userItem) {
        ModelPart[] focusedParts;
        if (!cosmetic.isEnabled() || itemDetails == null) {
            reset(modelWidget);
            return;
        }
        AttachmentPosition position = itemDetails.getPosition();
        if (position != null && position.side() == AttachmentPosition.Side.FRONT) {
            this.rotation.set(0.0f, 0.0f, 0.0f);
        } else if (position != null && position.side() == AttachmentPosition.Side.BACK) {
            this.rotation.set(0.0f, 3.1415927f, 0.0f);
        } else {
            this.rotation.set(modelWidget.rotation());
        }
        PlayerModel playerModel = new DummyPlayerModel(modelWidget.model());
        boolean attachedToArm = position == AttachmentPosition.ARM || position == AttachmentPosition.SHOULDER;
        if (position == AttachmentPosition.HEAD_TOP || position == AttachmentPosition.FACE) {
            focusedParts = new ModelPart[]{playerModel.getHead()};
        } else if (position == AttachmentPosition.CHEST || position == AttachmentPosition.HIPS) {
            focusedParts = new ModelPart[]{playerModel.getBody()};
        } else if (position == AttachmentPosition.LEGS_BACK || position == AttachmentPosition.FEET) {
            focusedParts = new ModelPart[]{playerModel.getLeftLeg(), playerModel.getRightLeg()};
        } else if (attachedToArm) {
            ModelPart leftPart = playerModel.getLeftArm();
            ModelPart rightPart = playerModel.getRightArm();
            if (itemDetails.isMirror()) {
                focusedParts = new ModelPart[]{leftPart, rightPart};
            } else {
                boolean rightSide = false;
                if (userItem != null) {
                    rightSide = userItem.itemMetadata().isRightSide();
                } else {
                    for (int i = 0; i < cosmetic.getData().length; i++) {
                        String name = cosmetic.getOptions()[i];
                        if (name.equalsIgnoreCase(ItemMetadata.SHOULDER_SIDE_KEY) || name.equalsIgnoreCase(ItemMetadata.SIDE_KEY)) {
                            String value = cosmetic.getData()[i];
                            rightSide = value.equals("1") || value.equalsIgnoreCase("true");
                        }
                    }
                }
                ModelPart[] modelPartArr = new ModelPart[1];
                modelPartArr[0] = rightSide ? rightPart : leftPart;
                focusedParts = modelPartArr;
            }
        } else {
            focusedParts = new ModelPart[0];
        }
        float translationX = 0.0f;
        float translationY = 0.0f;
        for (ModelPart part : focusedParts) {
            translationX += part.getModelPartTransform().getTranslation().getX();
            translationY += part.getModelPartTransform().getTranslation().getY();
            if (!part.getBoxes().isEmpty()) {
                ModelBox box = part.getBoxes().get(0);
                translationX += (box.getMinX() + box.getMaxX()) / 2.0f;
                translationY += (box.getMinY() + box.getMaxY()) / 2.0f;
            }
        }
        if (focusedParts.length != 0) {
            translationX /= -focusedParts.length;
            translationY /= -focusedParts.length;
        }
        if (position != null) {
            translationX += position.shift().getX();
            translationY += position.shift().getY();
        }
        this.translation.set(translationX, translationY, 0.0f);
        modelWidget.setMaxTranslation(translationX, translationY);
        float width = (itemDetails.isMirror() && attachedToArm) ? playerModel.getBody().getWidth() : 0.0f;
        for (ModelPart focusedPart : focusedParts) {
            width += focusedPart.getWidth();
        }
        float height = 0.0f;
        for (ModelPart focusedPart2 : focusedParts) {
            height = Math.max(height, focusedPart2.getHeight());
        }
        if (width == 0.0f || height == 0.0f) {
            this.scale.set(1.0f, 1.0f, 1.0f);
            modelWidget.setMaxScale(1.0f);
        } else {
            float scaleX = playerModel.getWidth() / width;
            float scaleY = playerModel.getHeight() / height;
            float scale = Math.min(scaleX, scaleY);
            this.scale.set(scale, scale, scale);
            modelWidget.setMaxScale(scale);
        }
        this.focusTransitionStart = TimeUtil.getMillis();
    }

    public void reset(PlayerModelWidget modelWidget) {
        reset(DEFAULT_TRANSLATION, modelWidget.rotation(), DEFAULT_SCALE);
        modelWidget.setMaxTranslation(0.0f, 0.0f);
        modelWidget.setMaxScale(1.0f);
    }

    public void reset(FloatVector3 translation, FloatVector3 rotation, FloatVector3 scale) {
        this.translation.set(translation.getX(), translation.getY(), translation.getZ());
        this.rotation.set(rotation);
        this.scale.set(scale);
        this.focusTransitionStart = TimeUtil.getMillis();
    }

    public FloatVector3 translation() {
        return this.translation;
    }

    public FloatVector3 rotation() {
        return this.rotation;
    }

    public FloatVector3 scale() {
        return this.scale;
    }
}
