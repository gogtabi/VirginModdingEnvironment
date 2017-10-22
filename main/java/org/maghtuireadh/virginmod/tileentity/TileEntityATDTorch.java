package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long timeset,start = (long)0;

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		if (nbt==null)
		{
			nbt = new NBTTagCompound();
		}
		super.readFromNBT(nbt);
		//this.fuelLevel = nbt.getInteger("FuelLevel");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		if (nbt==null)
		{
			nbt = new NBTTagCompound();
		}
		//nbt.setInteger("FuelLevel", this.fuelLevel);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void update() {
		if(!world.isRemote)
		{
			if(timeset > 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == true)
			{
				Utils.getLogger().info(timeset);
				if (world.getTotalWorldTime()-start > timeset )
				{
					((IIgnitable)world.getBlockState(pos).getBlock()).extinguish(world, pos, null);
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
	public long getTime()
	{
		return world.getTotalWorldTime()-start-timeset;
	}


}
