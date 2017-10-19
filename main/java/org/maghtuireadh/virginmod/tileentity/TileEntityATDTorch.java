package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long timeset,start = (long)0;
/*
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		this.fuelLevel = nbt.getInteger("FuelLevel");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("FuelLevel", this.fuelLevel);
		return super.writeToNBT(nbt);
	}
	*/
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(!world.isRemote)
		{
			if(timeset > 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == Boolean.valueOf(true))
			{
				Utils.getLogger().info(timeset);
				if (world.getTotalWorldTime()-start > timeset )
				{
					((BlockATDTorch)world.getBlockState(pos).getBlock()).extinguish(world, pos);
					Utils.getLogger().info("killed");
				}
			}
		}
	}
	
	public void setTime(long time)
	{
		start = world.getTotalWorldTime();
		timeset = time;
	}

}
