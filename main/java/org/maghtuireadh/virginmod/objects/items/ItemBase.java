package org.maghtuireadh.virginmod.objects.items;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.util.IHasModel;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.proxy.ClientProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
