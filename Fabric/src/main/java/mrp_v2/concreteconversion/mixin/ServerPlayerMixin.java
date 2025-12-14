package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Redirect(method = "drop(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"))
    public ItemEntity drop (ServerPlayer player, ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        ItemEntity entity = player.drop(stack, throwRandomly, retainOwnership);
        if (entity != null)
            if (!ConcreteEvents.ITEM_TOSS.post().handle(entity, (Player)(Object)this))
                return null;
        return entity;
    }

}
