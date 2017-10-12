package org.maghtuireadh.virginmod.objects.tools;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class ATDPokerIron extends ItemSpade implements IHasModel {

	public ATDPokerIron(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setCreativeTab(Main.virginmodtab);
		
		ItemInit.ITEMS.add(this);
	}
	
public void registerModels() {
	Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
