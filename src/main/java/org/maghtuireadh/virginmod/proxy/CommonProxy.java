package org.maghtuireadh.virginmod.proxy;


import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy 
{

	public void registerItemRenderer(Item item, int meta, String id) {}
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityFirepit.class, Reference.MODID + ":TileEntityFirepit");
		};	
	
}
