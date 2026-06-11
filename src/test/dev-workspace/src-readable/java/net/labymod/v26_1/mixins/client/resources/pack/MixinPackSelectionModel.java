package net.labymod.v26_1.mixins.client.resources.pack;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.resources.pack.rich.ResourcePackDetails;
import net.labymod.api.util.CastUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/resources/pack/MixinPackSelectionModel.class */
@Mixin({PackSelectionModel.class})
@Implements({@Interface(iface = net.labymod.api.client.resources.pack.rich.PackSelectionModel.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPackSelectionModel implements net.labymod.api.client.resources.pack.rich.PackSelectionModel {

    @Shadow
    @Final
    private List<Pack> unselected;

    @Shadow
    @Final
    private List<Pack> selected;

    @Shadow
    @Final
    private PackRepository repository;

    @Shadow
    public abstract void shadow$findNewPacks();

    @Shadow
    abstract void updateRepoSelectedList();

    @Intrinsic
    @NotNull
    public List<ResourcePackDetails> labyMod$getUnselected() {
        return (List) CastUtil.cast((Collection<?>) this.unselected);
    }

    @Intrinsic
    @NotNull
    public List<ResourcePackDetails> labyMod$getSelected() {
        return (List) CastUtil.cast((Collection<?>) this.selected);
    }

    @Intrinsic
    public void labyMod$updatePacks(@NotNull List<ResourcePackDetails> selected, @NotNull List<ResourcePackDetails> unselected) {
        this.selected.clear();
        this.unselected.clear();
        this.selected.addAll(CastUtil.cast((Collection<?>) selected));
        this.unselected.addAll(CastUtil.cast((Collection<?>) unselected));
        updateRepoSelectedList();
    }

    @Intrinsic
    public void labyMod$selectPack(@NotNull ResourcePackDetails pack) {
        if (!this.unselected.remove((Pack) pack)) {
            throw new IllegalArgumentException("The provided pack is not unselected.");
        }
        this.selected.add((Pack) pack);
        updateRepoSelectedList();
    }

    @Intrinsic
    public void labyMod$deselectPack(@NotNull ResourcePackDetails pack) {
        if (!this.selected.remove((Pack) pack)) {
            throw new IllegalArgumentException("The provided pack is not selected.");
        }
        this.unselected.add((Pack) pack);
    }

    @Intrinsic
    public void labyMod$findNewPacks() {
        shadow$findNewPacks();
    }

    @Intrinsic
    public void labyMod$commitPacks() {
        Minecraft.getInstance().options.updateResourcePacks(this.repository);
    }
}
