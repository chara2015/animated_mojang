package net.labymod.api.event.client.render.item;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/item/RenderFirstPersonItemInHandEvent.class */
public class RenderFirstPersonItemInHandEvent implements Event {
    private final Stack stack;
    private final Player player;
    private final PlayerModel playerModel;
    private final LivingEntity.Hand hand;
    private final LivingEntity.HandSide side;
    private final ItemStack itemStack;
    private final AnimationType animationType;
    private final float partialTicks;
    private float equipProgress;
    private float attackProgress;
    private TransformPhase phase;
    private boolean renderItem = true;
    private boolean applyItemArmTransform = true;
    private boolean applyItemArmAttackTransform = true;
    private boolean attackWhileItemUse = false;
    private boolean isUsingItem;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/item/RenderFirstPersonItemInHandEvent$AnimationTypeMapper.class */
    @Referenceable
    public interface AnimationTypeMapper {
        AnimationType fromMinecraft(Object obj);

        Object toMinecraft(AnimationType animationType);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/item/RenderFirstPersonItemInHandEvent$TransformPhase.class */
    public enum TransformPhase {
        HEAD,
        PRE_ARM_TRANSFORM,
        POST_ARM_TRANSFORM,
        PRE_EAT_TRANSFORM,
        POST_EAT_TRANSFORM,
        PRE_ATTACK_TRANSFORM,
        POST_ATTACK_TRANSFORM,
        PRE_RENDER
    }

    public RenderFirstPersonItemInHandEvent(Stack stack, Player player, PlayerModel playerModel, LivingEntity.Hand hand, LivingEntity.HandSide side, ItemStack itemStack, AnimationType animationType, float partialTicks, float equipProgress, float attackProgress, boolean isUsingItem) {
        this.stack = stack;
        this.player = player;
        this.playerModel = playerModel;
        this.hand = hand;
        this.side = side;
        this.itemStack = itemStack;
        this.animationType = animationType;
        this.partialTicks = partialTicks;
        this.equipProgress = equipProgress;
        this.attackProgress = attackProgress;
        this.isUsingItem = isUsingItem;
    }

    public Stack stack() {
        return this.stack;
    }

    public Player player() {
        return this.player;
    }

    public PlayerModel playerModel() {
        return this.playerModel;
    }

    public LivingEntity.Hand hand() {
        return this.hand;
    }

    public LivingEntity.HandSide side() {
        return this.side;
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }

    public AnimationType animationType() {
        return this.animationType;
    }

    public TransformPhase phase() {
        return this.phase;
    }

    @ApiStatus.Internal
    public void setPhase(TransformPhase phase) {
        this.phase = phase;
    }

    public float getAttackProgress() {
        return this.attackProgress;
    }

    public void setAttackProgress(float attackProgress) {
        this.attackProgress = attackProgress;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public float getEquipProgress() {
        return this.equipProgress;
    }

    public void setEquipProgress(float equipProgress) {
        this.equipProgress = equipProgress;
    }

    public boolean isRenderItem() {
        return this.renderItem;
    }

    public void setRenderItem(boolean renderItem) {
        this.renderItem = renderItem;
    }

    public boolean isUsingItem() {
        return this.isUsingItem;
    }

    public void setUsingItem(boolean usingItem) {
        this.isUsingItem = usingItem;
    }

    public boolean isApplyItemArmTransform() {
        return this.applyItemArmTransform;
    }

    public void setApplyItemArmTransform(boolean applyItemArmTransform) {
        this.applyItemArmTransform = applyItemArmTransform;
    }

    public boolean isApplyItemArmAttackTransform() {
        return this.applyItemArmAttackTransform;
    }

    public void setApplyItemArmAttackTransform(boolean applyItemArmAttackTransform) {
        this.applyItemArmAttackTransform = applyItemArmAttackTransform;
    }

    public boolean isAttackWhileItemUse() {
        return this.attackWhileItemUse;
    }

    public void setAttackWhileItemUse(boolean attackWhileItemUse) {
        this.attackWhileItemUse = attackWhileItemUse;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/item/RenderFirstPersonItemInHandEvent$AnimationType.class */
    public enum AnimationType {
        NONE,
        EAT,
        DRINK,
        BLOCK,
        BOW,
        SPEAR,
        TRIDENT,
        CROSSBOW,
        SPYGLASS,
        TOOT_HORN,
        BRUSH,
        BUNDLE;

        public static final AnimationType[] VALUES = values();

        public static AnimationType fromMinecraft(Object animationType) {
            return Laby.references().renderFirstPersonItemInHandEventAnimationTypeMapper().fromMinecraft(animationType);
        }
    }
}
