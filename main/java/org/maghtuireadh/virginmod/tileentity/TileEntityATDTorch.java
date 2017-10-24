package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long sentTimeset,sentStart,timeset,start = (long)0;
	public NBTTagCompound nbt;
	BlockPos sentPos = null;

	public TileEntityATDTorch()
	{
		Utils.getLogger().info("constructed TE");
	}
	

	
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("timeset") && nbt.hasKey("start"))
		{
			this.timeset = nbt.getLong("timeset");
			this.start = nbt.getLong("start");
			Utils.getLogger().info("read from nbt");
		}
		else
		{
			nbt.setLong("timeset", (long)0);
			nbt.setLong("start", (long)0);
			Utils.getLogger().info("in READ i wrote");
		}
	}
	
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		nbt.setLong("timeset", this.timeset);
		nbt.setLong("start", this.start);
		Utils.getLogger().info("write to nbt TE");
		super.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void update() {
		if(!world.isRemote)
		{
			if (sentPos != null && sentPos == pos)
			{
				timeset = sentTimeset;
				start = sentStart;
				sentTimeset = 0; 
				sentStart = 0;
				sentPos = null;
			}
			if(timeset > 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == true)
			{
				Utils.getLogger().info(world.getTotalWorldTime()-start + " < " + timeset);
				if (world.getTotalWorldTime()-start > timeset)
				{
					//((IIgnitable)world.getBlockState(pos).getBlock()).extinguish(world, pos, null);
					world.setBlockToAir(pos);
					world.removeTileEntity(pos);
					Utils.getLogger().info("killed");
				}
				
			}
			markDirty();
		}
	}
	
	public void setTime(long time, BlockPos pos)
	{
		sentStart = world.getTotalWorldTime();
		sentTimeset = time;
		sentPos = pos;
		Utils.getLogger().info("set time " + time);
		markDirty();
	}


}
