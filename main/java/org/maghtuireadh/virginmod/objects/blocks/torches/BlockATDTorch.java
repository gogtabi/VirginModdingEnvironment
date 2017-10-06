package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public class BlockATDTorch extends BlockTorch implements IHasModel {
	

	public static Long burnTime = (long) 40;
	public  Long setTime = (long) 0;
	
	
	
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
		//setCreativeTab(Main.virginmodtab);
		BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		//world.scheduleBlockUpdate(pos, this , burnTime, 0);
		this.setTime = world.getTotalWorldTime();
		if (world.isRainingAt(pos)) {
			breakBlock(world, pos, state);
		} 
		Utils.getLogger().info("Set Time: " + setTime);
			 
	}


	
//	@Override
//	public Item getItemDropped(IBlockState state, Random random, int fortune) {
//			return Item.getItemFromBlock(BlockInit.ATD_TORCH);
//	}

	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		//if(world.getTotalWorldTime() - this.setTime < burnTime) {
		//world.setBlockToAir(pos);
		//}
		Utils.getLogger().info("Get World Time: " + world.getTotalWorldTime());
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}

	@Override
	 public int quantityDropped(Random random)
    {
        return 0;
    }
	
	
	

	}