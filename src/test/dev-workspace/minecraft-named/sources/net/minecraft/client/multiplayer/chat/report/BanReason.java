package net.minecraft.client.multiplayer.chat.report;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/BanReason.class */
public enum BanReason {
    GENERIC_VIOLATION("generic_violation"),
    FALSE_REPORTING("false_reporting"),
    HATE_SPEECH("hate_speech"),
    HATE_TERRORISM_NOTORIOUS_FIGURE("hate_terrorism_notorious_figure"),
    HARASSMENT_OR_BULLYING("harassment_or_bullying"),
    DEFAMATION_IMPERSONATION_FALSE_INFORMATION("defamation_impersonation_false_information"),
    DRUGS("drugs"),
    FRAUD("fraud"),
    SPAM_OR_ADVERTISING("spam_or_advertising"),
    NUDITY_OR_PORNOGRAPHY("nudity_or_pornography"),
    SEXUALLY_INAPPROPRIATE("sexually_inappropriate"),
    EXTREME_VIOLENCE_OR_GORE("extreme_violence_or_gore"),
    IMMINENT_HARM_TO_PERSON_OR_PROPERTY("imminent_harm_to_person_or_property");

    private final Component title;

    BanReason(String $$0) {
        this.title = Component.translatable("gui.banned.reason." + $$0);
    }

    public Component title() {
        return this.title;
    }

    public static BanReason byId(int $$0) {
        switch ($$0) {
            case 2:
                return FALSE_REPORTING;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 20:
            case EntityEvent.REDUCED_DEBUG_INFO /* 22 */:
            case 24:
            case 26:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            default:
                return null;
            case 5:
                return HATE_SPEECH;
            case 16:
            case 25:
                return HATE_TERRORISM_NOTORIOUS_FIGURE;
            case EntityEvent.FIREWORKS_EXPLODE /* 17 */:
            case EntityEvent.SQUID_ANIM_SYNCH /* 19 */:
            case EntityEvent.FULL_DEBUG_INFO /* 23 */:
            case 31:
                return GENERIC_VIOLATION;
            case 21:
                return HARASSMENT_OR_BULLYING;
            case 27:
                return DEFAMATION_IMPERSONATION_FALSE_INFORMATION;
            case 28:
                return DRUGS;
            case OceanMonumentPieces.MonumentBuilding.BIOME_RANGE_CHECK /* 29 */:
                return FRAUD;
            case 30:
                return SPAM_OR_ADVERTISING;
            case 32:
                return NUDITY_OR_PORNOGRAPHY;
            case 33:
            case 35:
            case 36:
                return SEXUALLY_INAPPROPRIATE;
            case EntityEvent.STOP_OFFER_FLOWER /* 34 */:
                return EXTREME_VIOLENCE_OR_GORE;
            case 53:
                return IMMINENT_HARM_TO_PERSON_OR_PROPERTY;
        }
    }
}
