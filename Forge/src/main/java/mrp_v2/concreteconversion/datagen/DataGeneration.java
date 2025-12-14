package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(event.includeServer(), new RecipeProvider.Runner(packOutput, event.getLookupProvider()));
        gen.addProvider(event.includeClient(), new LanguageProvider(packOutput, "en_us"));
    }

    public static class RecipeProvider extends VanillaRecipeProvider implements IConditionBuilder {

        public RecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            super(lookupProvider, output);
        }

        @Override
        protected void buildRecipes() {
            ConcreteRecipes.generatePowderFromConcreteRecipes(this.output, this::has);
        }

        protected Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item) {
            return net.minecraft.data.recipes.RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), item));
        }

        public static class Runner extends VanillaRecipeProvider.Runner {

            public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
                super(output, lookupProvider);
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
                return new RecipeProvider(lookupProvider, output);
            }
        }
    }

    public static class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

        public LanguageProvider(PackOutput output, String locale) {
            super(output, ConcreteConversionCommon.ID, locale);
        }

        @Override
        protected void addTranslations() {
            ConcreteLanguage.english(this::add);
        }
    }
}
