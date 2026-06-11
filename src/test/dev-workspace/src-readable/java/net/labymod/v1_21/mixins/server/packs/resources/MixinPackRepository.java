package net.labymod.v1_21.mixins.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.pack.util.EventResourcePackRepositoryCaller;
import net.labymod.v1_21.client.resources.pack.ModifiedRepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/server/packs/resources/MixinPackRepository.class */
@Mixin({atp.class})
public class MixinPackRepository {
    @Redirect(method = {"<init>"}, at = @At(remap = false, value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    private <E> ImmutableSet<E> registerCustomRepositorySources(E[] elements) {
        List<E> repositorySources = new ArrayList<>(Arrays.asList(elements));
        repositorySources.add(new ModifiedRepositorySource(Laby.labyAPI()));
        return ImmutableSet.copyOf(repositorySources.toArray());
    }

    @Inject(method = {"rebuildSelected"}, at = {@At("RETURN")})
    private void labyMod$rebuildSelected(Collection<String> selected, CallbackInfoReturnable<List<atm>> cir) {
        List<atm> selectedPacks = (List) cir.getReturnValue();
        EventResourcePackRepositoryCaller.onRebuildSelected(selectedPacks);
    }
}
