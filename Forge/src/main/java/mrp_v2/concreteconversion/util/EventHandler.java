package mrp_v2.concreteconversion.util;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID)
public class EventHandler {

    @SubscribeEvent
    public static boolean itemTossEvent(ItemTossEvent event) {
        if (!ConcreteEvents.ITEM_TOSS.post().handle(event.getEntity(), event.getPlayer()))
            return true;
        return false;
    }

    @SubscribeEvent
    public static void worldTickEvent(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.SERVER)
            ConcreteEvents.SERVER_LEVEL_TICK.post().handle((ServerLevel) event.level);
    }
}
