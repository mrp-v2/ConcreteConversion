package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.config.Config;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = ConcreteConversion.MODID)
public class ConcreteConversion {

	public static final String MODID = "concreteconversion";
	public static final String TRANSLATION_KEY_STEM = "mrp_v2." + MODID + ".";

	public ConcreteConversion() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(Config.class);
	}
}