package org.maghtuireadh.virginmod.util.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IIgnitable {
    public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player);
    public boolean isLit(World world, BlockPos pos, EntityPlayer player);
    public boolean extinguish(World world, BlockPos pos, EntityPlayer player);
    public long getFuel(World world, BlockPos pos, EntityPlayer player);
    public void setFuel(World world, BlockPos pos, EntityPlayer player);
    
}