package net.labymod.v1_21.client.component.format.numbers;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.numbers.BlankFormat;
import net.labymod.api.client.component.format.numbers.FixedFormat;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/component/format/numbers/VersionedNumberFormatMapper.class */
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
        if (!(obj instanceof yp)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(yp.class));
        }
        yo yoVar = (yp) obj;
        if (yoVar instanceof yn) {
            return NumberFormat.blank();
        }
        if (yoVar instanceof yo) {
            yo fixedFormat = yoVar;
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat.b));
        }
        if (yoVar instanceof ys) {
            ys styledFormat = (ys) yoVar;
            return NumberFormat.styled(styledFormat.e);
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(yoVar.getClass()));
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormatMapper
    public Object toMinecraft(NumberFormat format) {
        if (format == null) {
            return null;
        }
        if (format instanceof BlankFormat) {
            return yn.a;
        }
        if (format instanceof FixedFormat) {
            FixedFormat fixedFormat = (FixedFormat) format;
            return new yo((wz) this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof StyledFormat) {
            StyledFormat styledFormat = (StyledFormat) format;
            return new ys(styledFormat.style());
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
