package org.maghtuireadh.virginmod.util.handlers;

import net.minecraft.init.Blocks;

public class VanillaHandler 
{
	public VanillaHandler() 
	{
		Blocks.LAVA.setLightLevel(0.6F);
		Blocks.FLOWING_LAVA.setLightLevel(0.6F);
		Blocks.LIT_FURNACE.setLightLevel(0.5F);
	}
}
