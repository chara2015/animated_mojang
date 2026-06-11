package net.labymod.api.util;

import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CountryCode.class */
public enum CountryCode {
    AD("Andorra"),
    AE("United Arab Emirates"),
    AF("Afghanistan"),
    AG("Antigua and Barbuda"),
    AI("Anguilla"),
    AL("Albania"),
    AM("Armenia"),
    AO("Angola"),
    AQ("Antarctica"),
    AR("Argentina"),
    AS("American Samoa"),
    AT("Austria"),
    AU("Australia"),
    AW("Aruba"),
    AX("Åland Islands"),
    AZ("Azerbaijan"),
    BA("Bosnia and Herzegovina"),
    BB("Barbados"),
    BD("Bangladesh"),
    BE("Belgium"),
    BF("Burkina Faso"),
    BG("Bulgaria"),
    BH("Bahrain"),
    BI("Burundi"),
    BJ("Benin"),
    BL("Saint Barthélemy"),
    BM("Bermuda"),
    BN("Brunei Darussalam"),
    BO("Bolivia", "Plurinational State of Bolivia"),
    BQ("Bonaire, Sint Eustatius and Saba"),
    BR("Brazil"),
    BS("Bahamas"),
    BT("Bhutan"),
    BV("Bouvet Island"),
    BW("Botswana"),
    BY("Belarus"),
    BZ("Belize"),
    CA("Canada"),
    CC("Cocos Islands", "Cocos (Keeling) Islands"),
    CD("Democratic Republic of Congo", "the Democratic Republic of the Congo"),
    CF("Central African Republic"),
    CG("Congo"),
    CH("Switzerland"),
    CI("Côte d'Ivoire"),
    CK("Cook Islands"),
    CL("Chile"),
    CM("Cameroon"),
    CN("China"),
    CO("Colombia"),
    CR("Costa Rica"),
    CU("Cuba"),
    CV("Cabo Verde", "Cape Verde"),
    CW("Curaçao"),
    CX("Christmas Island"),
    CY("Cyprus"),
    CZ("Czechia", "Czech Republic"),
    DE("Germany"),
    DJ("Djibouti"),
    DK("Denmark"),
    DM("Dominica"),
    DO("Dominican Republic"),
    DZ("Algeria"),
    EC("Ecuador"),
    EE("Estonia"),
    EG("Egypt"),
    EH("Western Sahara"),
    ER("Eritrea"),
    ES("Spain"),
    ET("Ethiopia"),
    EU("European Union"),
    FI("Finland"),
    FJ("Fiji"),
    FK("Falkland Islands", "Malvinas"),
    FM("Micronesia", "Federated States of Micronesia"),
    FO("Faroe Islands"),
    FR("France"),
    GA("Gabon"),
    GD_NG("England"),
    GD_IR("Ireland"),
    GD_CT("Scotland"),
    GD_LS("Wales"),
    GB("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", "Great Britain"),
    GD("Grenada"),
    GE("Georgia"),
    GF("French Guiana"),
    GG("Guernsey"),
    GH("Ghana"),
    GI("Gibraltar"),
    GL("Greenland"),
    GM("Gambia"),
    GN("Guinea"),
    GP("Guadeloupe"),
    GQ("Equatorial Guinea"),
    GR("Greece"),
    GS("South Georgia", "South Georgia and the South Sandwich Islands"),
    GT("Guatemala"),
    GU("Guam"),
    GW("Guinea-Bissau"),
    GY("Guyana"),
    HK("Hong Kong"),
    HM("Heard Island and McDonald Islands"),
    HN("Honduras"),
    HR("Croatia"),
    HT("Haiti"),
    HU("Hungary"),
    ID("Indonesia"),
    IE("Ireland"),
    IL("Israel"),
    IM("Isle of Man"),
    IN("India"),
    IO("British Indian Ocean Territory"),
    IQ("Iraq"),
    IR("Iran", "Islamic Republic of Iran"),
    IS("Iceland"),
    IT("Italy"),
    JE("Jersey"),
    JM("Jamaica"),
    JO("Jordan"),
    JP("Japan"),
    KE("Kenya"),
    KG("Kyrgyzstan"),
    KH("Cambodia"),
    KI("Kiribati"),
    KM("Comoros"),
    KN("Saint Kitts and Nevis"),
    KP("North Korea", "the Democratic People's Republic of Korea"),
    KR("South Korea", "the Republic of Korea"),
    KW("Kuwait"),
    KY("Cayman Islands"),
    KZ("Kazakhstan"),
    LA("Lao People's Democratic Republic"),
    LB("Lebanon"),
    LC("Saint Lucia"),
    LI("Liechtenstein"),
    LK("Sri Lanka"),
    LR("Liberia"),
    LS("Lesotho"),
    LT("Lithuania"),
    LU("Luxembourg"),
    LV("Latvia"),
    LY("Libya"),
    MA("Morocco"),
    MC("Monaco"),
    MD("Moldova", "the Republic of Moldova"),
    ME("Montenegro"),
    MF("Saint Martin (French part)", "Saint Martin"),
    MG("Madagascar"),
    MH("Marshall Islands"),
    MK("Republic of North Macedonia"),
    ML("Mali"),
    MM("Myanmar"),
    MN("Mongolia"),
    MO("Macao"),
    MP("Northern Mariana Islands"),
    MQ("Martinique"),
    MR("Mauritania"),
    MS("Montserrat"),
    MT("Malta"),
    MU("Mauritius"),
    MV("Maldives"),
    MW("Malawi"),
    MX("Mexico"),
    MY("Malaysia"),
    MZ("Mozambique"),
    NA("Namibia"),
    NC("New Caledonia"),
    NE("Niger"),
    NF("Norfolk Island"),
    NG("Nigeria"),
    NI("Nicaragua"),
    NL("Netherlands"),
    NO("Norway"),
    NP("Nepal"),
    NR("Nauru"),
    NU("Niue"),
    NZ("New Zealand"),
    OM("Oman"),
    PA("Panama"),
    PE("Peru"),
    PF("French Polynesia"),
    PG("Papua New Guinea"),
    PH("Philippines"),
    PK("Pakistan"),
    PL("Poland"),
    PM("Saint Pierre and Miquelon"),
    PN("Pitcairn"),
    PR("Puerto Rico"),
    PS("Palestine", "State of Palestine"),
    PT("Portugal"),
    PW("Palau"),
    PY("Paraguay"),
    QA("Qatar"),
    RE("Réunion"),
    RO("Romania"),
    RS("Serbia"),
    RU("Russia", "Russian Federation"),
    RW("Rwanda"),
    SA("Saudi Arabia"),
    SB("Solomon Islands"),
    SC("Seychelles"),
    SD("Sudan"),
    SE("Sweden"),
    SG("Singapore"),
    SH("Saint Helena", "Saint Helena, Ascension and Tristan da Cunha"),
    SI("Slovenia"),
    SJ("Svalbard and Jan Mayen"),
    SK("Slovakia"),
    SL("Sierra Leone"),
    SM("San Marino"),
    SN("Senegal"),
    SO("Somalia"),
    SR("Suriname"),
    SS("South Sudan"),
    ST("São Tomé and Príncipe", "Sao Tome and Principe"),
    SV("El Salvador"),
    SX("Sint Maarten (Dutch part)", "Sint Maarten"),
    SY("Syrian Arab Republic"),
    SZ("Eswatini"),
    TC("Turks and Caicos Islands"),
    TD("Chad"),
    TF("French Southern Territories"),
    TG("Togo"),
    TH("Thailand"),
    TJ("Tajikistan"),
    TK("Tokelau"),
    TL("Timor-Leste"),
    TM("Turkmenistan"),
    TN("Tunisia"),
    TO("Tonga"),
    TR("Turkey", "Türkiye"),
    TT("Trinidad and Tobago"),
    TV("Tuvalu"),
    TW("Taiwan", "Taiwan (Province of China)"),
    TZ("Tanzania", "United Republic of Tanzania"),
    UA("Ukraine"),
    UG("Uganda"),
    UM("United States Minor Outlying Islands"),
    US("United States of America"),
    UY("Uruguay"),
    UZ("Uzbekistan"),
    VA("Holy See"),
    VC("Saint Vincent and the Grenadines"),
    VE("Venezuela", "Bolivarian Republic of Venezuela"),
    VG("Virgin Islands (British)"),
    VI("Virgin Islands (U.S.)"),
    VN("Viet Nam"),
    VU("Vanuatu"),
    WF("Wallis and Futuna"),
    WS("Samoa"),
    XK("Kosovo"),
    YE("Yemen"),
    YT("Mayotte"),
    ZA("South Africa"),
    ZM("Zambia"),
    ZW("Zimbabwe");

    private static final CountryCode[] VALUES = values();
    public static final int FLAG_WIDTH = 49;
    public static final int FLAG_HEIGHT = 32;
    private final Icon icon;
    private final String[] names;
    private final String translationKey = "labymod.country." + name().toLowerCase(Locale.ROOT);

    CountryCode(String... names) {
        this.names = names;
        int ordinal = ordinal();
        int x = ordinal % 16;
        int y = ordinal / 16;
        ThemeTextureLocation texture = ThemeTextureLocation.of("flags", 784, 512);
        this.icon = Icon.sprite(texture, x, y, 49, 32);
    }

    @Nullable
    public static CountryCode fromCode(String string) {
        String search = StringUtil.toUppercase(string).replace("-", "_");
        for (CountryCode countryCode : VALUES) {
            if (countryCode.name().equals(search)) {
                return countryCode;
            }
        }
        return null;
    }

    @Nullable
    public static CountryCode fromName(String name) {
        for (CountryCode countryCode : VALUES) {
            for (String countryCodeName : countryCode.getNames()) {
                if (countryCodeName.equalsIgnoreCase(name)) {
                    return countryCode;
                }
            }
        }
        return null;
    }

    public String getPrimaryName() {
        return this.names[0];
    }

    public String[] getNames() {
        return this.names;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Component getDisplayName() {
        if (I18n.has(this.translationKey)) {
            return Component.translatable(this.translationKey, new Component[0]);
        }
        return Component.text(getPrimaryName());
    }
}
