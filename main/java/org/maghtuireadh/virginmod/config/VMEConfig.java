package org.maghtuireadh.virginmod.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VMEConfig {
	private static Configuration config = null;
	
	public static final String CATEGORY_NAME_FIREPIT = "firepit";
	public static int firepitMaxFuelLevel;
	public static int firepitDefaultBurnRate;
	public static int firepitStokedBurnRate;
	public static int firepitBankedBurnRate;
	public static int firepitRainingBurnRate;
	public static int firepitCoalMax;
	public static int firepitAshMax;
	public static double firepitMinBankedCoalMod;
	public static double firepitMinBankedAshMod;
	public static double firepitMinCoalMod;
	public static double firepitMinAshMod;
	public static double firepitLowBankedCoalMod;
	public static double firepitLowBankedAshMod;
	public static double firepitLowCoalMod;
	public static double firepitLowAshMod;
	public static double firepitMidStokedCoalMod;
	public static double firepitMidStokedAshMod;
	public static double firepitMidCoalMod;
	public static double firepitMidAshMod;
	public static double firepitMaxStokedCoalMod;
	public static double firepitMaxStokedAshMod;
	public static double firepitMaxCoalMod;
	public static double firepitMaxAshMod;
	public static final String CATEGORY_FUEL_OBJECTS = "fuel_objects";
	public static String hearthFuelObjectListString;
	public static String lanternFuelObjectListString;

	public static String fuelObjectListString;
	
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "VME.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	public static Configuration getConfig() {
		return config;
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	public static void syncFromFiles() { //Updates the config file
		syncConfig(true, true);
	}
	
	public static void syncFromGUI() {
		syncConfig(false, true);
	}
	
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if(loadFromConfigFile)
			config.load();
		
		Property propertyFirepitMaxFuelLevel = config.get(CATEGORY_NAME_FIREPIT, "firepit_maximum_fuel_level", 10);
		Property propertyFirepitDefaultBurnRate = config.get(CATEGORY_NAME_FIREPIT, "firepit_default_burn_rate", 2);
		Property propertyFirepitStokedBurnRate = config.get(CATEGORY_NAME_FIREPIT, "firepit_stoked_burn_rate", 3);
		Property propertyFirepitRainingBurnRate = config.get(CATEGORY_NAME_FIREPIT,"firepit_raining_burn_rate", 4);
		Property propertyFirepitBankedBurnRate = config.get(CATEGORY_NAME_FIREPIT, "firepit_banked_burn_rate", 1);
		Property propertyFirepitCoalMax = config.get(CATEGORY_NAME_FIREPIT, "firepit_maximum_coal", 10);
		Property propertyFirepitAshMax = config.get(CATEGORY_NAME_FIREPIT, "firepit_maximum_ash", 10);
		Property propertyFirepitMinBankedCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_min_banked_coal_modifier", .70);
		Property propertyFirepitMinBankedAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_min_banked_ash_modifier", 1.30);
		Property propertyFirepitMinCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_min_coal_modifier", 0.85);
		Property propertyFirepitMinAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_min_ash_modifier", 1.15);
		Property propertyFirepitLowBankedCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_low_banked_coal_modifier", .85);
		Property propertyFirepitLowBankedAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_low_banked_ash_modifier", 1.30);
		Property propertyFirepitLowCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_low_coal_modifier", 1.0);
		Property propertyFirepitLowAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_low_ash_modifier", 1.15);
		Property propertyFirepitMidStokedCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_mid_stoked_coal_modifier", 1.30);
		Property propertyFirepitMidStokedAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_mid_stoked_ash_modifier", .85);
		Property propertyFirepitMidCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_mid_coal_modifier", 1.15);
		Property propertyFirepitMidAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_mid_ash_modifier", 1.00);
		Property propertyFirepitMaxStokedCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_max_stoked_coal_modifier", 1.30);
		Property propertyFirepitMaxStokedAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_max_stoked_ash_modifier", .75);
		Property propertyFirepitMaxCoalMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_max_coal_modifier", 1.15);
		Property propertyFirepitMaxAshMod = config.get(CATEGORY_NAME_FIREPIT, "firepit_max_ash_modifier", .85);
		
		//Firepit Configuration Values
		propertyFirepitMaxFuelLevel.setLanguageKey("gui.config.hearths.firepit_max_fuel_level.name");
		propertyFirepitMaxFuelLevel.setComment(I18n.format("gui.config.hearths.firepit_max_fuel_level.comment"));
		propertyFirepitMaxFuelLevel.setMinValue(0);
		propertyFirepitBankedBurnRate.setLanguageKey("gui.config.hearths.firepit_banked_burn_rate.name");
		propertyFirepitBankedBurnRate.setComment(I18n.format("gui.config.hearths.firepit_banked_burn_rate.comment"));
		propertyFirepitBankedBurnRate.setMinValue(1);
		propertyFirepitDefaultBurnRate.setLanguageKey("gui.config.hearths.firepit_default_burn_rate.name");
		propertyFirepitDefaultBurnRate.setComment(I18n.format("gui.config.hearths.firepit_default_burn_rate.comment"));
		propertyFirepitDefaultBurnRate.setMinValue(1);
		propertyFirepitStokedBurnRate.setLanguageKey("gui.config.hearths.firepit_stoked_burn_rate.name");
		propertyFirepitStokedBurnRate.setComment(I18n.format("gui.config.hearths.firepit_stoked_burn_rate.comment"));
		propertyFirepitStokedBurnRate.setMinValue(1);
		propertyFirepitRainingBurnRate.setLanguageKey("gui.config.hearths.firepit_raining_burn_rate.name");
		propertyFirepitRainingBurnRate.setComment(I18n.format("gui.config.hearths.firepit_raining_burn_rate.comment"));
		propertyFirepitRainingBurnRate.setMinValue(1);
		propertyFirepitCoalMax.setLanguageKey("gui.config.hearths.firepit_coal_max.name");
		propertyFirepitCoalMax.setComment(I18n.format("gui.config.hearths.firepit_coal_max.comment"));
		propertyFirepitCoalMax.setMinValue(0);
		propertyFirepitAshMax.setLanguageKey("gui.config.hearths.firepit_ash_max.name");
		propertyFirepitAshMax.setComment(I18n.format("gui.config.hearths.firepit_ash_max.comment"));
		propertyFirepitAshMax.setMinValue(0);
		propertyFirepitMinBankedCoalMod.setLanguageKey("gui.config.hearths.firepit_coal_min_banked_modifier.name");
		propertyFirepitMinBankedCoalMod.setComment(I18n.format("gui.config.hearths.firepit_coal_min_banked_modifier.comment"));
		propertyFirepitMinBankedAshMod.setLanguageKey("gui.config.hearths.firepit_ash_min_banked_modifier.comment");
		propertyFirepitMinCoalMod.setLanguageKey("gui.config.hearths.firepit_coal_min_mod.name");
		propertyFirepitMinAshMod.setLanguageKey("gui.config.hearths.firepit_ash_min_mod.comment");
		propertyFirepitLowBankedCoalMod.setLanguageKey("gui.config.hearths.firepit_low_banked_coal_modifier.name");
		propertyFirepitLowBankedAshMod.setLanguageKey("gui.config.hearths.firepit_low_banked_ash_modifier.name");
		propertyFirepitLowCoalMod.setLanguageKey("gui.config.hearths.firepit_low_coal_modifier.name");
		propertyFirepitMidStokedCoalMod.setLanguageKey("gui.config.hearths.firepit_coal_mid_stoked_mod.name");
		propertyFirepitMidStokedAshMod.setLanguageKey("gui.config.hearths.firepit_coal_mid_stoked_mod.name");
		propertyFirepitMidCoalMod.setLanguageKey("gui.config.hearths.firepit_mid_coal_modifier.name");
		propertyFirepitMidAshMod.setLanguageKey("gui.config.hearths.firepit_mid_ash_modifer.name");
		propertyFirepitMaxStokedCoalMod.setLanguageKey("gui.config.hearths.firepit_max_stoked_coal_mod.name");
		propertyFirepitMaxStokedAshMod.setLanguageKey("gui.config.hearths.firepit_max_stoked_ash_modifier.name");
		propertyFirepitMaxCoalMod.setLanguageKey("gui.config.hearths.firepit_max_coal_modifier.name");
		propertyFirepitMaxAshMod.setLanguageKey("gui.config.hearths.firepit_max_ash_modifier.name");
		
		List<String> propertyOrderBlocks = new ArrayList<String>();
		propertyOrderBlocks.add(propertyFirepitMaxFuelLevel.getName());
		propertyOrderBlocks.add(propertyFirepitBankedBurnRate.getName());
		propertyOrderBlocks.add(propertyFirepitDefaultBurnRate.getName());
		propertyOrderBlocks.add(propertyFirepitStokedBurnRate.getName());
		propertyOrderBlocks.add(propertyFirepitRainingBurnRate.getName());
		propertyOrderBlocks.add(propertyFirepitCoalMax.getName());
		propertyOrderBlocks.add(propertyFirepitAshMax.getName());
		propertyOrderBlocks.add(propertyFirepitMinBankedCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMinBankedAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitMinCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMinAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitLowBankedCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitLowBankedAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitLowCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitLowAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitMidStokedCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMidStokedAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitMidCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMidAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitMaxStokedCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMaxStokedAshMod.getName());
		propertyOrderBlocks.add(propertyFirepitMaxCoalMod.getName());
		propertyOrderBlocks.add(propertyFirepitMaxAshMod.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_NAME_FIREPIT, propertyOrderBlocks);
		
		if(readFieldsFromConfig) {
			firepitMaxFuelLevel = propertyFirepitMaxFuelLevel.getInt();
			firepitBankedBurnRate = propertyFirepitBankedBurnRate.getInt();
			firepitDefaultBurnRate = propertyFirepitDefaultBurnRate.getInt();
			firepitStokedBurnRate = propertyFirepitStokedBurnRate.getInt();
			firepitRainingBurnRate = propertyFirepitRainingBurnRate.getInt();
			firepitCoalMax = propertyFirepitCoalMax.getInt();
			firepitAshMax = propertyFirepitAshMax.getInt();
			firepitMinBankedCoalMod = propertyFirepitMinBankedCoalMod.getDouble();
			firepitMinBankedAshMod = propertyFirepitMinBankedAshMod.getDouble();
			firepitMinCoalMod = propertyFirepitMinCoalMod.getDouble();
			firepitMinAshMod = propertyFirepitMinAshMod.getDouble();
			firepitLowBankedCoalMod = propertyFirepitLowBankedCoalMod.getDouble();
			firepitLowBankedAshMod = propertyFirepitLowBankedAshMod.getDouble();
			firepitLowCoalMod = propertyFirepitLowCoalMod.getDouble();
			firepitLowAshMod = propertyFirepitLowAshMod.getDouble();
			firepitMidStokedCoalMod = propertyFirepitMidStokedCoalMod.getDouble();
			firepitMidStokedAshMod = propertyFirepitMidStokedAshMod.getDouble();
			firepitMidCoalMod = propertyFirepitMidCoalMod.getDouble();
			firepitMidAshMod = propertyFirepitMidAshMod.getDouble();
			firepitMaxStokedCoalMod = propertyFirepitMaxStokedCoalMod.getDouble();
			firepitMaxStokedAshMod = propertyFirepitMaxStokedAshMod.getDouble();
			firepitMaxCoalMod = propertyFirepitMaxCoalMod.getDouble();
			firepitMaxAshMod = propertyFirepitMaxAshMod.getDouble();
		}
		propertyFirepitMaxFuelLevel.set(firepitMaxFuelLevel);
		propertyFirepitBankedBurnRate.set(firepitBankedBurnRate);
		propertyFirepitDefaultBurnRate.set(firepitDefaultBurnRate);
		propertyFirepitStokedBurnRate.set(firepitStokedBurnRate);
		propertyFirepitRainingBurnRate.set(firepitRainingBurnRate);
		propertyFirepitCoalMax.set(firepitCoalMax);
		propertyFirepitAshMax.set(firepitAshMax);
		propertyFirepitMinBankedCoalMod.set(firepitMinBankedCoalMod);
		propertyFirepitMinBankedAshMod.set(firepitMinBankedAshMod);
		propertyFirepitMinCoalMod.set(firepitMinCoalMod);
		propertyFirepitMinAshMod.set(firepitMinAshMod);
		propertyFirepitLowBankedCoalMod.set(firepitMinBankedCoalMod);
		propertyFirepitLowBankedAshMod.set(firepitLowBankedAshMod);
		propertyFirepitLowCoalMod.set(firepitLowCoalMod);
		propertyFirepitLowAshMod.set(firepitLowAshMod);
		propertyFirepitMidStokedCoalMod.set(firepitMidStokedCoalMod);
		propertyFirepitMidStokedAshMod.set(firepitMidStokedAshMod);
		propertyFirepitMidCoalMod.set(firepitMidCoalMod);
		propertyFirepitMidAshMod.set(firepitMidAshMod);
		propertyFirepitMaxStokedCoalMod.set(firepitMaxStokedCoalMod);
		propertyFirepitMaxStokedAshMod.set(firepitMaxStokedAshMod);
		propertyFirepitMaxCoalMod.set(firepitMaxCoalMod);
		propertyFirepitMaxAshMod.set(firepitMaxAshMod);
		
		Property propertyHearthFuelObjectListString = config.get(CATEGORY_FUEL_OBJECTS, "hearth_fuel_object_list", "minecraft:planks-2_1000_500_500_true_false,minecraft:planks-1_500_1000_1000_true_false");
		Property propertyLanternFuelObjectListString = config.get(CATEGORY_FUEL_OBJECTS, "lantern_fuel_object_list", "minecraft:coal-0_0_0_0_false_true");
		propertyHearthFuelObjectListString.setLanguageKey("gui.config.fuelobject.hearth_object_list.name");
		propertyHearthFuelObjectListString.setComment(I18n.format("gui.config.fuelobject.hearth_fuel_object_list.comment"));
		propertyLanternFuelObjectListString.setLanguageKey("gui.config.fuelobject.lantern_object_list.name");
		propertyLanternFuelObjectListString.setComment(I18n.format("gui.config.fuelobject.lantern_fuel_object_list.comment"));
		

		List<String> propertyOrderFuelObjects = new ArrayList<String>();
		propertyOrderFuelObjects.add(propertyHearthFuelObjectListString.getName());
		propertyOrderFuelObjects.add(propertyLanternFuelObjectListString.getName());
		
		config.setCategoryPropertyOrder(CATEGORY_FUEL_OBJECTS, propertyOrderFuelObjects);
		
		
		if(readFieldsFromConfig) {
			hearthFuelObjectListString = propertyHearthFuelObjectListString.getString();
			lanternFuelObjectListString = propertyLanternFuelObjectListString.getString();
		}
		
		propertyHearthFuelObjectListString.set(hearthFuelObjectListString);
		propertyLanternFuelObjectListString.set(lanternFuelObjectListString);
		fuelObjectListString = hearthFuelObjectListString + "," + lanternFuelObjectListString;
		
		if(config.hasChanged())
			config.save();
	}
		public static class ConfigEventHandler {
			@SubscribeEvent(priority = EventPriority.LOWEST)
			public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
				if(event.getModID().equals(Reference.MODID)){
					syncFromGUI();
				}
			}
		}
	}

