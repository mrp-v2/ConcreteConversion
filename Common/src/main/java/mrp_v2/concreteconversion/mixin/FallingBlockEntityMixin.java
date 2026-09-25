package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {

    @ModifyVariable(method = "tick", at = @At(value = "STORE", ordinal = 0), name = "isConcrete")
    private boolean tick(boolean isConcrete) {
        if (ConcreteConversionCommon.CONFIG.getDisableVanillaConversionMechanic()) {
            return false;
        }
        return isConcrete;
    }
}
