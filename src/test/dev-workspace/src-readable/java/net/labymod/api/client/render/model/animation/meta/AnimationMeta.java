package net.labymod.api.client.render.model.animation.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/meta/AnimationMeta.class */
public class AnimationMeta<T> {
    private static final Logging LOGGING = Logging.create("Animation Meta");
    public static AnimationMeta<List<AnimationTrigger>> TRIGGER = new AnimationMeta<>("trigger", "t", value -> {
        if ("*".equals(value)) {
            return new ArrayList(AnimationTrigger.values());
        }
        List<AnimationTrigger> list = new ArrayList<>();
        if (value.contains(",")) {
            String[] arr$ = value.split(",");
            for (String triggerName : arr$) {
                AnimationTrigger trigger = AnimationTrigger.getByName(triggerName);
                if (trigger != null) {
                    list.add(trigger);
                }
            }
            return list;
        }
        AnimationTrigger trigger2 = AnimationTrigger.getByName(value);
        if (trigger2 != null) {
            list.add(trigger2);
        }
        return list;
    });
    public static final AnimationMeta<Integer> PROBABILITY = new AnimationMeta<>("probability", "p", Integer::parseInt);
    public static final AnimationMeta<Boolean> FORCE = new AnimationMeta<>("force", "f", Boolean::parseBoolean);
    public static final AnimationMeta<Boolean> QUEUE = new AnimationMeta<>("queue", "q", Boolean::parseBoolean);
    public static final AnimationMeta<Float> SPEED = new AnimationMeta<>("speed", "s", Float::parseFloat);
    public static final AnimationMeta<Boolean> LOCK_ROTATION = new AnimationMeta<>("lock_rotation", "lr", Boolean::parseBoolean);
    public static final AnimationMeta<List<AnimationCondition>> CONDITION = new AnimationMeta<>("condition", "c", value -> {
        if (value == null) {
            return Collections.emptyList();
        }
        try {
            List<AnimationCondition> conditions = new ArrayList<>();
            String[] arr$ = value.split(",");
            for (String condition : arr$) {
                conditions.add(AnimationCondition.findCondition(StringUtil.toUppercase(condition)));
            }
            return conditions;
        } catch (Exception e) {
            LOGGING.error("Invalid condition: {}", value);
            return Collections.emptyList();
        }
    });
    private static final Collection<AnimationMeta<?>> DEFAULTS = Arrays.asList(TRIGGER, PROBABILITY, FORCE, QUEUE, SPEED, LOCK_ROTATION, CONDITION);
    private final String key;
    private final String shortcut;
    private final Function<String, T> parser;

    public static Collection<AnimationMeta<?>> defaults() {
        return DEFAULTS;
    }

    public AnimationMeta(String key, String shortcut, Function<String, T> parser) {
        this.key = key;
        this.shortcut = shortcut;
        this.parser = parser;
    }

    public String getKey() {
        return this.key;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public Function<String, T> getParser() {
        return this.parser;
    }
}
