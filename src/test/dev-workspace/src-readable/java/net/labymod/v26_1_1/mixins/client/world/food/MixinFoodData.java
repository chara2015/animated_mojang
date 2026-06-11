package net.labymod.v26_1_1.mixins.client.world.food;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/world/food/MixinFoodData.class */
@Mixin({FoodData.class})
public class MixinFoodData implements net.labymod.api.client.world.food.FoodData {

    @Shadow
    private float saturationLevel;

    @Shadow
    private int foodLevel;

    @Override // net.labymod.api.client.world.food.FoodData
    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    @Override // net.labymod.api.client.world.food.FoodData
    public int getFoodLevel() {
        return this.foodLevel;
    }
}
