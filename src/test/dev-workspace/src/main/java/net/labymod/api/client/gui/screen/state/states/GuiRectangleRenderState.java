package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.StateUtil;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiRectangleRenderState.class */
public class GuiRectangleRenderState extends AbstractGuiRenderState {
    private final RectConfig config;

    public GuiRectangleRenderState(Material material, Matrix4f pose, float x, float y, float width, float height, RectConfig config, @Nullable ScissorArea scissorArea) {
        super(material, pose, x, y, width, height, scissorArea);
        this.config = config;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) throws MatchException {
        int vertexColor0 = getVertexColor(0, this.config);
        int vertexColor1 = getVertexColor(1, this.config);
        int vertexColor2 = getVertexColor(2, this.config);
        int vertexColor3 = getVertexColor(3, this.config);
        Matrix4f pose = pose();
        consumer.addVertex2D(pose, this.left, this.top).setBlankUv().setColor(vertexColor0);
        consumer.addVertex2D(pose, this.left, this.bottom).setBlankUv().setColor(vertexColor1);
        consumer.addVertex2D(pose, this.right, this.bottom).setBlankUv().setColor(vertexColor2);
        consumer.addVertex2D(pose, this.right, this.top).setBlankUv().setColor(vertexColor3);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return this.config.getRoundedData() != null;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void consumeCommand(@NotNull DrawCommandContext context) {
        super.consumeCommand(context);
        RoundedData roundedData = this.config.getRoundedData();
        if (roundedData != null) {
            StateUtil.applyRoundData(context.commandBuffer(), roundedData, pose(), this.left, this.top, this.right, this.bottom);
        }
    }

    protected RectConfig config() {
        return this.config;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private int getVertexColor(int index, RectConfig config) throws MatchException {
        GradientDirection direction = config.getDirection();
        if (direction == null) {
            return config.getArgb();
        }
        switch (direction) {
            case TOP_TO_BOTTOM:
                switch (index) {
                    case 0:
                    case 3:
                        return config.getGradientArgb0();
                    case 1:
                    case 2:
                        return config.getGradientArgb1();
                    default:
                        throw new IllegalStateException("Unexpected value: " + index);
                }
            case BOTTOM_TO_TOP:
                switch (index) {
                    case 0:
                    case 3:
                        return config.getGradientArgb1();
                    case 1:
                    case 2:
                        return config.getGradientArgb0();
                    default:
                        throw new IllegalStateException("Unexpected value: " + index);
                }
            case LEFT_TO_RIGHT:
                switch (index) {
                    case 0:
                    case 1:
                        return config.getGradientArgb0();
                    case 2:
                    case 3:
                        return config.getGradientArgb1();
                    default:
                        throw new IllegalStateException("Unexpected value: " + index);
                }
            case RIGHT_TO_LEFT:
                switch (index) {
                    case 0:
                    case 1:
                        return config.getGradientArgb1();
                    case 2:
                    case 3:
                        return config.getGradientArgb0();
                    default:
                        throw new IllegalStateException("Unexpected value: " + index);
                }
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiRectangleRenderState$RectConfig.class */
    public static class RectConfig {
        private final int argb;

        @Nullable
        private final GradientDirection direction;
        private final int gradientArgb0;
        private final int gradientArgb1;

        @Nullable
        private final RoundedData roundedData;

        private RectConfig(int argb, @Nullable GradientDirection direction, int gradientArgb0, int gradientArgb1, @Nullable RoundedData roundedData) {
            this.argb = ColorUtil.combineAlpha(argb);
            this.direction = direction;
            this.gradientArgb0 = ColorUtil.combineAlpha(gradientArgb0);
            this.gradientArgb1 = ColorUtil.combineAlpha(gradientArgb1);
            this.roundedData = roundedData;
        }

        public static Builder builder() {
            return new Builder();
        }

        public int getArgb() {
            return this.argb;
        }

        @Nullable
        public GradientDirection getDirection() {
            return this.direction;
        }

        public int getGradientArgb0() {
            return this.gradientArgb0;
        }

        public int getGradientArgb1() {
            return this.gradientArgb1;
        }

        @Nullable
        public RoundedData getRoundedData() {
            return this.roundedData;
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiRectangleRenderState$RectConfig$Builder.class */
        public static class Builder {
            private int argb;

            @Nullable
            private GradientDirection direction;
            private int gradientArgb0;
            private int gradientArgb1;

            @Nullable
            private RoundedData roundedData;

            public Builder setArgb(int argb) {
                this.argb = argb;
                return this;
            }

            public Builder setGradient(GradientDirection direction, int gradientArgb0, int gradientArgb1) {
                this.direction = direction;
                this.gradientArgb0 = gradientArgb0;
                this.gradientArgb1 = gradientArgb1;
                return this;
            }

            public Builder setRoundedData(RoundedData roundedData) {
                this.roundedData = roundedData;
                return this;
            }

            public RectConfig build() {
                return new RectConfig(this.argb, this.direction, this.gradientArgb0, this.gradientArgb1, this.roundedData);
            }
        }
    }
}
