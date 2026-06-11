package com.mojang.realmsclient.dto;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsRegion.class */
public enum RealmsRegion {
    AUSTRALIA_EAST("AustraliaEast", "realms.configuration.region.australia_east"),
    AUSTRALIA_SOUTHEAST("AustraliaSoutheast", "realms.configuration.region.australia_southeast"),
    BRAZIL_SOUTH("BrazilSouth", "realms.configuration.region.brazil_south"),
    CENTRAL_INDIA("CentralIndia", "realms.configuration.region.central_india"),
    CENTRAL_US("CentralUs", "realms.configuration.region.central_us"),
    EAST_ASIA("EastAsia", "realms.configuration.region.east_asia"),
    EAST_US("EastUs", "realms.configuration.region.east_us"),
    EAST_US_2("EastUs2", "realms.configuration.region.east_us_2"),
    FRANCE_CENTRAL("FranceCentral", "realms.configuration.region.france_central"),
    JAPAN_EAST("JapanEast", "realms.configuration.region.japan_east"),
    JAPAN_WEST("JapanWest", "realms.configuration.region.japan_west"),
    KOREA_CENTRAL("KoreaCentral", "realms.configuration.region.korea_central"),
    NORTH_CENTRAL_US("NorthCentralUs", "realms.configuration.region.north_central_us"),
    NORTH_EUROPE("NorthEurope", "realms.configuration.region.north_europe"),
    SOUTH_CENTRAL_US("SouthCentralUs", "realms.configuration.region.south_central_us"),
    SOUTHEAST_ASIA("SoutheastAsia", "realms.configuration.region.southeast_asia"),
    SWEDEN_CENTRAL("SwedenCentral", "realms.configuration.region.sweden_central"),
    UAE_NORTH("UAENorth", "realms.configuration.region.uae_north"),
    UK_SOUTH("UKSouth", "realms.configuration.region.uk_south"),
    WEST_CENTRAL_US("WestCentralUs", "realms.configuration.region.west_central_us"),
    WEST_EUROPE("WestEurope", "realms.configuration.region.west_europe"),
    WEST_US("WestUs", "realms.configuration.region.west_us"),
    WEST_US_2("WestUs2", "realms.configuration.region.west_us_2"),
    INVALID_REGION("invalid", "");

    public final String nameId;
    public final String translationKey;

    RealmsRegion(String $$0, String $$1) {
        this.nameId = $$0;
        this.translationKey = $$1;
    }

    public static RealmsRegion findByNameId(String $$0) {
        for (RealmsRegion $$1 : values()) {
            if ($$1.nameId.equals($$0)) {
                return $$1;
            }
        }
        return null;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RealmsRegion$RealmsRegionJsonAdapter.class */
    public static class RealmsRegionJsonAdapter extends TypeAdapter<RealmsRegion> {
        private static final Logger LOGGER = LogUtils.getLogger();

        public void write(JsonWriter $$0, RealmsRegion $$1) throws IOException {
            $$0.value($$1.nameId);
        }

        /* JADX INFO: renamed from: read, reason: merged with bridge method [inline-methods] */
        public RealmsRegion m113read(JsonReader $$0) throws IOException {
            String $$1 = $$0.nextString();
            RealmsRegion $$2 = RealmsRegion.findByNameId($$1);
            if ($$2 == null) {
                LOGGER.warn("Unsupported RealmsRegion {}", $$1);
                return RealmsRegion.INVALID_REGION;
            }
            return $$2;
        }
    }
}
