package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;

import java.util.Map;
import java.util.function.Function;

public class ConcreteRecipes {

    public static void generatePowderFromConcreteRecipes(RecipeOutput consumer, Function<Block, Criterion<InventoryChangeTrigger.TriggerInstance>> has) {
        for (Map.Entry<ResourceKey<Block>, Block> block : BuiltInRegistries.BLOCK.entrySet())
            if (block.getValue() instanceof ConcretePowderBlock)
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(((ConcretePowderBlock) block.getValue()).concrete), RecipeCategory.BUILDING_BLOCKS, block.getValue(), 0.0F, 200).group("cooking_concrete").unlockedBy("has_concrete", has.apply(((ConcretePowderBlock) block.getValue()).concrete)).save(consumer, ConcreteConversionCommon.ID + ":" + BuiltInRegistries.BLOCK.getKey(((ConcretePowderBlock) block.getValue()).concrete).getPath());
    }
}
