package org.maghtuireadh.virginmod.util.interfaces;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IIgnitable {
	public void attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player);
	
}
