package org.maghtuireadh.virginmod.tabs;

import org.maghtuireadh.virginmod.init.BlockInit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class VirginModTab extends CreativeTabs
{

	public VirginModTab(String label) 
	{
		super("virginmodtab");
		this.setBackgroundImageName("virginmodtab.png");
	}
	
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(BlockInit.BLOCK_FIREPIT);
	}
}
