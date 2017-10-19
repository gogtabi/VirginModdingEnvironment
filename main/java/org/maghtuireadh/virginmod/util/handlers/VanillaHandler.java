package org.maghtuireadh.virginmod.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class VanillaHandler 
{

	public VanillaHandler() 
	{
		Blocks.LAVA.setLightLevel(0.6F);
		Blocks.FLOWING_LAVA.setLightLevel(0.6F);
		Blocks.LIT_FURNACE.setLightLevel(0.5F);
	}
}
