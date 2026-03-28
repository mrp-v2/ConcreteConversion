package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;

public class DataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(LanguageProvider::new);
    }

    public static class RecipeProvider extends FabricRecipeProvider {

        public RecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider registriesFuture, RecipeOutput exporter) {
            return new net.minecraft.data.recipes.RecipeProvider(registriesFuture, exporter) {
                @Override
                public void buildRecipes() {
                    ConcreteRecipes.generatePowderFromConcreteRecipes(exporter, this::has);
                }
            };
        }

        @Override
        public String getName() {
            return ConcreteConversionCommon.ID + " recipes";
        }
    }

    public static class LanguageProvider extends FabricLanguageProvider {

        public LanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, "en_us", registriesFuture);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
            ConcreteLanguage.english(builder::add);
        }
    }
}
