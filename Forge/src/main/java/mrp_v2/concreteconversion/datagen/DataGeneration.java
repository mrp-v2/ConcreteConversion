package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.RegistryDataBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jspecify.annotations.NonNull;

import java.util.Set;

@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(event.includeServer(), RegistryDataBuilder.of().modid(ConcreteConversionCommon.ID).reloadable(set -> set.add(RecipeProvider.create())).reloadableGenerator(packOutput));
        gen.addProvider(event.includeClient(), new LanguageProvider(packOutput, "en_us"));
    }

    public static class RecipeProvider extends VanillaRecipeProvider implements IConditionBuilder {

        public RecipeProvider(@NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
            super(recipes, advancements);
        }

        @Override
        protected void buildRecipes() {
            ConcreteRecipes.generatePowderFromConcreteRecipes(this.output, this::has);
        }

        public static MultiRegistryBootstrap create() {
            return new MultiRegistryBootstrap() {
                @Override
                public @NonNull Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                    return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
                }

                @Override
                public void run(final MultiRegistryBootstrap.@NonNull BootstrapGetter registries) {
                    new RecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
                }
            };
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
