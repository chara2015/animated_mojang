package net.labymod.v26_1_1.mixins.client.screen.packs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.labymod.v26_1_1.client.resources.pack.PackUtil;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/packs/MixinPackSelectionModel.class */
@Mixin({PackSelectionModel.class})
public class MixinPackSelectionModel {

    @Shadow
    @Final
    private PackRepository repository;

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;retainAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesSelected(List list, Collection<?> c) {
        return list.retainAll(getAvailablePacks());
    }

    @Redirect(method = {"findNewPacks"}, at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", ordinal = 0))
    private boolean filterModifiedPackResourcesUnselected(List list, Collection<?> c) {
        return list.addAll(getAvailablePacks());
    }

    private List<Pack> getAvailablePacks() {
        List<Pack> list = new ArrayList<>();
        for (Pack pack : this.repository.getAvailablePacks()) {
            if (!PackUtil.isModifiedPackResources(pack.open())) {
                list.add(pack);
            }
        }
        return list;
    }
}
