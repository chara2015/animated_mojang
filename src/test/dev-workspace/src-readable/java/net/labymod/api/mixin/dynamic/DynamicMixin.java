package net.labymod.api.mixin.dynamic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mixin/dynamic/DynamicMixin.class */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface DynamicMixin {
    String value();

    Class<? extends DynamicMixinApplier> applier() default AlwaysDynamicMixinApplier.class;
}
