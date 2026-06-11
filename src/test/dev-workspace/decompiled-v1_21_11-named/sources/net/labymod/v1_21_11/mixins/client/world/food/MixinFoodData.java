package net.labymod.v1_21_11.mixins.client.world.food;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/food/MixinFoodData.class */
@Mixin({FoodData.class})
public class MixinFoodData implements net.labymod.api.client.world.food.FoodData {

    @Shadow
    private float d;

    @Shadow
    private int c;

    public float getSaturationLevel() {
        return this.d;
    }

    public int getFoodLevel() {
        return this.c;
    }
}
