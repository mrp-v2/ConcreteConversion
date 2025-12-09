package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGeneration {

    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(true, new RecipeProvider.Runner(packOutput, event.getLookupProvider()));
        gen.addProvider(true, new LanguageProvider(packOutput, "en_us"));
    }

    public static class RecipeProvider extends VanillaRecipeProvider {

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

    public static class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

        public LanguageProvider(PackOutput output, String locale) {
            super(output, ConcreteConversionCommon.ID, locale);
        }

        @Override
        protected void addTranslations() {
            ConcreteLanguage.english(this::add);
        }
    }
}
