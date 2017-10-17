package org.maghtuireadh.virginmod.objects.blocks;


import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockSoil extends Block implements IHasModel
{

	public static final PropertyInteger SOILSTATE = PropertyInteger.create("soilstate", 0, 11);
	public static IBlockState[] soilStates = new IBlockState[12];
	
	public BlockSoil(String name, float hardness, String tool, int harvest, float resistance) 
	{
		super(Material.GROUND);
		this.setHardness(hardness);
		this.setHarvestLevel(tool, harvest);
		this.setResistance(resistance);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
		for (int i = 0; i < soilStates.length - 1; i++) 
		{
	        soilStates[i] =  this.blockState.getBaseState().withProperty(SOILSTATE, i);
	    }
	}

	@Override
	public void registerModels() 
	{
		for (int i = 0; i < soilStates.length - 1; i++) 
		{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, "state="+i);
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {SOILSTATE});
	}
}
