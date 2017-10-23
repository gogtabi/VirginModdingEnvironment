package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long timeset,start,burntime = (long)0;
	public NBTTagCompound nbt;

	public TileEntityATDTorch(long burntime)
	{
		this.burntime = burntime;
		Utils.getLogger().info("constructed TE");
	}
	

	
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		this.burntime = nbt.getLong("burntime");
		this.timeset = nbt.getLong("timeset");
		this.start = nbt.getLong("start");
		Utils.getLogger().info("read from nbt");
	}
	
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setLong("burntime", this.burntime);
		nbt.setLong("timeset", this.timeset);
		nbt.setLong("start", this.start);

		Utils.getLogger().info("write to nbt TE");
		return nbt;
	}

	@Override
	public void update() {
		if(!world.isRemote)
		{
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
	
	public void setTime(long time)
	{
		start = world.getTotalWorldTime();
		timeset = time;
		Utils.getLogger().info("set time " + time);
		markDirty();
	}
	public long getTime()
	{
		if(start != 0 && timeset != 0)
		{
			return world.getTotalWorldTime()-start-timeset;
		}
		else
		{
			return burntime;
		}
	}


}
