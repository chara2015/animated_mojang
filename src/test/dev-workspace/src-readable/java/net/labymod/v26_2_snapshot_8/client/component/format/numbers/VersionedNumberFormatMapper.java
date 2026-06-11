package net.labymod.v26_2_snapshot_8.client.component.format.numbers;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.chat.numbers.FixedFormat;
import net.minecraft.network.chat.numbers.StyledFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/component/format/numbers/VersionedNumberFormatMapper.class */
@Singleton
@Implements(NumberFormatMapper.class)
public class VersionedNumberFormatMapper implements NumberFormatMapper {
    private final ComponentMapper componentMapper;

    @Inject
    public VersionedNumberFormatMapper(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public NumberFormat fromMinecraft(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof net.minecraft.network.chat.numbers.NumberFormat)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(net.minecraft.network.chat.numbers.NumberFormat.class));
        }
        FixedFormat fixedFormat = (net.minecraft.network.chat.numbers.NumberFormat) obj;
        if (fixedFormat instanceof BlankFormat) {
            return NumberFormat.blank();
        }
        if (fixedFormat instanceof FixedFormat) {
            FixedFormat fixedFormat2 = fixedFormat;
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat2.value));
        }
        if (fixedFormat instanceof StyledFormat) {
            StyledFormat styledFormat = (StyledFormat) fixedFormat;
            return NumberFormat.styled((Style) CastUtil.cast(styledFormat.style));
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(fixedFormat.getClass()));
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public Object toMinecraft(NumberFormat format) {
        if (format == null) {
            return null;
        }
        if (format instanceof net.labymod.api.client.component.format.numbers.BlankFormat) {
            return BlankFormat.INSTANCE;
        }
        if (format instanceof net.labymod.api.client.component.format.numbers.FixedFormat) {
            net.labymod.api.client.component.format.numbers.FixedFormat fixedFormat = (net.labymod.api.client.component.format.numbers.FixedFormat) format;
            return new FixedFormat((Component) this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof net.labymod.api.client.component.format.numbers.StyledFormat) {
            net.labymod.api.client.component.format.numbers.StyledFormat styledFormat = (net.labymod.api.client.component.format.numbers.StyledFormat) format;
            return new StyledFormat((net.minecraft.network.chat.Style) CastUtil.cast(styledFormat.style()));
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
