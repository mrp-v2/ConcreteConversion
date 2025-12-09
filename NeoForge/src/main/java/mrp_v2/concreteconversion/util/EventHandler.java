package mrp_v2.concreteconversion.util;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = ConcreteConversionCommon.ID)
public class EventHandler {

    @SubscribeEvent
    public static void itemTossEvent(ItemTossEvent event) {
        if (!ConcreteEvents.ITEM_TOSS.post().handle(event.getEntity(), event.getPlayer()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void worldTickEvent(LevelTickEvent.Pre event) {
        if (!event.getLevel().isClientSide())
            ConcreteEvents.SERVER_LEVEL_TICK.post().handle((ServerLevel) event.getLevel());
    }
}
