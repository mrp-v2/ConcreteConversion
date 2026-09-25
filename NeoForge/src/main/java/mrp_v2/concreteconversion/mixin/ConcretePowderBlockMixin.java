package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin {

    @Redirect(method = "shouldSolidify(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canBeHydrated(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;)Z"), remap = false)
    private static boolean shouldSolidify(BlockState state, BlockGetter getter, BlockPos pos, FluidState fluidState) {
        if (ConcreteConversionCommon.CONFIG.getDisableVanillaConversionMechanic()) {
            return false;
        }
        return state.canBeHydrated(getter, pos, fluidState);
    }

    @Redirect(method = "touchesLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canBeHydrated(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;)Z"), remap = false)
    private static boolean touchesLiquid(BlockState state, BlockGetter getter, BlockPos pos, FluidState fluidState) {
        if (ConcreteConversionCommon.CONFIG.getDisableVanillaConversionMechanic()) {
            return false;
        }
        return state.canBeHydrated(getter, pos, fluidState);
    }
}
