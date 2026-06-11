package net.minecraft.client.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.client.gui.render.state.ColoredRectangleRenderState;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import net.minecraft.client.gui.render.state.TiledBlitRenderState;
import net.minecraft.client.gui.render.state.pip.GuiBannerResultRenderState;
import net.minecraft.client.gui.render.state.pip.GuiBookModelRenderState;
import net.minecraft.client.gui.render.state.pip.GuiEntityRenderState;
import net.minecraft.client.gui.render.state.pip.GuiProfilerChartRenderState;
import net.minecraft.client.gui.render.state.pip.GuiSignRenderState;
import net.minecraft.client.gui.render.state.pip.GuiSkinRenderState;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.AtlasIds;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.joml.Quaternionf;
import org.joml.Vector2ic;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/GuiGraphics.class */
public class GuiGraphics {
    private static final int EXTRA_SPACE_AFTER_FIRST_TOOLTIP_LINE = 2;
    final Minecraft minecraft;
    private final Matrix3x2fStack pose;
    private final ScissorStack scissorStack;
    private final MaterialSet materials;
    private final TextureAtlas guiSprites;
    final GuiRenderState guiRenderState;
    private CursorType pendingCursor;
    final int mouseX;
    final int mouseY;
    private Runnable deferredTooltip;
    Style hoveredTextStyle;
    Style clickableTextStyle;

    private GuiGraphics(Minecraft $$0, Matrix3x2fStack $$1, GuiRenderState $$2, int $$3, int $$4) {
        this.scissorStack = new ScissorStack();
        this.pendingCursor = CursorType.DEFAULT;
        this.minecraft = $$0;
        this.pose = $$1;
        this.mouseX = $$3;
        this.mouseY = $$4;
        AtlasManager $$5 = $$0.getAtlasManager();
        this.materials = $$5;
        this.guiSprites = $$5.getAtlasOrThrow(AtlasIds.GUI);
        this.guiRenderState = $$2;
    }

    public GuiGraphics(Minecraft $$0, GuiRenderState $$1, int $$2, int $$3) {
        this($$0, new Matrix3x2fStack(16), $$1, $$2, $$3);
    }

    public void requestCursor(CursorType $$0) {
        this.pendingCursor = $$0;
    }

    public void applyCursor(Window $$0) {
        $$0.selectCursor(this.pendingCursor);
    }

    public int guiWidth() {
        return this.minecraft.getWindow().getGuiScaledWidth();
    }

    public int guiHeight() {
        return this.minecraft.getWindow().getGuiScaledHeight();
    }

    public void nextStratum() {
        this.guiRenderState.nextStratum();
    }

    public void blurBeforeThisStratum() {
        this.guiRenderState.blurBeforeThisStratum();
    }

    public Matrix3x2fStack pose() {
        return this.pose;
    }

    public void hLine(int $$0, int $$1, int $$2, int $$3) {
        if ($$1 < $$0) {
            $$0 = $$1;
            $$1 = $$0;
        }
        fill($$0, $$2, $$1 + 1, $$2 + 1, $$3);
    }

    public void vLine(int $$0, int $$1, int $$2, int $$3) {
        if ($$2 < $$1) {
            $$1 = $$2;
            $$2 = $$1;
        }
        fill($$0, $$1 + 1, $$0 + 1, $$2, $$3);
    }

    public void enableScissor(int $$0, int $$1, int $$2, int $$3) {
        ScreenRectangle $$4 = new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1).transformAxisAligned(this.pose);
        this.scissorStack.push($$4);
    }

    public void disableScissor() {
        this.scissorStack.pop();
    }

    public boolean containsPointInScissor(int $$0, int $$1) {
        return this.scissorStack.containsPoint($$0, $$1);
    }

    public void fill(int $$0, int $$1, int $$2, int $$3, int $$4) {
        fill(RenderPipelines.GUI, $$0, $$1, $$2, $$3, $$4);
    }

    public void fill(RenderPipeline $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        if ($$1 < $$3) {
            $$1 = $$3;
            $$3 = $$1;
        }
        if ($$2 < $$4) {
            $$2 = $$4;
            $$4 = $$2;
        }
        submitColoredRectangle($$0, TextureSetup.noTexture(), $$1, $$2, $$3, $$4, $$5, null);
    }

    public void fillGradient(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        submitColoredRectangle(RenderPipelines.GUI, TextureSetup.noTexture(), $$0, $$1, $$2, $$3, $$4, Integer.valueOf($$5));
    }

    public void fill(RenderPipeline $$0, TextureSetup $$1, int $$2, int $$3, int $$4, int $$5) {
        submitColoredRectangle($$0, $$1, $$2, $$3, $$4, $$5, -1, null);
    }

    private void submitColoredRectangle(RenderPipeline $$0, TextureSetup $$1, int $$2, int $$3, int $$4, int $$5, int $$6, Integer $$7) {
        this.guiRenderState.submitGuiElement(new ColoredRectangleRenderState($$0, $$1, new Matrix3x2f(this.pose), $$2, $$3, $$4, $$5, $$6, $$7 != null ? $$7.intValue() : $$6, this.scissorStack.peek()));
    }

    public void textHighlight(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
        if ($$4) {
            fill(RenderPipelines.GUI_INVERT, $$0, $$1, $$2, $$3, -1);
        }
        fill(RenderPipelines.GUI_TEXT_HIGHLIGHT, $$0, $$1, $$2, $$3, CommonColors.BLUE);
    }

    public void drawCenteredString(Font $$0, String $$1, int $$2, int $$3, int $$4) {
        drawString($$0, $$1, $$2 - ($$0.width($$1) / 2), $$3, $$4);
    }

    public void drawCenteredString(Font $$0, Component $$1, int $$2, int $$3, int $$4) {
        FormattedCharSequence $$5 = $$1.getVisualOrderText();
        drawString($$0, $$5, $$2 - ($$0.width($$5) / 2), $$3, $$4);
    }

    public void drawCenteredString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4) {
        drawString($$0, $$1, $$2 - ($$0.width($$1) / 2), $$3, $$4);
    }

    public void drawString(Font $$0, String $$1, int $$2, int $$3, int $$4) {
        drawString($$0, $$1, $$2, $$3, $$4, true);
    }

    public void drawString(Font $$0, String $$1, int $$2, int $$3, int $$4, boolean $$5) {
        if ($$1 == null) {
            return;
        }
        drawString($$0, Language.getInstance().getVisualOrder(FormattedText.of($$1)), $$2, $$3, $$4, $$5);
    }

    public void drawString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4) {
        drawString($$0, $$1, $$2, $$3, $$4, true);
    }

    public void drawString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4, boolean $$5) {
        if (ARGB.alpha($$4) == 0) {
            return;
        }
        this.guiRenderState.submitText(new GuiTextRenderState($$0, $$1, new Matrix3x2f(this.pose), $$2, $$3, $$4, 0, $$5, false, this.scissorStack.peek()));
    }

    public void drawString(Font $$0, Component $$1, int $$2, int $$3, int $$4) {
        drawString($$0, $$1, $$2, $$3, $$4, true);
    }

    public void drawString(Font $$0, Component $$1, int $$2, int $$3, int $$4, boolean $$5) {
        drawString($$0, $$1.getVisualOrderText(), $$2, $$3, $$4, $$5);
    }

    public void drawWordWrap(Font $$0, FormattedText $$1, int $$2, int $$3, int $$4, int $$5) {
        drawWordWrap($$0, $$1, $$2, $$3, $$4, $$5, true);
    }

    public void drawWordWrap(Font $$0, FormattedText $$1, int $$2, int $$3, int $$4, int $$5, boolean $$6) {
        for (FormattedCharSequence $$7 : $$0.split($$1, $$4)) {
            drawString($$0, $$7, $$2, $$3, $$5, $$6);
            Objects.requireNonNull($$0);
            $$3 += 9;
        }
    }

    public void drawStringWithBackdrop(Font $$0, Component $$1, int $$2, int $$3, int $$4, int $$5) {
        int $$6 = this.minecraft.options.getBackgroundColor(0.0f);
        if ($$6 != 0) {
            Objects.requireNonNull($$0);
            fill($$2 - 2, $$3 - 2, $$2 + $$4 + 2, $$3 + 9 + 2, ARGB.multiply($$6, $$5));
        }
        drawString($$0, $$1, $$2, $$3, $$5, true);
    }

    public void renderOutline(int $$0, int $$1, int $$2, int $$3, int $$4) {
        fill($$0, $$1, $$0 + $$2, $$1 + 1, $$4);
        fill($$0, ($$1 + $$3) - 1, $$0 + $$2, $$1 + $$3, $$4);
        fill($$0, $$1 + 1, $$0 + 1, ($$1 + $$3) - 1, $$4);
        fill(($$0 + $$2) - 1, $$1 + 1, $$0 + $$2, ($$1 + $$3) - 1, $$4);
    }

    public void blitSprite(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5) {
        blitSprite($$0, $$1, $$2, $$3, $$4, $$5, -1);
    }

    public void blitSprite(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5, float $$6) {
        blitSprite($$0, $$1, $$2, $$3, $$4, $$5, ARGB.white($$6));
    }

    private static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite $$0) {
        return ((GuiMetadataSection) $$0.contents().getAdditionalMetadata(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT)).scaling();
    }

    public void blitSprite(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
        TextureAtlasSprite $$7 = this.guiSprites.getSprite($$1);
        GuiSpriteScaling $$8 = getSpriteScaling($$7);
        Objects.requireNonNull($$8);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), GuiSpriteScaling.Stretch.class, GuiSpriteScaling.Tile.class, GuiSpriteScaling.NineSlice.class).dynamicInvoker().invoke($$8, 0) /* invoke-custom */) {
            case 0:
                blitSprite($$0, $$7, $$2, $$3, $$4, $$5, $$6);
                break;
            case 1:
                GuiSpriteScaling.Tile $$10 = (GuiSpriteScaling.Tile) $$8;
                blitTiledSprite($$0, $$7, $$2, $$3, $$4, $$5, 0, 0, $$10.width(), $$10.height(), $$10.width(), $$10.height(), $$6);
                break;
            case 2:
                GuiSpriteScaling.NineSlice $$11 = (GuiSpriteScaling.NineSlice) $$8;
                blitNineSlicedSprite($$0, $$7, $$11, $$2, $$3, $$4, $$5, $$6);
                break;
        }
    }

    public void blitSprite(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9) {
        blitSprite($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, -1);
    }

    public void blitSprite(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10) {
        TextureAtlasSprite $$11 = this.guiSprites.getSprite($$1);
        GuiSpriteScaling $$12 = getSpriteScaling($$11);
        if ($$12 instanceof GuiSpriteScaling.Stretch) {
            blitSprite($$0, $$11, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10);
            return;
        }
        enableScissor($$6, $$7, $$6 + $$8, $$7 + $$9);
        blitSprite($$0, $$1, $$6 - $$4, $$7 - $$5, $$2, $$3, $$10);
        disableScissor();
    }

    public void blitSprite(RenderPipeline $$0, TextureAtlasSprite $$1, int $$2, int $$3, int $$4, int $$5) {
        blitSprite($$0, $$1, $$2, $$3, $$4, $$5, -1);
    }

    public void blitSprite(RenderPipeline $$0, TextureAtlasSprite $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
        if ($$4 == 0 || $$5 == 0) {
            return;
        }
        innerBlit($$0, $$1.atlasLocation(), $$2, $$2 + $$4, $$3, $$3 + $$5, $$1.getU0(), $$1.getU1(), $$1.getV0(), $$1.getV1(), $$6);
    }

    private void blitSprite(RenderPipeline $$0, TextureAtlasSprite $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10) {
        if ($$8 == 0 || $$9 == 0) {
            return;
        }
        innerBlit($$0, $$1.atlasLocation(), $$6, $$6 + $$8, $$7, $$7 + $$9, $$1.getU($$4 / $$2), $$1.getU(($$4 + $$8) / $$2), $$1.getV($$5 / $$3), $$1.getV(($$5 + $$9) / $$3), $$10);
    }

    private void blitNineSlicedSprite(RenderPipeline $$0, TextureAtlasSprite $$1, GuiSpriteScaling.NineSlice $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
        GuiSpriteScaling.NineSlice.Border $$8 = $$2.border();
        int $$9 = Math.min($$8.left(), $$5 / 2);
        int $$10 = Math.min($$8.right(), $$5 / 2);
        int $$11 = Math.min($$8.top(), $$6 / 2);
        int $$12 = Math.min($$8.bottom(), $$6 / 2);
        if ($$5 == $$2.width() && $$6 == $$2.height()) {
            blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, 0, $$3, $$4, $$5, $$6, $$7);
            return;
        }
        if ($$6 == $$2.height()) {
            blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, 0, $$3, $$4, $$9, $$6, $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, $$3 + $$9, $$4, ($$5 - $$10) - $$9, $$6, $$9, 0, ($$2.width() - $$10) - $$9, $$2.height(), $$2.width(), $$2.height(), $$7);
            blitSprite($$0, $$1, $$2.width(), $$2.height(), $$2.width() - $$10, 0, ($$3 + $$5) - $$10, $$4, $$10, $$6, $$7);
        } else {
            if ($$5 == $$2.width()) {
                blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, 0, $$3, $$4, $$5, $$11, $$7);
                blitNineSliceInnerSegment($$0, $$2, $$1, $$3, $$4 + $$11, $$5, ($$6 - $$12) - $$11, 0, $$11, $$2.width(), ($$2.height() - $$12) - $$11, $$2.width(), $$2.height(), $$7);
                blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, $$2.height() - $$12, $$3, ($$4 + $$6) - $$12, $$5, $$12, $$7);
                return;
            }
            blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, 0, $$3, $$4, $$9, $$11, $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, $$3 + $$9, $$4, ($$5 - $$10) - $$9, $$11, $$9, 0, ($$2.width() - $$10) - $$9, $$11, $$2.width(), $$2.height(), $$7);
            blitSprite($$0, $$1, $$2.width(), $$2.height(), $$2.width() - $$10, 0, ($$3 + $$5) - $$10, $$4, $$10, $$11, $$7);
            blitSprite($$0, $$1, $$2.width(), $$2.height(), 0, $$2.height() - $$12, $$3, ($$4 + $$6) - $$12, $$9, $$12, $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, $$3 + $$9, ($$4 + $$6) - $$12, ($$5 - $$10) - $$9, $$12, $$9, $$2.height() - $$12, ($$2.width() - $$10) - $$9, $$12, $$2.width(), $$2.height(), $$7);
            blitSprite($$0, $$1, $$2.width(), $$2.height(), $$2.width() - $$10, $$2.height() - $$12, ($$3 + $$5) - $$10, ($$4 + $$6) - $$12, $$10, $$12, $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, $$3, $$4 + $$11, $$9, ($$6 - $$12) - $$11, 0, $$11, $$9, ($$2.height() - $$12) - $$11, $$2.width(), $$2.height(), $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, $$3 + $$9, $$4 + $$11, ($$5 - $$10) - $$9, ($$6 - $$12) - $$11, $$9, $$11, ($$2.width() - $$10) - $$9, ($$2.height() - $$12) - $$11, $$2.width(), $$2.height(), $$7);
            blitNineSliceInnerSegment($$0, $$2, $$1, ($$3 + $$5) - $$10, $$4 + $$11, $$10, ($$6 - $$12) - $$11, $$2.width() - $$10, $$11, $$10, ($$2.height() - $$12) - $$11, $$2.width(), $$2.height(), $$7);
        }
    }

    private void blitNineSliceInnerSegment(RenderPipeline $$0, GuiSpriteScaling.NineSlice $$1, TextureAtlasSprite $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11, int $$12, int $$13) {
        if ($$5 <= 0 || $$6 <= 0) {
            return;
        }
        if ($$1.stretchInner()) {
            innerBlit($$0, $$2.atlasLocation(), $$3, $$3 + $$5, $$4, $$4 + $$6, $$2.getU($$7 / $$11), $$2.getU(($$7 + $$9) / $$11), $$2.getV($$8 / $$12), $$2.getV(($$8 + $$10) / $$12), $$13);
        } else {
            blitTiledSprite($$0, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
        }
    }

    private void blitTiledSprite(RenderPipeline $$0, TextureAtlasSprite $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11, int $$12) {
        if ($$4 <= 0 || $$5 <= 0) {
            return;
        }
        if ($$8 <= 0 || $$9 <= 0) {
            throw new IllegalArgumentException("Tile size must be positive, got " + $$8 + "x" + $$9);
        }
        AbstractTexture $$13 = this.minecraft.getTextureManager().getTexture($$1.atlasLocation());
        GpuTextureView $$14 = $$13.getTextureView();
        submitTiledBlit($$0, $$14, $$13.getSampler(), $$8, $$9, $$2, $$3, $$2 + $$4, $$3 + $$5, $$1.getU($$6 / $$10), $$1.getU(($$6 + $$8) / $$10), $$1.getV($$7 / $$11), $$1.getV(($$7 + $$9) / $$11), $$12);
    }

    public void blit(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9, int $$10) {
        blit($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$6, $$7, $$8, $$9, $$10);
    }

    public void blit(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9) {
        blit($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$6, $$7, $$8, $$9);
    }

    public void blit(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11) {
        blit($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, -1);
    }

    public void blit(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11, int $$12) {
        innerBlit($$0, $$1, $$2, $$2 + $$6, $$3, $$3 + $$7, ($$4 + 0.0f) / $$10, ($$4 + $$8) / $$10, ($$5 + 0.0f) / $$11, ($$5 + $$9) / $$11, $$12);
    }

    public void blit(Identifier $$0, int $$1, int $$2, int $$3, int $$4, float $$5, float $$6, float $$7, float $$8) {
        innerBlit(RenderPipelines.GUI_TEXTURED, $$0, $$1, $$3, $$2, $$4, $$5, $$6, $$7, $$8, -1);
    }

    private void innerBlit(RenderPipeline $$0, Identifier $$1, int $$2, int $$3, int $$4, int $$5, float $$6, float $$7, float $$8, float $$9, int $$10) {
        AbstractTexture $$11 = this.minecraft.getTextureManager().getTexture($$1);
        submitBlit($$0, $$11.getTextureView(), $$11.getSampler(), $$2, $$4, $$3, $$5, $$6, $$7, $$8, $$9, $$10);
    }

    private void submitBlit(RenderPipeline $$0, GpuTextureView $$1, GpuSampler $$2, int $$3, int $$4, int $$5, int $$6, float $$7, float $$8, float $$9, float $$10, int $$11) {
        this.guiRenderState.submitGuiElement(new BlitRenderState($$0, TextureSetup.singleTexture($$1, $$2), new Matrix3x2f(this.pose), $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, this.scissorStack.peek()));
    }

    private void submitTiledBlit(RenderPipeline $$0, GpuTextureView $$1, GpuSampler $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, float $$9, float $$10, float $$11, float $$12, int $$13) {
        this.guiRenderState.submitGuiElement(new TiledBlitRenderState($$0, TextureSetup.singleTexture($$1, $$2), new Matrix3x2f(this.pose), $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13, this.scissorStack.peek()));
    }

    public void renderItem(ItemStack $$0, int $$1, int $$2) {
        renderItem(this.minecraft.player, this.minecraft.level, $$0, $$1, $$2, 0);
    }

    public void renderItem(ItemStack $$0, int $$1, int $$2, int $$3) {
        renderItem(this.minecraft.player, this.minecraft.level, $$0, $$1, $$2, $$3);
    }

    public void renderFakeItem(ItemStack $$0, int $$1, int $$2) {
        renderFakeItem($$0, $$1, $$2, 0);
    }

    public void renderFakeItem(ItemStack $$0, int $$1, int $$2, int $$3) {
        renderItem(null, this.minecraft.level, $$0, $$1, $$2, $$3);
    }

    public void renderItem(LivingEntity $$0, ItemStack $$1, int $$2, int $$3, int $$4) {
        renderItem($$0, $$0.level(), $$1, $$2, $$3, $$4);
    }

    private void renderItem(LivingEntity $$0, Level $$1, ItemStack $$2, int $$3, int $$4, int $$5) {
        if ($$2.isEmpty()) {
            return;
        }
        TrackingItemStackRenderState $$6 = new TrackingItemStackRenderState();
        this.minecraft.getItemModelResolver().updateForTopItem($$6, $$2, ItemDisplayContext.GUI, $$1, $$0, $$5);
        try {
            this.guiRenderState.submitItem(new GuiItemRenderState($$2.getItem().getName().toString(), new Matrix3x2f(this.pose), $$6, $$3, $$4, this.scissorStack.peek()));
        } catch (Throwable $$7) {
            CrashReport $$8 = CrashReport.forThrowable($$7, "Rendering item");
            CrashReportCategory $$9 = $$8.addCategory("Item being rendered");
            $$9.setDetail("Item Type", () -> {
                return String.valueOf($$2.getItem());
            });
            $$9.setDetail("Item Components", () -> {
                return String.valueOf($$2.getComponents());
            });
            $$9.setDetail("Item Foil", () -> {
                return String.valueOf($$2.hasFoil());
            });
            throw new ReportedException($$8);
        }
    }

    public void renderItemDecorations(Font $$0, ItemStack $$1, int $$2, int $$3) {
        renderItemDecorations($$0, $$1, $$2, $$3, null);
    }

    public void renderItemDecorations(Font $$0, ItemStack $$1, int $$2, int $$3, String $$4) {
        if ($$1.isEmpty()) {
            return;
        }
        this.pose.pushMatrix();
        renderItemBar($$1, $$2, $$3);
        renderItemCooldown($$1, $$2, $$3);
        renderItemCount($$0, $$1, $$2, $$3, $$4);
        this.pose.popMatrix();
    }

    public void setTooltipForNextFrame(Component $$0, int $$1, int $$2) {
        setTooltipForNextFrame(List.of($$0.getVisualOrderText()), $$1, $$2);
    }

    public void setTooltipForNextFrame(List<FormattedCharSequence> $$0, int $$1, int $$2) {
        setTooltipForNextFrame(this.minecraft.font, $$0, DefaultTooltipPositioner.INSTANCE, $$1, $$2, false);
    }

    public void setTooltipForNextFrame(Font $$0, ItemStack $$1, int $$2, int $$3) {
        setTooltipForNextFrame($$0, Screen.getTooltipFromItem(this.minecraft, $$1), $$1.getTooltipImage(), $$2, $$3, (Identifier) $$1.get(DataComponents.TOOLTIP_STYLE));
    }

    public void setTooltipForNextFrame(Font $$0, List<Component> $$1, Optional<TooltipComponent> $$2, int $$3, int $$4) {
        setTooltipForNextFrame($$0, $$1, $$2, $$3, $$4, (Identifier) null);
    }

    public void setTooltipForNextFrame(Font $$0, List<Component> $$1, Optional<TooltipComponent> $$2, int $$3, int $$4, Identifier $$5) {
        List<ClientTooltipComponent> $$6 = (List) $$1.stream().map((v0) -> {
            return v0.getVisualOrderText();
        }).map(ClientTooltipComponent::create).collect(Util.toMutableList());
        $$2.ifPresent($$12 -> {
            $$6.add($$6.isEmpty() ? 0 : 1, ClientTooltipComponent.create($$12));
        });
        setTooltipForNextFrameInternal($$0, $$6, $$3, $$4, DefaultTooltipPositioner.INSTANCE, $$5, false);
    }

    public void setTooltipForNextFrame(Font $$0, Component $$1, int $$2, int $$3) {
        setTooltipForNextFrame($$0, $$1, $$2, $$3, (Identifier) null);
    }

    public void setTooltipForNextFrame(Font $$0, Component $$1, int $$2, int $$3, Identifier $$4) {
        setTooltipForNextFrame($$0, List.of($$1.getVisualOrderText()), $$2, $$3, $$4);
    }

    public void setComponentTooltipForNextFrame(Font $$0, List<Component> $$1, int $$2, int $$3) {
        setComponentTooltipForNextFrame($$0, $$1, $$2, $$3, null);
    }

    public void setComponentTooltipForNextFrame(Font $$0, List<Component> $$1, int $$2, int $$3, Identifier $$4) {
        setTooltipForNextFrameInternal($$0, $$1.stream().map((v0) -> {
            return v0.getVisualOrderText();
        }).map(ClientTooltipComponent::create).toList(), $$2, $$3, DefaultTooltipPositioner.INSTANCE, $$4, false);
    }

    public void setTooltipForNextFrame(Font $$0, List<? extends FormattedCharSequence> $$1, int $$2, int $$3) {
        setTooltipForNextFrame($$0, $$1, $$2, $$3, (Identifier) null);
    }

    public void setTooltipForNextFrame(Font $$0, List<? extends FormattedCharSequence> $$1, int $$2, int $$3, Identifier $$4) {
        setTooltipForNextFrameInternal($$0, (List) $$1.stream().map(ClientTooltipComponent::create).collect(Collectors.toList()), $$2, $$3, DefaultTooltipPositioner.INSTANCE, $$4, false);
    }

    public void setTooltipForNextFrame(Font $$0, List<FormattedCharSequence> $$1, ClientTooltipPositioner $$2, int $$3, int $$4, boolean $$5) {
        setTooltipForNextFrameInternal($$0, (List) $$1.stream().map(ClientTooltipComponent::create).collect(Collectors.toList()), $$3, $$4, $$2, null, $$5);
    }

    private void setTooltipForNextFrameInternal(Font $$0, List<ClientTooltipComponent> $$1, int $$2, int $$3, ClientTooltipPositioner $$4, Identifier $$5, boolean $$6) {
        if ($$1.isEmpty()) {
            return;
        }
        if (this.deferredTooltip == null || $$6) {
            this.deferredTooltip = () -> {
                renderTooltip($$0, $$1, $$2, $$3, $$4, $$5);
            };
        }
    }

    public void renderTooltip(Font $$0, List<ClientTooltipComponent> $$1, int $$2, int $$3, ClientTooltipPositioner $$4, Identifier $$5) {
        int $$6 = 0;
        int $$7 = $$1.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent $$8 : $$1) {
            int $$9 = $$8.getWidth($$0);
            if ($$9 > $$6) {
                $$6 = $$9;
            }
            $$7 += $$8.getHeight($$0);
        }
        int $$10 = $$6;
        int $$11 = $$7;
        Vector2ic $$12 = $$4.positionTooltip(guiWidth(), guiHeight(), $$2, $$3, $$10, $$11);
        int $$13 = $$12.x();
        int $$14 = $$12.y();
        this.pose.pushMatrix();
        TooltipRenderUtil.renderTooltipBackground(this, $$13, $$14, $$10, $$11, $$5);
        int $$15 = $$14;
        int $$16 = 0;
        while ($$16 < $$1.size()) {
            ClientTooltipComponent $$17 = $$1.get($$16);
            $$17.renderText(this, $$0, $$13, $$15);
            $$15 += $$17.getHeight($$0) + ($$16 == 0 ? 2 : 0);
            $$16++;
        }
        int $$152 = $$14;
        int $$18 = 0;
        while ($$18 < $$1.size()) {
            ClientTooltipComponent $$19 = $$1.get($$18);
            $$19.renderImage($$0, $$13, $$152, $$10, $$11, this);
            $$152 += $$19.getHeight($$0) + ($$18 == 0 ? 2 : 0);
            $$18++;
        }
        this.pose.popMatrix();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void renderDeferredElements() throws MatchException {
        if (this.hoveredTextStyle != null) {
            renderComponentHoverEffect(this.minecraft.font, this.hoveredTextStyle, this.mouseX, this.mouseY);
        }
        if (this.clickableTextStyle != null && this.clickableTextStyle.getClickEvent() != null) {
            requestCursor(CursorTypes.POINTING_HAND);
        }
        if (this.deferredTooltip != null) {
            nextStratum();
            this.deferredTooltip.run();
            this.deferredTooltip = null;
        }
    }

    private void renderItemBar(ItemStack $$0, int $$1, int $$2) {
        if ($$0.isBarVisible()) {
            int $$3 = $$1 + 2;
            int $$4 = $$2 + 13;
            fill(RenderPipelines.GUI, $$3, $$4, $$3 + 13, $$4 + 2, -16777216);
            fill(RenderPipelines.GUI, $$3, $$4, $$3 + $$0.getBarWidth(), $$4 + 1, ARGB.opaque($$0.getBarColor()));
        }
    }

    private void renderItemCount(Font $$0, ItemStack $$1, int $$2, int $$3, String $$4) {
        if ($$1.getCount() != 1 || $$4 != null) {
            String $$5 = $$4 == null ? String.valueOf($$1.getCount()) : $$4;
            drawString($$0, $$5, (($$2 + 19) - 2) - $$0.width($$5), $$3 + 6 + 3, -1, true);
        }
    }

    private void renderItemCooldown(ItemStack $$0, int $$1, int $$2) {
        LocalPlayer $$3 = this.minecraft.player;
        float $$4 = $$3 == null ? 0.0f : $$3.getCooldowns().getCooldownPercent($$0, this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(true));
        if ($$4 > 0.0f) {
            int $$5 = $$2 + Mth.floor(16.0f * (1.0f - $$4));
            int $$6 = $$5 + Mth.ceil(16.0f * $$4);
            fill(RenderPipelines.GUI, $$1, $$5, $$1 + 16, $$6, Integer.MAX_VALUE);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void renderComponentHoverEffect(Font $$0, Style $$1, int $$2, int $$3) throws MatchException {
        if ($$1 != null && $$1.getHoverEvent() != null) {
            HoverEvent hoverEvent = $$1.getHoverEvent();
            Objects.requireNonNull(hoverEvent);
            try {
                switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), HoverEvent.ShowItem.class, HoverEvent.ShowEntity.class, HoverEvent.ShowText.class).dynamicInvoker().invoke(hoverEvent, 0) /* invoke-custom */) {
                    case 0:
                        ItemStack $$4 = ((HoverEvent.ShowItem) hoverEvent).item();
                        setTooltipForNextFrame($$0, $$4, $$2, $$3);
                        break;
                    case 1:
                        HoverEvent.EntityTooltipInfo $$5 = ((HoverEvent.ShowEntity) hoverEvent).entity();
                        if (this.minecraft.options.advancedItemTooltips) {
                            setComponentTooltipForNextFrame($$0, $$5.getTooltipLines(), $$2, $$3);
                        }
                        break;
                    case 2:
                        Component $$6 = ((HoverEvent.ShowText) hoverEvent).value();
                        setTooltipForNextFrame($$0, $$0.split($$6, Math.max(guiWidth() / 2, 200)), $$2, $$3);
                        break;
                    default:
                        return;
                }
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
    }

    public void submitMapRenderState(MapRenderState $$0) {
        Minecraft $$1 = Minecraft.getInstance();
        TextureManager $$2 = $$1.getTextureManager();
        AbstractTexture $$3 = $$2.getTexture($$0.texture);
        submitBlit(RenderPipelines.GUI_TEXTURED, $$3.getTextureView(), $$3.getSampler(), 0, 0, 128, 128, 0.0f, 1.0f, 0.0f, 1.0f, -1);
        for (MapRenderState.MapDecorationRenderState $$4 : $$0.decorations) {
            if ($$4.renderOnFrame) {
                this.pose.pushMatrix();
                this.pose.translate(($$4.x / 2.0f) + 64.0f, ($$4.y / 2.0f) + 64.0f);
                this.pose.rotate(((0.017453292f * $$4.rot) * 360.0f) / 16.0f);
                this.pose.scale(4.0f, 4.0f);
                this.pose.translate(-0.125f, 0.125f);
                TextureAtlasSprite $$5 = $$4.atlasSprite;
                if ($$5 != null) {
                    AbstractTexture $$6 = $$2.getTexture($$5.atlasLocation());
                    submitBlit(RenderPipelines.GUI_TEXTURED, $$6.getTextureView(), $$6.getSampler(), -1, -1, 1, 1, $$5.getU0(), $$5.getU1(), $$5.getV1(), $$5.getV0(), -1);
                }
                this.pose.popMatrix();
                if ($$4.name != null) {
                    Font $$7 = $$1.font;
                    float $$8 = $$7.width($$4.name);
                    Objects.requireNonNull($$7);
                    float $$9 = Mth.clamp(25.0f / $$8, 0.0f, 6.0f / 9.0f);
                    this.pose.pushMatrix();
                    this.pose.translate((($$4.x / 2.0f) + 64.0f) - (($$8 * $$9) / 2.0f), ($$4.y / 2.0f) + 64.0f + 4.0f);
                    this.pose.scale($$9, $$9);
                    this.guiRenderState.submitText(new GuiTextRenderState($$7, $$4.name.getVisualOrderText(), new Matrix3x2f(this.pose), 0, 0, -1, ChunkSkyLightSources.NEGATIVE_INFINITY, false, false, this.scissorStack.peek()));
                    this.pose.popMatrix();
                }
            }
        }
    }

    public void submitEntityRenderState(EntityRenderState $$0, float $$1, Vector3f $$2, Quaternionf $$3, Quaternionf $$4, int $$5, int $$6, int $$7, int $$8) {
        this.guiRenderState.submitPicturesInPictureState(new GuiEntityRenderState($$0, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$1, this.scissorStack.peek()));
    }

    public void submitSkinRenderState(PlayerModel $$0, Identifier $$1, float $$2, float $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9) {
        this.guiRenderState.submitPicturesInPictureState(new GuiSkinRenderState($$0, $$1, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$2, this.scissorStack.peek()));
    }

    public void submitBookModelRenderState(BookModel $$0, Identifier $$1, float $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
        this.guiRenderState.submitPicturesInPictureState(new GuiBookModelRenderState($$0, $$1, $$3, $$4, $$5, $$6, $$7, $$8, $$2, this.scissorStack.peek()));
    }

    public void submitBannerPatternRenderState(BannerFlagModel $$0, DyeColor $$1, BannerPatternLayers $$2, int $$3, int $$4, int $$5, int $$6) {
        this.guiRenderState.submitPicturesInPictureState(new GuiBannerResultRenderState($$0, $$1, $$2, $$3, $$4, $$5, $$6, this.scissorStack.peek()));
    }

    public void submitSignRenderState(Model.Simple $$0, float $$1, WoodType $$2, int $$3, int $$4, int $$5, int $$6) {
        this.guiRenderState.submitPicturesInPictureState(new GuiSignRenderState($$0, $$2, $$3, $$4, $$5, $$6, $$1, this.scissorStack.peek()));
    }

    public void submitProfilerChartRenderState(List<ResultField> $$0, int $$1, int $$2, int $$3, int $$4) {
        this.guiRenderState.submitPicturesInPictureState(new GuiProfilerChartRenderState($$0, $$1, $$2, $$3, $$4, this.scissorStack.peek()));
    }

    public TextureAtlasSprite getSprite(Material $$0) {
        return this.materials.get($$0);
    }

    public ActiveTextCollector textRendererForWidget(AbstractWidget $$0, HoveredTextEffects $$1) {
        return new RenderingTextCollector(createDefaultTextParameters($$0.getAlpha()), $$1, null);
    }

    public ActiveTextCollector textRenderer() {
        return textRenderer(HoveredTextEffects.TOOLTIP_ONLY);
    }

    public ActiveTextCollector textRenderer(HoveredTextEffects $$0) {
        return textRenderer($$0, null);
    }

    public ActiveTextCollector textRenderer(HoveredTextEffects $$0, Consumer<Style> $$1) {
        return new RenderingTextCollector(createDefaultTextParameters(1.0f), $$0, $$1);
    }

    private ActiveTextCollector.Parameters createDefaultTextParameters(float $$0) {
        return new ActiveTextCollector.Parameters(new Matrix3x2f(this.pose), $$0, this.scissorStack.peek());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/GuiGraphics$ScissorStack.class */
    static class ScissorStack {
        private final Deque<ScreenRectangle> stack = new ArrayDeque();

        ScissorStack() {
        }

        public ScreenRectangle push(ScreenRectangle $$0) {
            ScreenRectangle $$1 = this.stack.peekLast();
            if ($$1 != null) {
                ScreenRectangle $$2 = (ScreenRectangle) Objects.requireNonNullElse($$0.intersection($$1), ScreenRectangle.empty());
                this.stack.addLast($$2);
                return $$2;
            }
            this.stack.addLast($$0);
            return $$0;
        }

        public ScreenRectangle pop() {
            if (this.stack.isEmpty()) {
                throw new IllegalStateException("Scissor stack underflow");
            }
            this.stack.removeLast();
            return this.stack.peekLast();
        }

        public ScreenRectangle peek() {
            return this.stack.peekLast();
        }

        public boolean containsPoint(int $$0, int $$1) {
            if (this.stack.isEmpty()) {
                return true;
            }
            return this.stack.peek().containsPoint($$0, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/GuiGraphics$HoveredTextEffects.class */
    public enum HoveredTextEffects {
        NONE(false, false),
        TOOLTIP_ONLY(true, false),
        TOOLTIP_AND_CURSOR(true, true);

        public final boolean allowTooltip;
        public final boolean allowCursorChanges;

        HoveredTextEffects(boolean $$0, boolean $$1) {
            this.allowTooltip = $$0;
            this.allowCursorChanges = $$1;
        }

        public static HoveredTextEffects notClickable(boolean $$0) {
            return $$0 ? TOOLTIP_ONLY : NONE;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/GuiGraphics$RenderingTextCollector.class */
    class RenderingTextCollector implements ActiveTextCollector, Consumer<Style> {
        private ActiveTextCollector.Parameters defaultParameters;
        private final HoveredTextEffects hoveredTextEffects;
        private final Consumer<Style> additionalConsumer;

        RenderingTextCollector(ActiveTextCollector.Parameters $$0, HoveredTextEffects $$1, Consumer<Style> $$2) {
            this.defaultParameters = $$0;
            this.hoveredTextEffects = $$1;
            this.additionalConsumer = $$2;
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public ActiveTextCollector.Parameters defaultParameters() {
            return this.defaultParameters;
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void defaultParameters(ActiveTextCollector.Parameters $$0) {
            this.defaultParameters = $$0;
        }

        @Override // java.util.function.Consumer
        public void accept(Style $$0) {
            if (this.hoveredTextEffects.allowTooltip && $$0.getHoverEvent() != null) {
                GuiGraphics.this.hoveredTextStyle = $$0;
            }
            if (this.hoveredTextEffects.allowCursorChanges && $$0.getClickEvent() != null) {
                GuiGraphics.this.clickableTextStyle = $$0;
            }
            if (this.additionalConsumer != null) {
                this.additionalConsumer.accept($$0);
            }
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void accept(TextAlignment $$0, int $$1, int $$2, ActiveTextCollector.Parameters $$3, FormattedCharSequence $$4) {
            boolean $$5 = this.hoveredTextEffects.allowCursorChanges || this.hoveredTextEffects.allowTooltip || this.additionalConsumer != null;
            int $$6 = $$0.calculateLeft($$1, GuiGraphics.this.minecraft.font, $$4);
            GuiTextRenderState $$7 = new GuiTextRenderState(GuiGraphics.this.minecraft.font, $$4, $$3.pose(), $$6, $$2, ARGB.white($$3.opacity()), 0, true, $$5, $$3.scissor());
            if (ARGB.as8BitChannel($$3.opacity()) != 0) {
                GuiGraphics.this.guiRenderState.submitText($$7);
            }
            if ($$5) {
                ActiveTextCollector.findElementUnderCursor($$7, GuiGraphics.this.mouseX, GuiGraphics.this.mouseY, this);
            }
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void acceptScrolling(Component $$0, int $$1, int $$2, int $$3, int $$4, int $$5, ActiveTextCollector.Parameters $$6) {
            int $$7 = GuiGraphics.this.minecraft.font.width($$0);
            Objects.requireNonNull(GuiGraphics.this.minecraft.font);
            defaultScrollingHelper($$0, $$1, $$2, $$3, $$4, $$5, $$7, 9, $$6);
        }
    }
}
