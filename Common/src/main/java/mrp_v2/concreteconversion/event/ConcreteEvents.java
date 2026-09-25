package mrp_v2.concreteconversion.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class ConcreteEvents {

    public static final ConcreteEvent<ItemToss> ITEM_TOSS = new ConcreteEvent<>(listeners -> (entity, livingEntity) -> {
        for(var listener : listeners)
            if(listener.handle(entity, livingEntity))
                return true;
        return false;
    });

    public static final ConcreteEvent<ServerLevelTick> SERVER_LEVEL_TICK = new ConcreteEvent<>(listeners -> (level) -> {
        for(var listener : listeners)
            listener.handle(level);
    });

    public interface ItemToss extends IConcreteEvent {

        boolean handle(ItemEntity entity, LivingEntity livingEntity);
    }

    public interface ServerLevelTick extends IConcreteEvent {
        void handle(ServerLevel level);
    }
}
