<<<<<<< HEAD
package org.maghtuireadh.virginmod.util.interfaces;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IIgnitable {
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player);
	public boolean isLit(World world, BlockPos pos, EntityPlayer player);
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player);
	public long getFuel(World world, BlockPos pos, EntityPlayer player);
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos);
}

=======
package org.maghtuireadh.virginmod.util.interfaces;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IIgnitable {
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player);
	public boolean isLit(World world, BlockPos pos, EntityPlayer player);
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player);
	public long getFuel(World world, BlockPos pos, EntityPlayer player);
	public void setFuel(long fuel, World world, BlockPos pos, EntityPlayer player);
}

>>>>>>> 727ab70cc1b325e5c0a5512144996783657821d9
