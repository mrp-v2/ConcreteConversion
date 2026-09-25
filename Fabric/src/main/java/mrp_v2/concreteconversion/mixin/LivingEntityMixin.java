package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "createItemStackToDrop", at = @At("RETURN"), cancellable = true)
    public void createDrop(CallbackInfoReturnable<ItemEntity> cir) {
        if (cir.getReturnValue() != null)
            if (!ConcreteEvents.ITEM_TOSS.post().handle(cir.getReturnValue(), (LivingEntity)(Object)this))
                cir.cancel();
    }
}
