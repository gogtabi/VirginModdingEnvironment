package org.maghtuireadh.virginmod;

import org.maghtuireadh.virginmod.proxy.CommonProxy;
import org.maghtuireadh.virginmod.tabs.VirginModTab;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	@Instance
	public static Main instance;
	
	public static final CreativeTabs virginmodtab = new VirginModTab("virginmodtab");
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public static void preInit(FMLInitializationEvent event) {}
	
	@EventHandler
	public static void preInit(FMLPostInitializationEvent event) {}
	
}
