package net.labymod.v1_16_5.mixins.client.screen.packs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.labymod.v1_16_5.client.resources.pack.ModifiedPackResources;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/screen/packs/MixinPackSelectionModel.class */
@Mixin({drh.class})
public class MixinPackSelectionModel {

    @Shadow
    @Final
    private abw a;

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;retainAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesSelected(List list, Collection<?> c) {
        return list.retainAll(getAvailablePacks());
    }

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesUnselected(List list, Collection<?> c) {
        return list.addAll(getAvailablePacks());
    }

    private List<abu> getAvailablePacks() {
        return (List) this.a.c().stream().filter(pack -> {
            return !(pack.d() instanceof ModifiedPackResources);
        }).collect(Collectors.toList());
    }
}
