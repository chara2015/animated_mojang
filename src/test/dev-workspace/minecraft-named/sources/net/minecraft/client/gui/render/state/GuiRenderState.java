package net.minecraft.client.gui.render.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix3x2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/GuiRenderState.class */
public class GuiRenderState {
    private static final int DEBUG_RECTANGLE_COLOR = 2000962815;
    private Node current;
    private ScreenRectangle lastElementBounds;
    private final List<Node> strata = new ArrayList();
    private int firstStratumAfterBlur = Integer.MAX_VALUE;
    private final Set<Object> itemModelIdentities = new HashSet();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/GuiRenderState$TraverseRange.class */
    public enum TraverseRange {
        ALL,
        BEFORE_BLUR,
        AFTER_BLUR
    }

    public GuiRenderState() {
        nextStratum();
    }

    public void nextStratum() {
        this.current = new Node(null);
        this.strata.add(this.current);
    }

    public void blurBeforeThisStratum() {
        if (this.firstStratumAfterBlur != Integer.MAX_VALUE) {
            throw new IllegalStateException("Can only blur once per frame");
        }
        this.firstStratumAfterBlur = this.strata.size() - 1;
    }

    public void up() {
        if (this.current.up == null) {
            this.current.up = new Node(this.current);
        }
        this.current = this.current.up;
    }

    public void submitItem(GuiItemRenderState $$0) {
        if (!findAppropriateNode($$0)) {
            return;
        }
        this.itemModelIdentities.add($$0.itemStackRenderState().getModelIdentity());
        this.current.submitItem($$0);
        sumbitDebugRectangleIfEnabled($$0.bounds());
    }

    public void submitText(GuiTextRenderState $$0) {
        if (!findAppropriateNode($$0)) {
            return;
        }
        this.current.submitText($$0);
        sumbitDebugRectangleIfEnabled($$0.bounds());
    }

    public void submitPicturesInPictureState(PictureInPictureRenderState $$0) {
        if (!findAppropriateNode($$0)) {
            return;
        }
        this.current.submitPicturesInPictureState($$0);
        sumbitDebugRectangleIfEnabled($$0.bounds());
    }

    public void submitGuiElement(GuiElementRenderState $$0) {
        if (!findAppropriateNode($$0)) {
            return;
        }
        this.current.submitGuiElement($$0);
        sumbitDebugRectangleIfEnabled($$0.bounds());
    }

    private void sumbitDebugRectangleIfEnabled(ScreenRectangle $$0) {
        if (!SharedConstants.DEBUG_RENDER_UI_LAYERING_RECTANGLES || $$0 == null) {
            return;
        }
        up();
        this.current.submitGuiElement(new ColoredRectangleRenderState(RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(), 0, 0, 10000, 10000, DEBUG_RECTANGLE_COLOR, DEBUG_RECTANGLE_COLOR, $$0));
    }

    private boolean findAppropriateNode(ScreenArea $$0) {
        ScreenRectangle $$1 = $$0.bounds();
        if ($$1 == null) {
            return false;
        }
        if (this.lastElementBounds != null && this.lastElementBounds.encompasses($$1)) {
            up();
        } else {
            navigateToAboveHighestElementWithIntersectingBounds($$1);
        }
        this.lastElementBounds = $$1;
        return true;
    }

    private void navigateToAboveHighestElementWithIntersectingBounds(ScreenRectangle $$0) {
        Node $$1;
        Node node = (Node) this.strata.getLast();
        while (true) {
            $$1 = node;
            if ($$1.up == null) {
                break;
            } else {
                node = $$1.up;
            }
        }
        boolean $$2 = false;
        while (!$$2) {
            $$2 = hasIntersection($$0, $$1.elementStates) || hasIntersection($$0, $$1.itemStates) || hasIntersection($$0, $$1.textStates) || hasIntersection($$0, $$1.picturesInPictureStates);
            if ($$1.parent == null) {
                break;
            } else if (!$$2) {
                $$1 = $$1.parent;
            }
        }
        this.current = $$1;
        if ($$2) {
            up();
        }
    }

    private boolean hasIntersection(ScreenRectangle $$0, List<? extends ScreenArea> $$1) {
        if ($$1 != null) {
            for (ScreenArea $$2 : $$1) {
                ScreenRectangle $$3 = $$2.bounds();
                if ($$3 != null && $$3.intersects($$0)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void submitBlitToCurrentLayer(BlitRenderState $$0) {
        this.current.submitGuiElement($$0);
    }

    public void submitGlyphToCurrentLayer(GuiElementRenderState $$0) {
        this.current.submitGlyph($$0);
    }

    public Set<Object> getItemModelIdentities() {
        return this.itemModelIdentities;
    }

    public void forEachElement(Consumer<GuiElementRenderState> $$0, TraverseRange $$1) {
        traverse($$12 -> {
            if ($$12.elementStates == null && $$12.glyphStates == null) {
                return;
            }
            if ($$12.elementStates != null) {
                for (GuiElementRenderState $$2 : $$12.elementStates) {
                    $$0.accept($$2);
                }
            }
            if ($$12.glyphStates != null) {
                for (GuiElementRenderState $$3 : $$12.glyphStates) {
                    $$0.accept($$3);
                }
            }
        }, $$1);
    }

    public void forEachItem(Consumer<GuiItemRenderState> $$0) {
        Node $$1 = this.current;
        traverse($$12 -> {
            if ($$12.itemStates != null) {
                this.current = $$12;
                for (GuiItemRenderState $$2 : $$12.itemStates) {
                    $$0.accept($$2);
                }
            }
        }, TraverseRange.ALL);
        this.current = $$1;
    }

    public void forEachText(Consumer<GuiTextRenderState> $$0) {
        Node $$1 = this.current;
        traverse($$12 -> {
            if ($$12.textStates != null) {
                for (GuiTextRenderState $$2 : $$12.textStates) {
                    this.current = $$12;
                    $$0.accept($$2);
                }
            }
        }, TraverseRange.ALL);
        this.current = $$1;
    }

    public void forEachPictureInPicture(Consumer<PictureInPictureRenderState> $$0) {
        Node $$1 = this.current;
        traverse($$12 -> {
            if ($$12.picturesInPictureStates != null) {
                this.current = $$12;
                for (PictureInPictureRenderState $$2 : $$12.picturesInPictureStates) {
                    $$0.accept($$2);
                }
            }
        }, TraverseRange.ALL);
        this.current = $$1;
    }

    public void sortElements(Comparator<GuiElementRenderState> $$0) {
        traverse($$1 -> {
            if ($$1.elementStates != null) {
                if (SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER) {
                    Collections.shuffle($$1.elementStates);
                }
                $$1.elementStates.sort($$0);
            }
        }, TraverseRange.ALL);
    }

    private void traverse(Consumer<Node> $$0, TraverseRange $$1) {
        int $$2 = 0;
        int $$3 = this.strata.size();
        if ($$1 == TraverseRange.BEFORE_BLUR) {
            $$3 = Math.min(this.firstStratumAfterBlur, this.strata.size());
        } else if ($$1 == TraverseRange.AFTER_BLUR) {
            $$2 = this.firstStratumAfterBlur;
        }
        for (int $$4 = $$2; $$4 < $$3; $$4++) {
            Node $$5 = this.strata.get($$4);
            traverse($$5, $$0);
        }
    }

    private void traverse(Node $$0, Consumer<Node> $$1) {
        $$1.accept($$0);
        if ($$0.up != null) {
            traverse($$0.up, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/GuiRenderState$Node.class */
    static class Node {
        public final Node parent;
        public Node up;
        public List<GuiElementRenderState> elementStates;
        public List<GuiElementRenderState> glyphStates;
        public List<GuiItemRenderState> itemStates;
        public List<GuiTextRenderState> textStates;
        public List<PictureInPictureRenderState> picturesInPictureStates;

        Node(Node $$0) {
            this.parent = $$0;
        }

        public void submitItem(GuiItemRenderState $$0) {
            if (this.itemStates == null) {
                this.itemStates = new ArrayList();
            }
            this.itemStates.add($$0);
        }

        public void submitText(GuiTextRenderState $$0) {
            if (this.textStates == null) {
                this.textStates = new ArrayList();
            }
            this.textStates.add($$0);
        }

        public void submitPicturesInPictureState(PictureInPictureRenderState $$0) {
            if (this.picturesInPictureStates == null) {
                this.picturesInPictureStates = new ArrayList();
            }
            this.picturesInPictureStates.add($$0);
        }

        public void submitGuiElement(GuiElementRenderState $$0) {
            if (this.elementStates == null) {
                this.elementStates = new ArrayList();
            }
            this.elementStates.add($$0);
        }

        public void submitGlyph(GuiElementRenderState $$0) {
            if (this.glyphStates == null) {
                this.glyphStates = new ArrayList();
            }
            this.glyphStates.add($$0);
        }
    }

    public void reset() {
        this.itemModelIdentities.clear();
        this.strata.clear();
        this.firstStratumAfterBlur = Integer.MAX_VALUE;
        nextStratum();
    }
}
