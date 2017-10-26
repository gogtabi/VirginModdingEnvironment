package org.maghtuireadh.virginmod.objects.blocks.hearths;

import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.item.itemFireStarter;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHearth extends Block implements IIgnitable, IFireStarter, IHasModel, ITileEntityProvider 
{
	int lightCount;
	public BlockHearth(String unlocalizedName, Material material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		this.setCreativeTab(Main.virginmodtab);
		lightCount=100;
	}
	
	@Override
	public void registerModels() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) {
		return true;}

		


	@Override
	public boolean isLit(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public long getFuel(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos) {
		// TODO Auto-generated method stub
		
	}


}


