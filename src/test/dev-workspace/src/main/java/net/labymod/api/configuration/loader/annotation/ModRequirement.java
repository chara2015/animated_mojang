package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.api.modloader.ModLoaderId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/annotation/ModRequirement.class */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModRequirement {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/annotation/ModRequirement$RequirementState.class */
    public enum RequirementState {
        INSTALLED,
        NOT_INSTALLED
    }

    String namespace();

    RequirementState state() default RequirementState.INSTALLED;

    RequirementType type() default RequirementType.ADDON;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/annotation/ModRequirement$RequirementType.class */
    public enum RequirementType {
        ADDON(null),
        FABRIC_MOD(ModLoaderId.FABRIC),
        FORGE_MOD(ModLoaderId.FORGE),
        NEO_FORGE_MOD(ModLoaderId.NEO_FORGE);

        private final String loaderId;

        RequirementType(String loaderId) {
            this.loaderId = loaderId;
        }

        public String getLoaderId() {
            return this.loaderId;
        }
    }
}
