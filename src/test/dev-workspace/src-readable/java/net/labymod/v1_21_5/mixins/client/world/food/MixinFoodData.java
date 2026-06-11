package net.labymod.v1_21_5.mixins.client.world.food;

import net.labymod.api.client.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/world/food/MixinFoodData.class */
@Mixin({cvx.class})
public class MixinFoodData implements FoodData {

    @Shadow
    private float d;

    @Shadow
    private int c;

    @Override // net.labymod.api.client.world.food.FoodData
    public float getSaturationLevel() {
        return this.d;
    }

    @Override // net.labymod.api.client.world.food.FoodData
    public int getFoodLevel() {
        return this.c;
    }
}
