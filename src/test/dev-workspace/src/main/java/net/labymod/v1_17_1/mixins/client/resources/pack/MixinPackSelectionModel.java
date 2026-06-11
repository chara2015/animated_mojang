package net.labymod.v1_17_1.mixins.client.resources.pack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.labymod.api.client.resources.pack.rich.PackSelectionModel;
import net.labymod.api.client.resources.pack.rich.ResourcePackDetails;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/resources/pack/MixinPackSelectionModel.class */
@Mixin({edh.class})
@Implements({@Interface(iface = PackSelectionModel.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPackSelectionModel implements PackSelectionModel {

    @Shadow
    @Final
    private List<adg> c;

    @Shadow
    @Final
    private List<adg> b;

    @Shadow
    @Final
    private adi a;

    @Shadow
    public abstract void shadow$d();

    @Intrinsic
    @NotNull
    public List<ResourcePackDetails> labyMod$getUnselected() {
        return (List) CastUtil.cast((Collection<?>) this.c);
    }

    @Intrinsic
    @NotNull
    public List<ResourcePackDetails> labyMod$getSelected() {
        return (List) CastUtil.cast((Collection<?>) this.b);
    }

    @Intrinsic
    public void labyMod$updatePacks(@NotNull List<ResourcePackDetails> selected, @NotNull List<ResourcePackDetails> unselected) {
        this.b.clear();
        this.c.clear();
        this.b.addAll(CastUtil.cast((Collection<?>) selected));
        this.c.addAll(CastUtil.cast((Collection<?>) unselected));
        labymod4$updateRepoSelectedList();
    }

    @Intrinsic
    public void labyMod$selectPack(@NotNull ResourcePackDetails pack) {
        if (!this.c.remove((adg) pack)) {
            throw new IllegalArgumentException("The provided pack is not unselected.");
        }
        this.b.add((adg) pack);
        labymod4$updateRepoSelectedList();
    }

    @Intrinsic
    public void labyMod$deselectPack(@NotNull ResourcePackDetails pack) {
        if (!this.b.remove((adg) pack)) {
            throw new IllegalArgumentException("The provided pack is not selected.");
        }
        this.c.add((adg) pack);
    }

    @Intrinsic
    public void labyMod$commitPacks() {
        labymod4$saveToOptions();
    }

    @Intrinsic
    public void labyMod$findNewPacks() {
        shadow$d();
    }

    @Unique
    private void labymod4$updateRepoSelectedList() {
        List<adg> reversed = Lists.reverse(this.b);
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (adg pack : reversed) {
            builder.add(pack.e());
        }
        this.a.a(builder.build());
    }

    @Unique
    private void labymod4$saveToOptions() {
        dvp minecraft = dvp.C();
        dvt options = minecraft.l;
        ImmutableList immutableListCopyOf = ImmutableList.copyOf(options.o);
        options.o.clear();
        options.p.clear();
        for (adg selectedPack : this.a.e()) {
            if (!selectedPack.g()) {
                options.o.add(selectedPack.e());
                if (!selectedPack.c().a()) {
                    options.p.add(selectedPack.e());
                }
            }
        }
        options.b();
        if (!immutableListCopyOf.equals(ImmutableList.copyOf(options.o))) {
            minecraft.j();
        }
    }
}
