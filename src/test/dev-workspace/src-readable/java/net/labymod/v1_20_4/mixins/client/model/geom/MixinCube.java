package net.labymod.v1_20_4.mixins.client.model.geom;

import java.util.Set;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.accessor.model.ModelPartCubeAccessor;
import net.labymod.core.client.render.model.box.DefaultModelBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/model/geom/MixinCube.class */
@Mixin({a.class})
public class MixinCube implements ModelPartCubeAccessor {
    private ModelBox labyMod$modelBox;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$constructModelBox(int param0, int param1, float param2, float param3, float param4, float param5, float param6, float param7, float param8, float param9, float param10, boolean mirror, float param12, float param13, Set<Direction> visibleFaces, CallbackInfo ci) {
        this.labyMod$modelBox = new DefaultModelBox(param0, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, mirror, param12, param13);
    }

    @Override // net.labymod.core.client.accessor.model.ModelPartCubeAccessor
    public ModelBox getModelBox() {
        return this.labyMod$modelBox;
    }
}
