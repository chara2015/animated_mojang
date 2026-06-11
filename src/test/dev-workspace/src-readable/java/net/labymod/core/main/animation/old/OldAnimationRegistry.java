package net.labymod.core.main.animation.old;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.event.EventBus;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.core.main.animation.old.animations.BlockBuildOldAnimation;
import net.labymod.core.main.animation.old.animations.BowOldAnimation;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import net.labymod.core.main.animation.old.animations.EquipOldAnimation;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.labymod.core.main.animation.old.animations.FoodOldAnimation;
import net.labymod.core.main.animation.old.animations.GeneralItemPostureOldAnimation;
import net.labymod.core.main.animation.old.animations.HeadRotationOldAnimation;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.labymod.core.main.animation.old.animations.SneakingOldAnimation;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySneakingOldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySwordOldAnimation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/OldAnimationRegistry.class */
@Singleton
@Referenceable
public class OldAnimationRegistry {
    private final Map<String, OldAnimation> animations = new HashMap();
    private final EventBus eventBus;

    @Inject
    public OldAnimationRegistry(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerAnimations() {
        register(new BowOldAnimation());
        register(new FishingRodOldAnimation());
        register(new BlockBuildOldAnimation());
        register(new HeartOldAnimation());
        register(new FoodOldAnimation());
        register(new InventoryLayoutOldAnimation());
        register(new SwordOldAnimation());
        register(new LegacySwordOldAnimation());
        register(new EquipOldAnimation());
        register(new SneakingOldAnimation());
        register(new LegacySneakingOldAnimation());
        register(new RangeOldAnimation());
        register(new SlowdownOldAnimation());
        register(new HeadRotationOldAnimation());
        register(new BackwardsOldAnimation());
        register(new GeneralItemPostureOldAnimation());
        register(new DamageOldAnimation());
    }

    public void register(OldAnimation animation) {
        this.animations.put(animation.getName(), animation);
        this.eventBus.registerListener(animation);
    }

    @Nullable
    public <T extends OldAnimation> T get(String name) {
        T t = (T) this.animations.get(name);
        if (t == null) {
            return null;
        }
        return t;
    }
}
