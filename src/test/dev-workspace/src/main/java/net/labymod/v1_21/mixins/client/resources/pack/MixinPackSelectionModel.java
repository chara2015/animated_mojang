package net.labymod.v1_21.mixins.client.resources.pack;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/resources/pack/MixinPackSelectionModel.class */
@Mixin({frs.class})
@Implements({@Interface(iface = PackSelectionModel.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPackSelectionModel implements PackSelectionModel {

    @Shadow
    @Final
    private List<atm> c;

    @Shadow
    @Final
    private List<atm> b;

    @Shadow
    @Final
    private atp a;

    @Shadow
    public abstract void shadow$d();

    @Shadow
    abstract void e();

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
        e();
    }

    @Intrinsic
    public void labyMod$selectPack(@NotNull ResourcePackDetails pack) {
        if (!this.c.remove((atm) pack)) {
            throw new IllegalArgumentException("The provided pack is not unselected.");
        }
        this.b.add((atm) pack);
        e();
    }

    @Intrinsic
    public void labyMod$deselectPack(@NotNull ResourcePackDetails pack) {
        if (!this.b.remove((atm) pack)) {
            throw new IllegalArgumentException("The provided pack is not selected.");
        }
        this.c.add((atm) pack);
    }

    @Intrinsic
    public void labyMod$findNewPacks() {
        shadow$d();
    }

    @Intrinsic
    public void labyMod$commitPacks() {
        fgo.Q().m.a(this.a);
    }
}
