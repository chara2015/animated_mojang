package net.minecraft.client.gui.screens;

import com.mojang.authlib.minecraft.BanDetails;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.report.BanReason;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.CommonLinks;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/BanNoticeScreens.class */
public class BanNoticeScreens {
    private static final Component TEMPORARY_BAN_TITLE = Component.translatable("gui.banned.title.temporary").withStyle(ChatFormatting.BOLD);
    private static final Component PERMANENT_BAN_TITLE = Component.translatable("gui.banned.title.permanent").withStyle(ChatFormatting.BOLD);
    public static final Component NAME_BAN_TITLE = Component.translatable("gui.banned.name.title").withStyle(ChatFormatting.BOLD);
    private static final Component SKIN_BAN_TITLE = Component.translatable("gui.banned.skin.title").withStyle(ChatFormatting.BOLD);
    private static final Component SKIN_BAN_DESCRIPTION = Component.translatable("gui.banned.skin.description", Component.translationArg(CommonLinks.SUSPENSION_HELP));

    public static ConfirmLinkScreen create(BooleanConsumer $$0, BanDetails $$1) {
        return new ConfirmLinkScreen($$0, getBannedTitle($$1), getBannedScreenText($$1), CommonLinks.SUSPENSION_HELP, CommonComponents.GUI_ACKNOWLEDGE, true);
    }

    public static ConfirmLinkScreen createSkinBan(Runnable $$0) {
        URI $$1 = CommonLinks.SUSPENSION_HELP;
        return new ConfirmLinkScreen($$2 -> {
            if ($$2) {
                Util.getPlatform().openUri($$1);
            }
            $$0.run();
        }, SKIN_BAN_TITLE, SKIN_BAN_DESCRIPTION, $$1, CommonComponents.GUI_ACKNOWLEDGE, true);
    }

    public static ConfirmLinkScreen createNameBan(String $$0, Runnable $$1) {
        URI $$2 = CommonLinks.SUSPENSION_HELP;
        return new ConfirmLinkScreen($$22 -> {
            if ($$22) {
                Util.getPlatform().openUri($$2);
            }
            $$1.run();
        }, NAME_BAN_TITLE, (Component) Component.translatable("gui.banned.name.description", Component.literal($$0).withStyle(ChatFormatting.YELLOW), Component.translationArg(CommonLinks.SUSPENSION_HELP)), $$2, CommonComponents.GUI_ACKNOWLEDGE, true);
    }

    private static Component getBannedTitle(BanDetails $$0) {
        return isTemporaryBan($$0) ? TEMPORARY_BAN_TITLE : PERMANENT_BAN_TITLE;
    }

    private static Component getBannedScreenText(BanDetails $$0) {
        return Component.translatable("gui.banned.description", getBanReasonText($$0), getBanStatusText($$0), Component.translationArg(CommonLinks.SUSPENSION_HELP));
    }

    private static Component getBanReasonText(BanDetails $$0) {
        Component $$7;
        String $$1 = $$0.reason();
        String $$2 = $$0.reasonMessage();
        if (StringUtils.isNumeric($$1)) {
            int $$3 = Integer.parseInt($$1);
            BanReason $$4 = BanReason.byId($$3);
            if ($$4 != null) {
                $$7 = ComponentUtils.mergeStyles($$4.title(), Style.EMPTY.withBold(true));
            } else if ($$2 != null) {
                $$7 = Component.translatable("gui.banned.description.reason_id_message", Integer.valueOf($$3), $$2).withStyle(ChatFormatting.BOLD);
            } else {
                $$7 = Component.translatable("gui.banned.description.reason_id", Integer.valueOf($$3)).withStyle(ChatFormatting.BOLD);
            }
            return Component.translatable("gui.banned.description.reason", $$7);
        }
        return Component.translatable("gui.banned.description.unknownreason");
    }

    private static Component getBanStatusText(BanDetails $$0) {
        if (isTemporaryBan($$0)) {
            Component $$1 = getBanDurationText($$0);
            return Component.translatable("gui.banned.description.temporary", Component.translatable("gui.banned.description.temporary.duration", $$1).withStyle(ChatFormatting.BOLD));
        }
        return Component.translatable("gui.banned.description.permanent").withStyle(ChatFormatting.BOLD);
    }

    private static Component getBanDurationText(BanDetails $$0) {
        Duration $$1 = Duration.between(Instant.now(), $$0.expires());
        long $$2 = $$1.toHours();
        if ($$2 > 72) {
            return CommonComponents.days($$1.toDays());
        }
        if ($$2 < 1) {
            return CommonComponents.minutes($$1.toMinutes());
        }
        return CommonComponents.hours($$1.toHours());
    }

    private static boolean isTemporaryBan(BanDetails $$0) {
        return $$0.expires() != null;
    }
}
