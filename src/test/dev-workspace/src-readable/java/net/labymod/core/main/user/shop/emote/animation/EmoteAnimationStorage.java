package net.labymod.core.main.user.shop.emote.animation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.laby.lib.emote.AbortAction;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.util.function.FloatSupplier;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/animation/EmoteAnimationStorage.class */
public class EmoteAnimationStorage {
    private static final FloatSupplier TICK_PROVIDER = () -> {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        ClientPlayer clientPlayer = minecraft.getClientPlayer();
        if (clientPlayer == null) {
            return Laby.references().gameTickProvider().get();
        }
        return clientPlayer.getRenderTick(minecraft.getPartialTicks());
    };
    private static final AnimationController.AnimationApplier ANIMATION_APPLIER = new EmoteAnimationApplier();
    private static final AnimationController.PartTransformer PART_TRANSFORMER = new EmotePartTransformer();
    private static final long SUSPEND_TIMEOUT = 500;
    private final AnimationController animationController = new DefaultAnimationController().tickProvider(TICK_PROVIDER).animationApplier(ANIMATION_APPLIER).partTransformer(PART_TRANSFORMER);
    private final Collection<String> suspendedParts = new HashSet();
    private final Map<AbortAction, Long> lastActionMetTimes = new HashMap();
    private EmoteItem emote;
    private Model propsModel;
    private ModelBuffer propsModelBuffer;
    private Model steveModel;
    private Model alexModel;
    private boolean abortRequested;
    private boolean lastMoving;
    private boolean lastSneaking;
    private Perspective lastPerspective;

    public void playEmote(EmoteItem emote) {
        deactivate();
        this.suspendedParts.clear();
        this.lastActionMetTimes.clear();
        this.emote = emote;
        Model propsModel = emote.getPropsModel();
        if (propsModel != null) {
            this.propsModel = propsModel.copy();
            if (this.propsModel != null) {
                this.propsModelBuffer = new DefaultModelBuffer(this.propsModel, false);
                this.propsModelBuffer.setResourceLocation(emote.getPropsTextureLocation());
            }
        }
        Model steveModel = emote.getSteveModel();
        if (steveModel != null) {
            this.steveModel = steveModel.copy();
        }
        Model alexModel = emote.getAlexModel();
        if (alexModel != null) {
            this.alexModel = alexModel.copy();
        }
        this.abortRequested = false;
        this.animationController.playNext(emote.getStartAnimation());
    }

    public void suspendAllParts() {
        updateSuspendedParts(null);
    }

    public void updateSuspendedParts(Collection<String> suspendedParts) {
        Collection<TransformationType> disabledSuspensions;
        if (!isActive() || !isPlaying() || this.suspendedParts.equals(suspendedParts)) {
            return;
        }
        long progress = this.animationController.getProgress();
        long suspendEnd = progress + SUSPEND_TIMEOUT;
        if (suspendedParts == null) {
            disabledSuspensions = Collections.emptyList();
        } else {
            disabledSuspensions = this.emote.getDisabledSuspensions();
        }
        Collection<TransformationType> disabledSuspensions2 = disabledSuspensions;
        String playingName = this.animationController.getPlaying().getName();
        ModelAnimation modelAnimation = this.emote.animationContainer().getByName(playingName).copy((partName, partAnimation) -> {
            if (progress > partAnimation.getLength()) {
                return null;
            }
            boolean alreadySuspended = this.suspendedParts.contains(partName);
            boolean suspend = suspendedParts == null || suspendedParts.contains(partName);
            if (alreadySuspended && suspend) {
                return null;
            }
            if (!alreadySuspended && !suspend) {
                return partAnimation;
            }
            Collection<TransformationType> partDisabledSuspensions = new HashSet<>((Collection<? extends TransformationType>) disabledSuspensions2);
            TransformationType[] arr$ = TransformationType.values();
            for (TransformationType type : arr$) {
                if (progress > partAnimation.transformationByType(type).getLength()) {
                    partDisabledSuspensions.add(type);
                }
            }
            if (suspend) {
                FloatVector3 position = partAnimation.position().get(progress);
                FloatVector3 rotation = partAnimation.rotation().get(progress);
                FloatVector3 scale = partAnimation.scale().get(progress);
                partAnimation.filterKeyframes((transformation, keyframe) -> {
                    return partDisabledSuspensions.contains(transformation.type()) || keyframe.getTimestamp() < progress;
                });
                partAnimation.addKeyframes(progress, true, position, rotation, scale, partDisabledSuspensions).addKeyframes(suspendEnd, false, new FloatVector3(), new FloatVector3(rotation).map((v0) -> {
                    return MathHelper.roundRadians(v0);
                }), new FloatVector3(1.0f), partDisabledSuspensions);
            } else {
                FloatVector3 position2 = partAnimation.position().get(suspendEnd);
                FloatVector3 rotation2 = partAnimation.rotation().get(suspendEnd);
                FloatVector3 scale2 = partAnimation.scale().get(suspendEnd);
                partAnimation.filterKeyframes((transformation2, keyframe2) -> {
                    return disabledSuspensions2.contains(transformation2.type()) || keyframe2.getTimestamp() > suspendEnd;
                });
                partAnimation.addKeyframes(progress, true, new FloatVector3(), new FloatVector3(rotation2).map((v0) -> {
                    return MathHelper.roundRadians(v0);
                }), new FloatVector3(1.0f), partDisabledSuspensions).addKeyframes(suspendEnd, true, position2, rotation2, scale2, partDisabledSuspensions);
            }
            return partAnimation;
        });
        if (suspendedParts == null) {
            modelAnimation.removeMeta(EmoteAnimationMeta.TRIGGER_EMOTE);
        }
        this.animationController.swap(modelAnimation);
        this.suspendedParts.clear();
        if (suspendedParts != null) {
            this.suspendedParts.addAll(suspendedParts);
        }
    }

    public boolean isActive() {
        return (this.emote == null || this.emote.animationContainer().getAnimations() == null) ? false : true;
    }

    public boolean isPlaying() {
        return this.animationController.isPlaying();
    }

    public boolean hasProps() {
        return (this.propsModel == null || this.propsModelBuffer == null) ? false : true;
    }

    public boolean hasPlayerModel() {
        return (this.steveModel == null || this.alexModel == null) ? false : true;
    }

    public void deactivate() {
        this.emote = null;
        this.propsModel = null;
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
        }
    }

    public void requestAbort() {
        this.abortRequested = true;
    }

    public void actionMet(AbortAction abortAction) {
        this.lastActionMetTimes.put(abortAction, Long.valueOf(TimeUtil.getMillis()));
    }

    public boolean isActionMetDelay(AbortAction abortAction) {
        long lastActionMetTime = this.lastActionMetTimes.getOrDefault(abortAction, 0L).longValue();
        return TimeUtil.getMillis() - lastActionMetTime < SUSPEND_TIMEOUT;
    }

    public AnimationController animationController() {
        return this.animationController;
    }

    public Collection<String> getSuspendedParts() {
        return this.suspendedParts;
    }

    public EmoteItem getEmote() {
        return this.emote;
    }

    public Model getPropsModel() {
        return this.propsModel;
    }

    public ModelBuffer getPropsModelBuffer() {
        return this.propsModelBuffer;
    }

    public Model getSteveModel() {
        return this.steveModel;
    }

    public Model getAlexModel() {
        return this.alexModel;
    }

    public boolean isAbortRequested() {
        return this.abortRequested;
    }

    public boolean isLastMoving() {
        return this.lastMoving;
    }

    public void setLastMoving(boolean lastMoving) {
        this.lastMoving = lastMoving;
    }

    public boolean isLastSneaking() {
        return this.lastSneaking;
    }

    public void setLastSneaking(boolean lastSneaking) {
        this.lastSneaking = lastSneaking;
    }

    public Perspective getLastPerspective() {
        return this.lastPerspective;
    }

    public void setLastPerspective(Perspective lastPerspective) {
        this.lastPerspective = lastPerspective;
    }
}
