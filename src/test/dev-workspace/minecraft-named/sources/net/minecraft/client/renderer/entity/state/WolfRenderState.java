package net.minecraft.client.renderer.entity.state;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/WolfRenderState.class */
public class WolfRenderState extends LivingEntityRenderState {
    private static final Identifier DEFAULT_TEXTURE = Identifier.withDefaultNamespace("textures/entity/wolf/wolf.png");
    public boolean isAngry;
    public boolean isSitting;
    public float headRollAngle;
    public float shakeAnim;
    public DyeColor collarColor;
    public float tailAngle = 0.62831855f;
    public float wetShade = 1.0f;
    public Identifier texture = DEFAULT_TEXTURE;
    public ItemStack bodyArmorItem = ItemStack.EMPTY;

    public float getBodyRollAngle(float $$0) {
        float $$1 = (this.shakeAnim + $$0) / 1.8f;
        if ($$1 < 0.0f) {
            $$1 = 0.0f;
        } else if ($$1 > 1.0f) {
            $$1 = 1.0f;
        }
        return Mth.sin($$1 * 3.1415927f) * Mth.sin($$1 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
}
