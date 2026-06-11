package net.labymod.core.main.user.shop.emote.animation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/animation/EmoteAnimationMeta.class */
public class EmoteAnimationMeta {
    public static AnimationMeta<Boolean> START_ANIMATION = new AnimationMeta<>("start_animation", "s_a", Boolean::parseBoolean);
    public static final AnimationMeta<Boolean> TRIGGER_EMOTE = new AnimationMeta<>("trigger_emote", "t_e", Boolean::parseBoolean);
    public static final AnimationMeta<Boolean> BLOCK_SNEAKING = new AnimationMeta<>("block_sneaking", "b_s", Boolean::parseBoolean);
    public static final AnimationMeta<Boolean> BLOCK_HEAD_ANIMATION = new AnimationMeta<>("block_head_animation", "b_h_a", Boolean::parseBoolean);
    private static final Collection<AnimationMeta<?>> WITH_DEFAULTS;

    static {
        Collection<AnimationMeta<?>> withDefaults = new HashSet<>(AnimationMeta.defaults());
        withDefaults.add(TRIGGER_EMOTE);
        withDefaults.add(BLOCK_SNEAKING);
        withDefaults.add(BLOCK_HEAD_ANIMATION);
        WITH_DEFAULTS = Collections.unmodifiableCollection(withDefaults);
    }

    public static Collection<AnimationMeta<?>> withDefaults() {
        return WITH_DEFAULTS;
    }
}
