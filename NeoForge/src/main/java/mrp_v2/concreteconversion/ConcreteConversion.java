package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.datagen.DataGeneration;
import mrp_v2.concreteconversion.server.Config;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(value = ConcreteConversionCommon.ID)
public class ConcreteConversion {

    public ConcreteConversion(ModContainer mod, IEventBus modBus) {
        mod.registerConfig(ModConfig.Type.SYNCED, Config.serverSpec);
        modBus.addListener(DataGeneration::gatherData);
        modBus.addListener(this::onLoad);
        modBus.addListener(this::onFileChange);
        ConcreteConversionCommon.init();
    }

    public void onLoad(final ModConfigEvent.Loading event) {
        ConcreteConversionCommon.LOG.debug("Loaded Concrete Conversion config file {}", event.getConfig().getFileName());
        ConcreteConversionCommon.CONFIG.updateValues();
    }

    public void onFileChange(final ModConfigEvent.Reloading event) {
        ConcreteConversionCommon.LOG.debug("Concrete Conversion config just got changed on the file system!");
        ConcreteConversionCommon.CONFIG.updateValues();
    }
}
