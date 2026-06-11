package net.labymod.v26_1_2.client.world.phys.hit;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/world/phys/hit/VersionedHitResultController.class */
@Singleton
@Implements(HitResultController.class)
public class VersionedHitResultController implements HitResultController {
    @Inject
    public VersionedHitResultController() {
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResultController
    public HitResult getResult() {
        return Minecraft.getInstance().hitResult;
    }
}
