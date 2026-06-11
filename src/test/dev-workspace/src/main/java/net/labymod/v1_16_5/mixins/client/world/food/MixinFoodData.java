package net.labymod.v1_16_5.mixins.client.world.food;

import net.labymod.api.client.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/food/MixinFoodData.class */
@Mixin({bhy.class})
public class MixinFoodData implements FoodData {

    @Shadow
    private float b;

    @Shadow
    private int a;

    @Override // net.labymod.api.client.world.food.FoodData
    public float getSaturationLevel() {
        return this.b;
    }

    @Override // net.labymod.api.client.world.food.FoodData
    public int getFoodLevel() {
        return this.a;
    }
}
