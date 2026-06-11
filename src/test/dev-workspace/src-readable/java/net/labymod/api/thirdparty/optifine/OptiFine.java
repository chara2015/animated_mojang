package net.labymod.api.thirdparty.optifine;

import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/thirdparty/optifine/OptiFine.class */
@Referenceable
public interface OptiFine {
    public static final String BETTER_GRASS_CLASS_NAME = "net.optifine.BetterGrass";
    public static final boolean BUNDLED_OPTIFINE = SystemProperties.BUNDLED_OPTIFINE.get().booleanValue();
    public static final String NAMESPACE = "optifine";
    public static final String FABRIC_MOD_ID = "optifabric";

    boolean isOptiFinePresent();

    boolean isOptiFabricPresent();

    OptiFineConfig optiFineConfig();

    static boolean isPresent() {
        return Laby.references().optiFine().isOptiFinePresent();
    }

    static boolean isPresentViaFabric() {
        return Laby.references().optiFine().isOptiFabricPresent();
    }

    static OptiFineConfig config() {
        return Laby.references().optiFine().optiFineConfig();
    }
}
