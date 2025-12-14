package mrp_v2.concreteconversion.util;

import com.google.common.collect.Maps;
import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;

import java.util.Iterator;
import java.util.Map;

public class EventHandlerCommon {

    private static final Map<ItemEntity, Integer> entities = Maps.newHashMap();
    private static int lastCheck = 0;

    public static boolean itemTossEvent(ItemEntity entity, Player player) {
        if (!entity.level().isClientSide())
            addPlayerThrownItemEntity(entity);
        return true;
    }

    public static void worldTickEvent(ServerLevel level) {
        itemEntityCheck(level);
    }

    private static void addPlayerThrownItemEntity(ItemEntity entity) {
        if (ConcreteConversionCommon.CONFIG.getOnlyPlayerThrownItems() && (isConcretePowder(entity) || (ConcreteConversionCommon.CONFIG.getConvertMud() && isValidDirt(entity))))
            entities.putIfAbsent(entity, 0);
    }

    private static boolean isConcretePowder(ItemEntity entity) {
        return (Block.byItem(entity.getItem().getItem()) instanceof ConcretePowderBlock);
    }

    private static boolean isValidDirt(ItemEntity entity) {
        return Block.byItem(entity.getItem().getItem()).defaultBlockState().is(BlockTags.CONVERTABLE_TO_MUD);
    }

    private static void itemEntityCheck(ServerLevel level) {
        if ((ConcreteConversionCommon.CONFIG.getConversionCheckDelay() <= ++lastCheck)) {
            lastCheck = 0;
            if (!ConcreteConversionCommon.CONFIG.getOnlyPlayerThrownItems())
                for (Entity entity : level.getEntities(EntityType.ITEM, (entity) -> true))
                    entities.putIfAbsent((ItemEntity) entity, 0);

            Iterator<ItemEntity> iterator = entities.keySet().iterator();
            while (iterator.hasNext()) {
                ItemEntity entity = iterator.next();
                if (!entity.isAlive()) {
                    iterator.remove();
                    continue;
                }
                if (entity.isInWater()) {
                    if (entities.get(entity) >= ConcreteConversionCommon.CONFIG.getConversionDelay()) {
                        convertEntity(entity);
                        iterator.remove();
                    } else {
                        entities.replace(entity, entities.get(entity) + 1);
                    }
                } else {
                    entities.replace(entity, 0);
                }
            }
        }
    }

    private static void convertEntity(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        Block block = null;
        if (isConcretePowder(entity))
            block = getPowderConverted(Block.byItem(stack.getItem()));
        else if (ConcreteConversionCommon.CONFIG.getConvertMud() && isValidDirt(entity))
            block = Blocks.MUD;
        if (block != null)
            entity.setItem(new ItemStack(block, stack.getCount()));
    }

    private static Block getPowderConverted(Block block) {
        ConcretePowderBlock concretePowderBlock = block instanceof ConcretePowderBlock ? (ConcretePowderBlock) block : null;
        if (concretePowderBlock == null)
            return null;
        return concretePowderBlock.concrete;
    }
}
