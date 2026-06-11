package net.labymod.core.main.user.shop.item.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.core.main.user.shop.cosmetic.appearance.PartAppearanceStore;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.laby3d.api.mesh.Mesh;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/renderer/ItemModel.class */
public class ItemModel {
    final Supplier<String> nameSupplier;
    private final ItemEffect effect;

    @Nullable
    private Model model;
    private boolean hasOutlineParts;
    private boolean firstPerson;
    private long lastGlobalVersion;
    private long outlineLastGlobalVersion;
    final List<CosmeticsUniformBlockData> groupCosmeticsData = new ArrayList();
    final List<Mesh> compiledMeshes = new ArrayList();
    final List<Mesh> outlineMeshes = new ArrayList();
    final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
    final PartAppearanceStore appearanceStore = new PartAppearanceStore();

    public ItemModel(Supplier<String> nameSupplier, Supplier<Model> modelSupplier) {
        this.nameSupplier = nameSupplier;
        this.effect = new ItemEffect(modelSupplier);
    }

    public boolean isFirstPerson() {
        return this.firstPerson;
    }

    public void setFirstPerson(boolean firstPerson) {
        this.firstPerson = firstPerson;
    }

    @Nullable
    public Model getModel() {
        return this.model;
    }

    public void setModel(@Nullable Model model) {
        this.model = model;
    }

    public void loadEffects(List<GeometryEffect> effects) {
        this.effect.loadEffects(effects);
        for (GeometryEffect geometryEffect : effects) {
            geometryEffect.setAppearanceStore(this.appearanceStore);
        }
    }

    public void bindAppearanceStore(PartAppearanceStore store) {
        for (GeometryEffect geometryEffect : this.effect.getAllEffects()) {
            geometryEffect.setAppearanceStore(store);
        }
    }

    public PartAppearanceStore appearanceStore() {
        return this.appearanceStore;
    }

    public void restoreDefaultAppearanceStore() {
        bindAppearanceStore(this.appearanceStore);
    }

    public void apply(Player player, PlayerModel playerModel, ItemEffect.EffectData effectData, ItemMetadata itemMetadata, GeometryEffect.Type type) {
        this.effect.apply(player, playerModel, effectData, itemMetadata, type);
    }

    public Collection<GeometryEffect> getEffects(GeometryEffect.Type type) {
        return this.effect.getEffects(type);
    }

    @Nullable
    public GeometryEffect findEffect(GeometryEffect.Type type, Predicate<GeometryEffect> effectFilter) {
        return this.effect.findEffect(type, effectFilter);
    }

    public boolean hasOutlineParts() {
        return this.hasOutlineParts;
    }

    public void setHasOutlineParts(boolean hasOutlineParts) {
        this.hasOutlineParts = hasOutlineParts;
    }

    public long getLastGlobalVersion() {
        return this.lastGlobalVersion;
    }

    public void setLastGlobalVersion(long lastGlobalVersion) {
        this.lastGlobalVersion = lastGlobalVersion;
    }

    public long getOutlineLastGlobalVersion() {
        return this.outlineLastGlobalVersion;
    }

    public void setOutlineLastGlobalVersion(long outlineLastGlobalVersion) {
        this.outlineLastGlobalVersion = outlineLastGlobalVersion;
    }

    public String getName() {
        return this.nameSupplier.get();
    }

    public void close() {
        for (Mesh mesh : this.compiledMeshes) {
            if (mesh != null) {
                mesh.close();
            }
        }
        this.compiledMeshes.clear();
        for (Mesh mesh2 : this.outlineMeshes) {
            if (mesh2 != null) {
                mesh2.close();
            }
        }
        this.outlineMeshes.clear();
    }
}
