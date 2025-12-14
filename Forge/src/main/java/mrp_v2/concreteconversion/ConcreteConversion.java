package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.server.Config;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = ConcreteConversionCommon.ID)
@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConcreteConversion {

    public ConcreteConversion(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
        ConcreteConversionCommon.init();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        ConcreteConversionCommon.LOG.debug("Loaded Concrete Conversion config file {}", event.getConfig().getFileName());
        ConcreteConversionCommon.CONFIG.updateValues();
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event) {
        ConcreteConversionCommon.LOG.debug("Concrete Conversion config just got changed on the file system!");
        ConcreteConversionCommon.CONFIG.updateValues();
    }
}
