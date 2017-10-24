package org.maghtuireadh.virginmod.util.handlers;

import net.minecraft.init.Blocks;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class VanillaHandler 
{
	public VanillaHandler() 
	{

		Blocks.LAVA.setLightLevel(0.5F);
		Blocks.FLOWING_LAVA.setLightLevel(0.5F);
		Blocks.LIT_FURNACE.setLightLevel(0.5F);
	}
}
