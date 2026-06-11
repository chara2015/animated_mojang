package net.minecraft.client.gui.screens.reporting;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.CommonLayouts;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.multiplayer.chat.report.NameReport;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/reporting/NameReportScreen.class */
public class NameReportScreen extends AbstractReportScreen<NameReport.Builder> {
    private static final Component TITLE = Component.translatable("gui.abuseReport.name.title");
    private static final Component COMMENT_BOX_LABEL = Component.translatable("gui.abuseReport.name.comment_box_label");
    private MultiLineEditBox commentBox;

    private NameReportScreen(Screen $$0, ReportingContext $$1, NameReport.Builder $$2) {
        super(TITLE, $$0, $$1, $$2);
    }

    public NameReportScreen(Screen $$0, ReportingContext $$1, UUID $$2, String $$3) {
        this($$0, $$1, new NameReport.Builder($$2, $$3, $$1.sender().reportLimits()));
    }

    public NameReportScreen(Screen $$0, ReportingContext $$1, NameReport $$2) {
        this($$0, $$1, new NameReport.Builder($$2, $$1.sender().reportLimits()));
    }

    @Override // net.minecraft.client.gui.screens.reporting.AbstractReportScreen
    protected void addContent() {
        Component $$0 = Component.literal(((NameReport.Builder) this.reportBuilder).report().getReportedName()).withStyle(ChatFormatting.YELLOW);
        this.layout.addChild(new StringWidget(Component.translatable("gui.abuseReport.name.reporting", $$0), this.font), $$02 -> {
            $$02.alignHorizontallyCenter().padding(0, 8);
        });
        Objects.requireNonNull(this.font);
        this.commentBox = createCommentBox(InputConstants.KEY_CAPSLOCK, 9 * 8, $$03 -> {
            ((NameReport.Builder) this.reportBuilder).setComments($$03);
            onReportChanged();
        });
        this.layout.addChild(CommonLayouts.labeledElement(this.font, this.commentBox, COMMENT_BOX_LABEL, $$04 -> {
            $$04.paddingBottom(12);
        }));
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public boolean mouseReleased(MouseButtonEvent $$0) {
        if (super.mouseReleased($$0)) {
            return true;
        }
        if (this.commentBox != null) {
            return this.commentBox.mouseReleased($$0);
        }
        return false;
    }
}
