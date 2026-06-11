package net.labymod.v1_21_10.client.component.format.numbers;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.numbers.BlankFormat;
import net.labymod.api.client.component.format.numbers.FixedFormat;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/component/format/numbers/VersionedNumberFormatMapper.class */
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
        if (!(obj instanceof zw)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(zw.class));
        }
        zv zvVar = (zw) obj;
        if (zvVar instanceof zu) {
            return NumberFormat.blank();
        }
        if (zvVar instanceof zv) {
            zv fixedFormat = zvVar;
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat.b));
        }
        if (zvVar instanceof zz) {
            zz styledFormat = (zz) zvVar;
            return NumberFormat.styled((Style) CastUtil.cast(styledFormat.e));
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(zvVar.getClass()));
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public Object toMinecraft(NumberFormat format) {
        if (format == null) {
            return null;
        }
        if (format instanceof BlankFormat) {
            return zu.a;
        }
        if (format instanceof FixedFormat) {
            FixedFormat fixedFormat = (FixedFormat) format;
            return new zv((xx) this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof StyledFormat) {
            StyledFormat styledFormat = (StyledFormat) format;
            return new zz((yv) CastUtil.cast(styledFormat.style()));
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
