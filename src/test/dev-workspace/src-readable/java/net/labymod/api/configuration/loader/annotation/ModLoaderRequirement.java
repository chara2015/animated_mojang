package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.api.configuration.loader.annotation.ModRequirement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/annotation/ModLoaderRequirement.class */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModLoaderRequirement {
    String loaderId() default "any";

    ModRequirement.RequirementState state() default ModRequirement.RequirementState.INSTALLED;
}
