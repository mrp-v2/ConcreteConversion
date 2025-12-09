package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;

import java.util.function.BiConsumer;

public class ConcreteLanguage {

    public static void english(BiConsumer<String, String> values) {
        values.accept(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.OPTI), "Only Player Thrown Items");
        values.accept(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CCD), "Conversion Check Delay");
        values.accept(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CD), "Conversion Delay");
        values.accept(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.DVCM), "Disable Vanilla Conversion Mechanic");
        values.accept(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CM), "Convert Mud");
    }
}
