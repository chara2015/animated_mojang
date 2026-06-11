package net.labymod.api.client.gui.icon;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.loader.platform.PlatformEnvironment;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/IconBatch.class */
public class IconBatch {
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
    private final Map<String, List<BatchIcon>> icons = new HashMap();

    public void addIcon(Stack stack, Icon icon, float x, float y, float width, float height, int rgb) {
        Matrix4f m = JomlMath.extractMatrix(stack.getProvider().getPose());
        ResourceLocation location = icon.getResourceLocation();
        String key = "";
        if (location != null) {
            key = location.getNamespace() + ":" + location.getPath();
        }
        this.icons.computeIfAbsent(key, l -> {
            return new ArrayList();
        }).add(new BatchIcon(icon, m, x, y, width, height, rgb));
    }

    public void renderBatch(Stack stack) {
        if (this.icons.isEmpty()) {
            return;
        }
        boolean ancient = PlatformEnvironment.isAncientOpenGL();
        if (ancient) {
            stack.push();
            stack.identity();
        }
        Stack customStack = this.stack;
        for (Map.Entry<String, List<BatchIcon>> entry : this.icons.entrySet()) {
            renderBatch(customStack, entry.getValue().get(0).icon().getResourceLocation(), entry.getValue());
        }
        this.icons.clear();
        if (ancient) {
            stack.pop();
        }
    }

    private void renderBatch(Stack stack, ResourceLocation location, List<BatchIcon> icons) {
        if (location == null) {
            for (BatchIcon icon : icons) {
                stack.getProvider().getPose().set(icon.matrix);
                icon.icon.render(stack, icon.x, icon.y, icon.width, icon.height, false, icon.rgb);
            }
            return;
        }
        ResourceRenderContext batch = Laby.references().resourceRenderContext().begin(stack);
        for (BatchIcon icon2 : icons) {
            stack.getProvider().getPose().set(icon2.matrix);
            icon2.icon.render(batch, icon2.x, icon2.y, icon2.width, icon2.height, false, icon2.rgb);
        }
        ShaderTextures.setShaderTexture(0, location);
        batch.uploadToBuffer();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/IconBatch$BatchIcon.class */
    private static final class BatchIcon extends Record {
        private final Icon icon;
        private final Matrix4f matrix;
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private final int rgb;

        private BatchIcon(Icon icon, Matrix4f matrix, float x, float y, float width, float height, int rgb) {
            this.icon = icon;
            this.matrix = matrix;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.rgb = rgb;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BatchIcon.class), BatchIcon.class, "icon;matrix;x;y;width;height;rgb", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->matrix:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->x:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->y:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->width:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->height:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->rgb:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BatchIcon.class), BatchIcon.class, "icon;matrix;x;y;width;height;rgb", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->matrix:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->x:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->y:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->width:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->height:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->rgb:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BatchIcon.class, Object.class), BatchIcon.class, "icon;matrix;x;y;width;height;rgb", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->matrix:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->x:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->y:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->width:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->height:F", "FIELD:Lnet/labymod/api/client/gui/icon/IconBatch$BatchIcon;->rgb:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Icon icon() {
            return this.icon;
        }

        public Matrix4f matrix() {
            return this.matrix;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public float width() {
            return this.width;
        }

        public float height() {
            return this.height;
        }

        public int rgb() {
            return this.rgb;
        }
    }
}
