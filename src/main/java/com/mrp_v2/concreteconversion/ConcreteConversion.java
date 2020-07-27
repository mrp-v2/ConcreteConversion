package com.mrp_v2.concreteconversion;

import com.mrp_v2.concreteconversion.config.ConfigOptions;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(value = ConcreteConversion.MODID)
public class ConcreteConversion {

	public static final String MODID = "concreteconversion";

	public ConcreteConversion() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigOptions.serverSpec);
	}
}