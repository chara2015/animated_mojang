package com.mojang.realmsclient.dto;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.RealmsRegion;
import com.mojang.realmsclient.dto.RegionSelectionPreference;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/RegionSelectionPreferenceDto.class */
public class RegionSelectionPreferenceDto implements ReflectionBasedSerialization {
    public static final RegionSelectionPreferenceDto DEFAULT = new RegionSelectionPreferenceDto(RegionSelectionPreference.AUTOMATIC_OWNER, null);

    @SerializedName("regionSelectionPreference")
    @JsonAdapter(RegionSelectionPreference.RegionSelectionPreferenceJsonAdapter.class)
    public final RegionSelectionPreference regionSelectionPreference;

    @SerializedName("preferredRegion")
    @JsonAdapter(RealmsRegion.RealmsRegionJsonAdapter.class)
    public RealmsRegion preferredRegion;

    public RegionSelectionPreferenceDto(RegionSelectionPreference $$0, RealmsRegion $$1) {
        this.regionSelectionPreference = $$0;
        this.preferredRegion = $$1;
    }

    public RegionSelectionPreferenceDto copy() {
        return new RegionSelectionPreferenceDto(this.regionSelectionPreference, this.preferredRegion);
    }
}
