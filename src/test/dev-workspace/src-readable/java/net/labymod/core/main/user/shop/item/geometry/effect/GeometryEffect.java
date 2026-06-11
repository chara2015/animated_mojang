package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.main.user.shop.cosmetic.appearance.PartAppearanceStore;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/GeometryEffect.class */
public abstract class GeometryEffect {
    private final String name = getClass().getSimpleName();
    private final Type type;
    protected final ModelPart modelPart;
    protected final String[] arguments;
    private final int parameterCount;
    protected PartAppearanceStore appearanceStore;
    protected PartAppearanceStore.Appearance appearance;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/GeometryEffect$Type.class */
    public enum Type {
        BUFFER_CREATION,
        PHYSIC,
        POST_PROCESSING,
        METADATA;

        public static Type[] VALUES = values();
    }

    protected abstract boolean processParameters();

    public abstract void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData);

    protected GeometryEffect(String effectArgument, ModelPart modelPart, Type type, int parameterCount) {
        this.arguments = effectArgument.split("_");
        this.modelPart = modelPart;
        this.type = type;
        this.parameterCount = parameterCount;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public GeometryEffect get() {
        if (this.arguments.length < this.parameterCount || !processParameters()) {
            return null;
        }
        return this;
    }

    protected String getParameter(int index) {
        return this.arguments[index + 1];
    }

    protected String getParameter(int index, int length) {
        String argument = this.arguments[index + 1];
        if (argument.length() == length) {
            return argument;
        }
        return null;
    }

    public ModelPart getModelPart() {
        return this.modelPart;
    }

    public String[] getArguments() {
        return this.arguments;
    }

    protected boolean hasParameter(int index) {
        return this.arguments.length > index + 1;
    }

    public Type getType() {
        return this.type;
    }

    public void setAppearanceStore(PartAppearanceStore appearanceStore) {
        this.appearanceStore = appearanceStore;
        this.appearance = appearanceStore.getOrCreate(this.modelPart);
    }
}
