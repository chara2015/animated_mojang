package net.labymod.v1_21.mixins.client.screen.packs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.labymod.v1_21.client.resources.pack.PackUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/screen/packs/MixinPackSelectionModel.class */
@Mixin({frs.class})
public class MixinPackSelectionModel {

    @Shadow
    @Final
    private atp a;

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;retainAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesSelected(List list, Collection<?> c) {
        return list.retainAll(getAvailablePacks());
    }

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesUnselected(List list, Collection<?> c) {
        return list.addAll(getAvailablePacks());
    }

    private List<atm> getAvailablePacks() {
        List<atm> list = new ArrayList<>();
        for (atm pack : this.a.c()) {
            if (!PackUtil.isModifiedPackResources(pack.f())) {
                list.add(pack);
            }
        }
        return list;
    }
}
