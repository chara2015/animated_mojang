package net.labymod.core.main.user.shop.emote.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.laby.lib.cosmetics.AttachmentPoint;
import net.laby.lib.emote.AbortAction;
import net.laby.lib.emote.Emote;
import net.laby.lib.emote.SuspensionType;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CharSequences;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.Purchasable;
import net.labymod.core.main.user.shop.item.texture.Ratio;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/model/EmoteItem.class */
public class EmoteItem implements Purchasable {
    private final Emote emote;
    private final Perspective perspective;
    private final Ratio textureRatio;
    private final Collection<TransformationType> disabledSuspensions;
    private transient CharSequence lowercaseName;
    private transient AnimationContainer animationContainer;
    private transient Model propsModel;
    private transient ResourceLocation propsTextureLocation;
    private transient AnimatedResourceLocation animatedPropsTextureLocation;
    private transient Model steveModel;
    private transient Model alexModel;

    public EmoteItem(Emote emote, Perspective perspective) {
        Ratio ratio;
        this.emote = emote;
        this.perspective = perspective;
        if (emote.ratio() != null) {
            ratio = new Ratio(emote.ratio().width(), emote.ratio().height());
        } else {
            ratio = null;
        }
        this.textureRatio = ratio;
        this.disabledSuspensions = convertSuspensions();
        this.lowercaseName = CharSequences.toLowerCase(emote.name());
    }

    public int getId() {
        return this.emote.id();
    }

    @Override // net.labymod.core.main.user.shop.Purchasable
    public String getName() {
        return this.emote.name();
    }

    public Ratio getTextureRatio() {
        return this.textureRatio;
    }

    public long getTextureAnimationDelay() {
        if (this.emote.textureAnimationDelay() == 0) {
            return 50L;
        }
        return this.emote.textureAnimationDelay();
    }

    public CharSequence getLowercaseName() {
        if (this.lowercaseName == null) {
            this.lowercaseName = CharSequences.toLowerCase(getName());
        }
        return this.lowercaseName;
    }

    public Collection<AbortAction> getAbortActions() {
        if (this.emote.abortActions() == null) {
            return Collections.emptyList();
        }
        return this.emote.abortActions();
    }

    public Collection<AbortAction> getIgnoredAbortActions() {
        if (this.emote.ignoredAbortActions() == null) {
            return Collections.emptyList();
        }
        return this.emote.ignoredAbortActions();
    }

    public Collection<TransformationType> getDisabledSuspensions() {
        return this.disabledSuspensions;
    }

    public AttachmentPoint getAttachedTo() {
        return this.emote.attachedTo();
    }

    public boolean isDraft() {
        return this.emote.draft();
    }

    public boolean hasProps() {
        return this.emote.props();
    }

    public boolean hasPlayerModel() {
        return this.emote.playerModel();
    }

    public Perspective getPerspective() {
        return this.perspective;
    }

    public AnimationContainer animationContainer() {
        return this.animationContainer;
    }

    public void setAnimationContainer(AnimationContainer animationContainer) {
        this.animationContainer = animationContainer;
    }

    public ModelAnimation getStartAnimation() {
        return this.animationContainer.getByTrigger(AnimationTrigger.NONE);
    }

    public Model getPropsModel() {
        return this.propsModel;
    }

    public void setPropsModel(Model propsModel) {
        this.propsModel = propsModel;
    }

    public ResourceLocation getPropsTextureLocation() {
        return this.propsTextureLocation;
    }

    public void setPropsTextureLocation(ResourceLocation propsTextureLocation) {
        this.propsTextureLocation = propsTextureLocation;
    }

    public AnimatedResourceLocation getAnimatedPropsTextureLocation() {
        return this.animatedPropsTextureLocation;
    }

    public void setAnimatedPropsTextureLocation(AnimatedResourceLocation animatedPropsTextureLocation) {
        this.animatedPropsTextureLocation = animatedPropsTextureLocation;
    }

    public Model getSteveModel() {
        return this.steveModel;
    }

    public void setSteveModel(Model steveModelJson) {
        this.steveModel = steveModelJson;
    }

    public Model getAlexModel() {
        return this.alexModel;
    }

    public void setAlexModel(Model alexModel) {
        this.alexModel = alexModel;
    }

    public void updateAnimatedTexture(ModelBuffer propsModelBuffer) {
        if (this.animatedPropsTextureLocation != null) {
            ResourceLocation previousLocation = propsModelBuffer.getResourceLocation();
            this.animatedPropsTextureLocation.update();
            ResourceLocation currentLocation = this.animatedPropsTextureLocation.getCurrentResourceLocation();
            if (!Objects.equals(previousLocation, currentLocation)) {
                propsModelBuffer.rebuildModel();
                propsModelBuffer.setResourceLocation(currentLocation);
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || super.getClass() != object.getClass()) {
            return false;
        }
        EmoteItem other = (EmoteItem) object;
        return this.emote.id() == other.emote.id();
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.emote.id()));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private Collection<TransformationType> convertSuspensions() throws MatchException {
        TransformationType transformationType;
        List<SuspensionType> suspensions = this.emote.disabledSuspensions();
        List<TransformationType> result = new ArrayList<>();
        if (suspensions == null) {
            return result;
        }
        for (SuspensionType suspension : suspensions) {
            switch (AnonymousClass1.$SwitchMap$net$laby$lib$emote$SuspensionType[suspension.ordinal()]) {
                case 1:
                    transformationType = TransformationType.POSITION;
                    break;
                case 2:
                    transformationType = TransformationType.ROTATION;
                    break;
                case 3:
                    transformationType = TransformationType.SCALE;
                    break;
                case 4:
                    transformationType = null;
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            TransformationType converted = transformationType;
            if (converted != null) {
                result.add(converted);
            }
        }
        return result;
    }

    /* JADX INFO: renamed from: net.labymod.core.main.user.shop.emote.model.EmoteItem$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/model/EmoteItem$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$laby$lib$emote$SuspensionType = new int[SuspensionType.values().length];

        static {
            try {
                $SwitchMap$net$laby$lib$emote$SuspensionType[SuspensionType.POSITION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$laby$lib$emote$SuspensionType[SuspensionType.ROTATION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$laby$lib$emote$SuspensionType[SuspensionType.SCALE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$laby$lib$emote$SuspensionType[SuspensionType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
