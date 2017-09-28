package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class atd_torch extends BlockTorch {
	
	public static int burntime = 500;
	
	
	
	
	/**
	 * Default constructor which sets the hardness and resistance
	 * @param unlocalizedName The unlocalized name
	 */
	public atd_torch(String unlocalizedName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		setLightLevel(15.0F);
		setTickRandomly(true);
		Blocks.FIRE.setFireInfo(this, 60, 20);
		
		
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleBlockUpdate(pos, this , burntime, 0);
		if (world.isRainingAt(pos)) {
			breakBlock(world, pos, state);
		} else {
			
		}
		 
	}
	

	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
			return Item.getItemFromBlock(BlockInit.ATD_TORCH);
	}

	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		world.setBlockToAir(pos);
	}


	
	
	

	}