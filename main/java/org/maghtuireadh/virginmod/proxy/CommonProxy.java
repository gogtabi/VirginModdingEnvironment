package org.maghtuireadh.virginmod.proxy;


import org.maghtuireadh.virginmod.config.VMEConfig;
import org.maghtuireadh.virginmod.events.MovingLightEvent;
import org.maghtuireadh.virginmod.tileentity.TileEntityMovingLightSource;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	
	public void registerTileEntities() 
	{
		GameRegistry.registerTileEntity(TileEntityFirepit.class, Reference.MODID + ":TileEntityFirepit");
		GameRegistry.registerTileEntity(TileEntityMovingLightSource.class, Reference.MODID + ":TEMovingLightSource");
		MinecraftForge.EVENT_BUS.register(new MovingLightEvent());
	}	
}
