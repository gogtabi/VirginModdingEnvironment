package org.maghtuireadh.virginmod.proxy;

import org.maghtuireadh.virginmod.tileentity.TileEntityBlockBreaker;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBlockBreaker.class, Reference.MODID + ": block_breaker");
	}
}
