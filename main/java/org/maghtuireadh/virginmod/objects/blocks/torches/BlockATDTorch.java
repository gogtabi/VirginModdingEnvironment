package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockATDTorch extends BlockTorch implements IHasModel {
	
	public static int burntime = 40;
	
	
	
	
	/**
	 * Default constructor which sets the hardness and resistance
	 * @param unlocalizedName The unlocalized name
	 */
	public BlockATDTorch(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(1.0F);
		this.setTickRandomly(true);
		Blocks.FIRE.setFireInfo(this, 60, 20);
		setCreativeTab(Main.virginmodtab);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	/*@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleBlockUpdate(pos, this , burntime, 0);
		if (world.isRainingAt(pos)) {
			breakBlock(world, pos, state);
		} else {
			
		}
		 
	}
	*/
/*
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
			return Item.getItemFromBlock(BlockInit.ATD_TORCH);
	}

	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		world.setBlockToAir(pos);
	}
*/
/*	@Override
	public void registerModels() {
	Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}*/

	/*@Override
	 public int quantityDropped(Random random)
    {
        return 0;
    }*/
	
	
	

	}