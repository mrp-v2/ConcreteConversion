package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.server.Config;
import net.minecraft.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class) public class FallingBlockEntityMixin
{
    @ModifyVariable(at = @At(value = "STORE", ordinal = 0), method = "tick") private boolean tick(boolean flag)
    {
        if (Config.SERVER.getDisableVanillaConversionMechanic())
        {
            return false;
        }
        return flag;
    }
}
