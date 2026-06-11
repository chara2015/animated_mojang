package net.labymod.core.event.client.render.world;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.world.RenderWorldEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/world/RenderWorldEventCaller.class */
public final class RenderWorldEventCaller {
    public static void callPre(Stack stack, float partialTicks) {
        call(stack, Phase.PRE, partialTicks);
    }

    public static void callPost(Stack stack, float partialTicks) {
        call(stack, Phase.POST, partialTicks);
    }

    private static void call(Stack stack, Phase phase, float partialTicks) {
        MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        Laby.fireEvent(new RenderWorldEvent(stack, phase, camera, partialTicks));
    }
}
